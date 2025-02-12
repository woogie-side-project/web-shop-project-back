package com.project.webshopproject.product.repository;

import com.project.webshopproject.product.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long>{

}
