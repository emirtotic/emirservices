package com.emirtotic.notification;

import com.emirtotic.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(Notification.builder()
                .toCustomerId(notificationRequest.getToCustomerId())
                .toCustomerEmail(notificationRequest.getToCustomerEmail())
                .sender("Emir Services")
                .message(notificationRequest.getMessage())
                .sentAt(LocalDateTime.now())
                .build());
    }

}
