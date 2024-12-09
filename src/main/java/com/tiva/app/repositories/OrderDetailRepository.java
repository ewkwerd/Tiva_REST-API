package com.tiva.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiva.app.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
  
}