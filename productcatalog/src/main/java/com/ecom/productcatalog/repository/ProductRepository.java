package com.ecom.productcatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.productcatalog.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // this function will be translated by jpa, the find = select * | the by = where | categoryid = categoryid is equal to param
    List<Product> findByCategoryId(Long categoryId);
}
