package com.tiva.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiva.app.models.ProductSize;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Integer> {

    Optional<ProductSize> findByName(String name);
}
