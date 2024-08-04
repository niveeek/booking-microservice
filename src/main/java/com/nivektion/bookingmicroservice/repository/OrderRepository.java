package com.nivektion.bookingmicroservice.repository;

import com.nivektion.bookingmicroservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
