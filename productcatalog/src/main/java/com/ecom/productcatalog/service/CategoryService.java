package com.ecom.productcatalog.service;

import com.ecom.productcatalog.model.Category;
import com.ecom.productcatalog.repository.*;

import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
        
    }
}
