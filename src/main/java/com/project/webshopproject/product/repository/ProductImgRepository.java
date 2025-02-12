package com.project.webshopproject.product.repository;

import com.project.webshopproject.product.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg,Long> {
}
