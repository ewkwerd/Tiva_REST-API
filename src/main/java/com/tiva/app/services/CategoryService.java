package com.tiva.app.services;

import com.tiva.app.exceptions.ConflictException;
import com.tiva.app.exceptions.ResourceNotFoundException;
import com.tiva.app.models.Category;
import com.tiva.app.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(int id) {
        return categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }

    public Category save(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new ConflictException("Category with name " + category.getName() + " already exists");
        }

        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(int id, Category categoryDetails) {
        Category existingCategory = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));

        existingCategory.setName(categoryDetails.getName());
        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public void deleteById(int id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
}
