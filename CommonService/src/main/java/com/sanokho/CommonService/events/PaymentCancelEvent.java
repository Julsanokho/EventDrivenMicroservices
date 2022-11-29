package com.sanokho.CommonService.events;

import lombok.Data;

@Data
public class PaymentCancelEvent {
    private String paymentId;
    private String orderId;
    private String paymentStatus;
}
