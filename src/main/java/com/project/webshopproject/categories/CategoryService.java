package com.project.webshopproject.categories;

import com.project.webshopproject.categories.dto.CategoryAddRequestDto;
import com.project.webshopproject.categories.dto.CategoryEditRequestDto;
import com.project.webshopproject.categories.entity.ProductCategory;
import com.project.webshopproject.categories.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    // 카테고리 전체 조회
    public List<ProductCategory> getAllCategories(){
        List<ProductCategory> allcategories = productCategoryRepository.findAll();
        return allcategories;
    }
    // 카테고리 추가
    public void addCategory(CategoryAddRequestDto categoryAddRequestDto){
        ProductCategory productCategory = ProductCategory.builder()
                .name(categoryAddRequestDto.categoryName()).build();
        productCategoryRepository.save(productCategory);
    }

    // 카테고리 수정
    public void editCategory(Long categoryId,CategoryEditRequestDto categoryEditRequestDto){
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));

        productCategory = productCategory.builder()
                .id(categoryId)
                .name(categoryEditRequestDto.name())
                .build();

        productCategoryRepository.save(productCategory);
    }

    // 카테고리 삭제
}
