package org.example.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.example.entities.Order;
import org.example.repositories.OrderRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@ProcessingGroup("order-create")
@Slf4j
@RequiredArgsConstructor
public class OrderEventHandler {
    private final OrderRepo orderRepo;

    @EventHandler
    public void on(OrderCreateEvent orderCreateEvent) throws JsonProcessingException {

        log.debug("Handling {} event: {}", orderCreateEvent.getClass().getSimpleName(), orderCreateEvent.getOrderId());
        Order order = new Order();
        BeanUtils.copyProperties(orderCreateEvent,order);
        orderRepo.save(order);
//        initiatePaymentEventOnKafka(order);
        log.trace("Done handling {} event: {}", orderCreateEvent.getClass().getSimpleName(), orderCreateEvent.getOrderId());

    }

    @EventHandler
    public void on(OrderUpdateEvent orderUpdateEvent) {
        log.debug("Handling {} event: {}", orderUpdateEvent.getClass().getSimpleName(), orderUpdateEvent.getOrderId());
        Optional<Order> optionalOrder = orderRepo.findByOrderId(orderUpdateEvent.getOrderId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(orderUpdateEvent.getStatus());
            order.setUpdatedDate(LocalDateTime.now().toString());
            orderRepo.save(order);
        }
        log.trace("Done handling {} event: {}", orderUpdateEvent.getClass().getSimpleName(), orderUpdateEvent.getOrderId());
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;

    }

}