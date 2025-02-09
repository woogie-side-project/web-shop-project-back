package com.project.webshopproject.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductFindRequestDto {
    private String productName;
    private List<String> productImg;
    private Integer productPrice;
    private Integer productStock;

    @QueryProjection
    public ProductFindRequestDto(String productName, Integer productPrice, Integer productStock) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public void setImageList(List<String> productImgs) {
        this.productImg = productImgs;
    }
}
