package org.example.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dtos.BillingAddress;
import org.example.dtos.Product;
import org.example.dtos.ShippingAddress;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateEvent {
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

}