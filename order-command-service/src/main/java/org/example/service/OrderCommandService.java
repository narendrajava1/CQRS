package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.commands.CreateOrderCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderCommandService {
    @Autowired
    private  CommandGateway commandGateway;

    public CompletableFuture<String> createOrder(CreateOrderCommand createOrderCommand) {
        log.debug("Processing CreateOrderCommand: {}", createOrderCommand);
        createOrderCommand.setOrderId(UUID.randomUUID().toString());
        log.info("Create ==== Order Id ==== {} ", createOrderCommand.getOrderId());
        return commandGateway.send(createOrderCommand);

    }
}