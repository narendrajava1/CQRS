package org.example.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.aspects.LogExecutionTime;
import org.example.commands.CreateOrderCommand;
import org.example.dtos.OrderDto;
import org.example.exceptions.PreconditionFailed;
import org.example.service.OrderCommandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderCommandController {
    @Autowired
    private OrderCommandService orderCommandService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @LogExecutionTime
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto) throws ExecutionException, InterruptedException, PreconditionFailed {
        if (Objects.nonNull(orderDto.getOrderId())) {
            throw new PreconditionFailed("Invalid payload....! ");
        }
        CreateOrderCommand createOrderCommand=new CreateOrderCommand();
        BeanUtils.copyProperties(orderDto,createOrderCommand);
        log.debug("Processing CreateOrderCommand: {}", createOrderCommand);
        createOrderCommand.setOrderId(UUID.randomUUID().toString());
        CompletableFuture<String> order = orderCommandService.createOrder(createOrderCommand);
        Map<String, String> map = new HashMap<>();
        if (Objects.nonNull(order)) {
            map.put("orderId", order.get());
            map.put("message", "Order created successfully...!");
            return ResponseEntity.ok(map);
        } else
            map.put("message", "Order is not created...!");
        return ResponseEntity.internalServerError().body(map);
    }

}