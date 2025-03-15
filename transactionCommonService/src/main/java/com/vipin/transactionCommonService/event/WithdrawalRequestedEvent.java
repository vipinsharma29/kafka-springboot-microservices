package com.vipin.transactionCommonService.event;

import java.math.BigDecimal;

public class WithdrawalRequestedEvent {

    private String senderId;

    private String recipientId;

    private BigDecimal amount;

    public WithdrawalRequestedEvent() {
    }

    public WithdrawalRequestedEvent(String senderId, String recipientId, BigDecimal amount) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.amount = amount;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}