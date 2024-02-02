package org.example.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.example.dtos.BillingAddress;
import org.example.dtos.Product;
import org.example.dtos.ShippingAddress;

@Data
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String customerId;
    private String createdDate;
    private String updatedDate;
    private Double orderAmount;
    private String status;
    private String invoiceNumber;
    private String transactionId;
    private String createdBy;
    private String updatedBy;
    private Product product;
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;
}