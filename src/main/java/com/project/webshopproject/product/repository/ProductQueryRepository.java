package com.project.webshopproject.product.repository;

import com.project.webshopproject.product.dto.*;
import com.project.webshopproject.product.entity.QProducts;
import com.project.webshopproject.product.entity.QProductCategory;
import com.project.webshopproject.product.entity.QProductImg;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProducts product = QProducts.products;
    private final QProductImg productImage = QProductImg.productImg;
    private final QProductCategory productCategory = QProductCategory.productCategory;

    // 모든 상품 조회
    public List<ProductResponseDto> findAllProducts(){
        return jpaQueryFactory
                .select(new QProductResponseDto(
                        product.productId,
                        product.category.productCategoryId,
                        product.categoryType.stringValue(),
                        productCategory.name,
                        product.name,
                        productImage.image,
                        product.price,
                        product.stock
                ))
                .from(product)
                .leftJoin(product.category, productCategory)
                .leftJoin(productImage)
                .on(product.eq(productImage.products)
                        .and(productImage.isMain.isTrue())) // 메인이미지만 필터링
                .fetch();
    }

}
