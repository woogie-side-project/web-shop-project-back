package com.project.webshopproject.product.dto;

import com.project.webshopproject.product.entity.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductAddRequestDto {

    private String productName;
    private Integer productPrice;
    private Integer productStock;
    private SubCategory subCategory;
    private List<String> productImg;
    public ProductAddRequestDto(String productName, Integer productPrice, Integer productStock, SubCategory subCategory) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.subCategory = subCategory;
    }
    public void setImageList(List<String> productImgs) {
        this.productImg = productImgs;
    }

}



