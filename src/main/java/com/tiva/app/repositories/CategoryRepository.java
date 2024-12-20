package com.tiva.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiva.app.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    Optional<Category> findByName(String name);
}
