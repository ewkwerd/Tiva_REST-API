package com.tiva.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiva.app.models.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
