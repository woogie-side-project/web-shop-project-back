package com.project.webshopproject.product.repository;

import com.project.webshopproject.product.dto.ProductAddRequestDto;
import com.project.webshopproject.product.dto.ProductFindRequestDto;
import com.project.webshopproject.product.dto.ProductRequestDto;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    List<ProductRequestDto> findAllProducts();
    ProductFindRequestDto findProductById(Long id);

//    List<Product> findByCategory(String category);

    //메인화면 들어가면 상품 전체 조회 하면서 세부카테고리 고유 id,이름,메인카테고리 이름 까지 보내줘야함
}
