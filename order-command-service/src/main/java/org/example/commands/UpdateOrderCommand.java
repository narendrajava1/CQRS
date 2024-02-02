package org.example.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class UpdateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String status;
    private String transactionId;
}