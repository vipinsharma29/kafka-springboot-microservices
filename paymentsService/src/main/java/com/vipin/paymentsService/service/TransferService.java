package com.vipin.paymentsService.service;

import com.vipin.paymentsService.dto.TransferRestModel;

public interface TransferService {

    public boolean transfer(TransferRestModel productPaymentRestModel);

}
