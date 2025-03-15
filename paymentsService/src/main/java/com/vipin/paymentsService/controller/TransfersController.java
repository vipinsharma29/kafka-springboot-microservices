package com.vipin.paymentsService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.vipin.paymentsService.dto.TransferRestModel;
import com.vipin.paymentsService.service.TransferService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/transfers")
public class TransfersController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransferService transferService;

    @PostMapping()
    public boolean transfer(@RequestBody TransferRestModel transferRestModel) {
        return transferService.transfer(transferRestModel);
    }

}
