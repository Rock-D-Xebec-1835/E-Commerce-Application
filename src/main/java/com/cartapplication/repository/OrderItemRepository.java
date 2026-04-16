package com.cartapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cartapplication.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
