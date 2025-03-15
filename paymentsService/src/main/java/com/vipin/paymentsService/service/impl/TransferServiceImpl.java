package com.vipin.paymentsService.service.impl;

import com.vipin.paymentsService.model.TransferEntity;
import com.vipin.paymentsService.repository.TransferRepository;
import com.vipin.paymentsService.service.TransferService;
import org.apache.kafka.common.Uuid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.vipin.paymentsService.exception.TransferServiceException;
import com.vipin.paymentsService.dto.TransferRestModel;
import com.vipin.transactionCommonService.event.TransactionRequestedEvent;

@Service
public class TransferServiceImpl implements TransferService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransferRepository transferRepository;

    @Transactional("transactionManager")
    @Override
    public boolean transfer(TransferRestModel transferRestModel) {
        TransactionRequestedEvent withdrawalEvent = new TransactionRequestedEvent(transferRestModel.getSenderId(),
                transferRestModel.getRecipientId(), transferRestModel.getAmount());
        TransactionRequestedEvent depositEvent = new TransactionRequestedEvent(transferRestModel.getSenderId(),
                transferRestModel.getRecipientId(), transferRestModel.getAmount());

        try {

            saveTransferDetails(transferRestModel);

            kafkaTemplate.send(environment.getProperty("withdraw-money-topic", "withdraw-money-topic"),
                    withdrawalEvent);
            LOGGER.info("Sent event to withdrawal topic.");

            // Business logic that causes and error
            String response = callRemoteService().getBody();

            kafkaTemplate.send(environment.getProperty("deposit-money-topic", "deposit-money-topic"), depositEvent);
            LOGGER.info("Sent event to deposit topic");

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new TransferServiceException(ex);
        }

        return true;
    }

    private void saveTransferDetails(TransferRestModel transferRestModel) {
        TransferEntity transferEntity = new TransferEntity();
        BeanUtils.copyProperties(transferRestModel, transferEntity);
        transferEntity.setTransferId(Uuid.randomUuid().toString());

        // Save record to a database table
        transferRepository.save(transferEntity);
    }

    private ResponseEntity<String> callRemoteService() throws Exception {
        String requestUrl = "http://localhost:8080/response/200";
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, String.class);

        if (response.getStatusCode().value() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            throw new Exception("Destination Microservice not availble");
        }

        if (response.getStatusCode().value() == HttpStatus.OK.value()) {
            LOGGER.info("Received response from mock service: " + response.getBody());
        }
        return response;
    }

}
