package org.example.repositories;

import org.example.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepo extends MongoRepository<Order, String> {
    Optional<Order> findByOrderId(String ordrerId);
}