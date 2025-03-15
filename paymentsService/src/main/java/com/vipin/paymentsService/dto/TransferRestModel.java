package com.vipin.paymentsService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRestModel {

    private String senderId;
    private String recipientId;
    private BigDecimal amount;

}
