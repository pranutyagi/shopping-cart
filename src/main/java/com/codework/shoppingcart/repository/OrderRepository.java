package com.codework.shoppingcart.repository;

import com.codework.shoppingcart.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByUserId(Long userId);
}
