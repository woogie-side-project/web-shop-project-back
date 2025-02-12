package com.project.webshopproject.product.dto;

import com.project.webshopproject.categories.entity.CategoryType;
public record ProductAddRequestDto(
        Long categoryId,
        String productName,
        Integer productPrice,
        Integer productStock,
        String categoryName,
        CategoryType categoryType
) {
}



