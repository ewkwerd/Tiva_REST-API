package com.tiva.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiva.app.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {

}

