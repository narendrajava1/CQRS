package org.example.aggregaters;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.commands.CreateOrderCommand;
import org.example.dtos.BillingAddress;
import org.example.dtos.OrderDto;
import org.example.dtos.Product;
import org.example.dtos.ShippingAddress;
import org.example.events.OrderCreateEvent;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Aggregate
@Data
@Slf4j
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String customerId;
    private String createdDate;
    private String updatedDate;
    private double orderAmount;
    private String status;
    private String invoiceNumber;
    private String createdBy;
    private String updatedBy;
    private Product product;

    private String transactionId;
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;

    public OrderAggregate(){

    }

    @CommandHandler()
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        if (Objects.isNull(createOrderCommand.getOrderId()))
            throw new IllegalArgumentException("Order id should be present...!");

        if (createOrderCommand.getOrderAmount() <= 0)
            throw new IllegalArgumentException("Price cannot be less or equal to zero...!");

        if (Objects.isNull(createOrderCommand.getProduct()))
            throw new IllegalArgumentException("Product should be present...!");
        createOrderCommand.setCreatedDate(LocalDateTime.now().toString());
        log.debug("Handling {} command: {}", createOrderCommand.getClass().getSimpleName(), createOrderCommand.getOrderId());
        OrderCreateEvent orderCreateEvent=new OrderCreateEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreateEvent);
        AggregateLifecycle.apply(orderCreateEvent);
        log.trace("Done handling {} event: {}", orderCreateEvent.getClass().getSimpleName(), orderCreateEvent.getOrderId());

    }

    @EventSourcingHandler
    public void on(OrderCreateEvent orderCreateEvent) {
        log.debug("Handling {} event: {}", orderCreateEvent.getClass().getSimpleName(), orderCreateEvent.getOrderId());
        this.orderId = orderCreateEvent.getOrderId();
        this.billingAddress = orderCreateEvent.getBillingAddress();
        this.createdBy = orderCreateEvent.getCreatedBy();
        this.createdDate = orderCreateEvent.getCreatedDate();
        this.customerId = orderCreateEvent.getCustomerId();
        this.invoiceNumber = orderCreateEvent.getInvoiceNumber();
        this.orderAmount = orderCreateEvent.getOrderAmount();
        this.product = orderCreateEvent.getProduct();
        this.shippingAddress = orderCreateEvent.getShippingAddress();
        this.status = orderCreateEvent.getStatus();
        this.updatedBy = orderCreateEvent.getUpdatedBy();
        this.updatedDate = orderCreateEvent.getUpdatedDate();
        this.transactionId = orderCreateEvent.getTransactionId();
        log.trace("Done handling {} event: {}", orderCreateEvent.getClass().getSimpleName(), orderCreateEvent.getOrderId());
    }

}

