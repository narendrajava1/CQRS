package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.example.dtos.OrderDto;
import org.example.dtos.OrderResult;
import org.example.queries.GetOrderQuery;
import org.example.utils.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.axonframework.queryhandling.QueryGateway;

@Slf4j
@Service
public class OrderQueryService {
    @Autowired
    private  QueryGateway queryGateway;
    public CompletableFuture<Optional<OrderDto>> getOrderById(String orderId) {
        return queryGateway.query(orderId, ResponseTypes.optionalInstanceOf(OrderDto.class));
    }

    public CompletableFuture<OrderResult> retrieveAllOrders(Integer months, Integer page, Integer size, SortOrder sortOrder) {
        GetOrderQuery getOrderQuery = GetOrderQuery.builder().months(months).page(page).size(size).sortOrder(sortOrder).build();
        return queryGateway.query(getOrderQuery, ResponseTypes.instanceOf(OrderResult.class));
    }
}