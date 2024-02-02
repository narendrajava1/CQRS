package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
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
    private ShippingAddress shippingAddress;
    private BillingAddress billingAddress;
    private String transactionId;
}