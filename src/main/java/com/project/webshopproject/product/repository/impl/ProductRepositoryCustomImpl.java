package com.project.webshopproject.product.repository.impl;

import com.project.webshopproject.product.dto.*;
import com.project.webshopproject.product.entity.Product;
import com.project.webshopproject.product.entity.QProduct;
import com.project.webshopproject.product.entity.QProductImg;
import com.project.webshopproject.product.repository.ProductRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct product = QProduct.product;
    private final QProductImg productImage = QProductImg.productImg;


    // 모든 상품 조회 그런데 이제 메인 이미지 조인을 곁들인
    public List<ProductRequestDto> findAllProducts(){

        return jpaQueryFactory
                .select(new QProductRequestDto(
                        product.name,
                        productImage.image,
                        product.price,
                        product.stock
                ))
                .from(product)
                .leftJoin(productImage)
                .on(product.eq(productImage.product) // product_id 기준으로 조인
                        .and(productImage.isMain.isTrue())) // 메인이미지만 필터링
                .fetch();
    }

    public ProductFindRequestDto findProductById(Long id){
        List<String> imageList = jpaQueryFactory
                .select(productImage.image)
                .from(productImage)
                .where(productImage.product.productId.eq(id))
                .orderBy(
                        productImage.orderNo.asc()
                )
                .fetch();

        ProductFindRequestDto productFindRequestDto = jpaQueryFactory
                .select(new QProductFindRequestDto(
                        product.name,
                        product.price,
                        product.stock
                ))
                .from(product)
                .where(product.productId.eq(id))
                .fetchOne();
        productFindRequestDto.setImageList(imageList);
        return productFindRequestDto;
    }

}
