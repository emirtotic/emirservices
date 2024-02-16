package com.emirtotic.clients.notification;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {

    private Integer toCustomerId;
    private String toCustomerEmail;
    private String message;

}
