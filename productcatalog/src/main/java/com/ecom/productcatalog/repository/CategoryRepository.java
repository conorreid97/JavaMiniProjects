package com.ecom.productcatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecom.productcatalog.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
