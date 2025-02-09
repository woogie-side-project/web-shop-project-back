package com.project.webshopproject.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {
    private String productName;
    private String productImg;
    private Integer productPrice;
    private Integer productStock;


    @QueryProjection
    public ProductRequestDto(String name,String mainImageId, Integer price, Integer stock) {
        this.productName = name;
        this.productImg = mainImageId;
        this.productPrice = price;
        this.productStock = stock;
    }
}
