package com.emirtotic.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "com.emirtotic.customer",
                "com.emirtotic.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.emirtotic.clients"
)
@PropertySources({
        @PropertySource("classpath:client-${spring.profiles.active}.properties")
})
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }



}
