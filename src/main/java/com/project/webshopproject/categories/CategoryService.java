package com.project.webshopproject.categories;

import com.project.webshopproject.categories.dto.CategoryAddRequestDto;
import com.project.webshopproject.categories.dto.CategoryEditRequestDto;
import com.project.webshopproject.categories.dto.CategoryResponseDto;
import com.project.webshopproject.categories.entity.ProductCategory;
import com.project.webshopproject.categories.repository.ProductCategoryRepository;
import com.project.webshopproject.product.entity.Products;
import com.project.webshopproject.product.repository.ProductQueryRepository;
import com.project.webshopproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

    // 카테고리 전체 조회
    public List<CategoryResponseDto> getAllCategories(){
        List<ProductCategory> allcategories = productCategoryRepository.findAll();
        return allcategories.stream()
                .map(productCategory -> new CategoryResponseDto(
                productCategory.getProductCategoryId(),
                productCategory.getName())).collect(Collectors.toList());
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
    public void deleteCategory(Long categoryId){
        ProductCategory productCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리가 존재하지 않습니다."));
        productQueryRepository.deleteProductByCategory(categoryId);
    }
}
