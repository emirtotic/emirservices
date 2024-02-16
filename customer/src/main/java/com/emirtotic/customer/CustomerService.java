package com.emirtotic.customer;

import com.emirtotic.amqp.RabbitMQMessageProducer;
import com.emirtotic.clients.fraud.FraudCheckResponse;
import com.emirtotic.clients.fraud.FraudClient;
import com.emirtotic.clients.notification.NotificationClient;
import com.emirtotic.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {

        Customer customer = Customer.builder().firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // TODO: check if email is valid
        // TODO: check if email is not taken

        customerRepository.saveAndFlush(customer);  // Save and flush allows us to get the customer id not to be null

        // TODO: check is the customer a fraudster

        FraudCheckResponse response = fraudClient.isFraudster(customer.getId());

        if (response.isFraudster()) {
            throw new IllegalStateException("Fraudster!");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Emir Services...", customer.getFirstName())
        );

        // Adding to the queue
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        // TODO: save the customer to the DB


    }
}
