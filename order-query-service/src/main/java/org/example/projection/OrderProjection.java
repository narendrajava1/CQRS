package org.example.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.example.dtos.OrderDto;
import org.example.dtos.OrderResult;
import org.example.entities.Order;
import org.example.utils.SortOrder;

import org.example.queries.GetOrderQuery;
import org.example.repositories.OrderRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderProjection {
    @Autowired
    private OrderRepo orderRepo;

    @QueryHandler
    public Optional<OrderDto> handle(String orderId) {
        Optional<Order> orderOpt = orderRepo.findByOrderId(orderId);
        OrderDto orderDto = new OrderDto();
        orderOpt.ifPresent(order -> {
            BeanUtils.copyProperties(order, orderDto);
        });
        return Optional.ofNullable(orderDto);
    }

    @QueryHandler
    public OrderResult handle(GetOrderQuery getOrderQuery) {
        Sort.Direction sort = Sort.Direction.DESC;
        if (SortOrder.ASC.equals(getOrderQuery.getSortOrder())) {
            sort = Sort.Direction.ASC;
        }
        if (Objects.isNull(getOrderQuery.getPage())) {
            getOrderQuery.setPage(1);
        }
        if (Objects.isNull(getOrderQuery.getSize())) {
            getOrderQuery.setSize(10);
        }
        Pageable paging = PageRequest.of(getOrderQuery.getPage() - 1, getOrderQuery.getSize(), Sort.by(sort, "createdDate"));
        long count = orderRepo.count();
        Page<Order> orderPage = orderRepo.findAll(paging);
        OrderResult orderResult = new OrderResult();
        orderResult.setPage(paging.getPageNumber());
        orderResult.setSize(paging.getPageSize());
        orderResult.setTotal(count);
        orderResult.setOrderDtos(orderPage.stream().map(order ->{
            OrderDto orderDto=new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            return orderDto;
        }).collect(Collectors.toList()));
        return orderResult;
    }


}