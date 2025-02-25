package com.ecom.productcatalog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecom.productcatalog.model.Product;
import com.ecom.productcatalog.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getProductByCategory(Long categoryId){
        return productRepository.findByCategoryId(categoryId);
    }
}
