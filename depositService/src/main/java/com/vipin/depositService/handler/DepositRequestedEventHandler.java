package com.vipin.depositService.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.vipin.transactionCommonService.event.TransactionRequestedEvent;

@Component
@KafkaListener(topics = "deposit-money-topic", containerFactory = "kafkaListenerContainerFactory")
public class DepositRequestedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @KafkaHandler
    public void handle(@Payload TransactionRequestedEvent depositRequestedEvent) {
        LOGGER.info("Received a new deposit event: {} ", depositRequestedEvent.getAmount());
    }
}
