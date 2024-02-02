package org.example.controllers;

import org.example.aspects.LogExecutionTime;
import org.example.dtos.OrderDto;
import org.example.dtos.OrderResult;
import org.example.services.OrderQueryService;
import org.example.utils.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/orders")
public class OrderQueryController {

    @Autowired
    private OrderQueryService orderQueryService;

    @GetMapping("/{orderId}")
    @LogExecutionTime
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") String orderId) throws ExecutionException, InterruptedException {
        CompletableFuture<Optional<OrderDto>> orderDTOFuture = orderQueryService.getOrderById(orderId);
        if (orderDTOFuture.get().isPresent()) {
            return ResponseEntity.ok(orderDTOFuture.get().get());
        }
        return ResponseEntity.ok("Order not found...!");
    }

    @LogExecutionTime
    @GetMapping
    public CompletableFuture<OrderResult> retrieveAllOrders(@RequestParam(required = false, name = "months") Integer months, @RequestParam(required = false, name = "page") Integer page, @RequestParam(required = false, name = "size") Integer size, @RequestParam(required = false, name = "sortOrder") SortOrder sortOrder) {
        return orderQueryService.retrieveAllOrders(months, page, size, sortOrder);
    }

}
