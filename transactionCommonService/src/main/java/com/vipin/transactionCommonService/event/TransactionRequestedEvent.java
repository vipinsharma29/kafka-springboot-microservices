package com.vipin.transactionCommonService.event;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestedEvent {

    private String senderId;

    private String recipientId;

    private BigDecimal amount;

    public TransactionRequestedEvent() {
    }

    public TransactionRequestedEvent(String senderId, String recipientId, BigDecimal amount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
    }

}