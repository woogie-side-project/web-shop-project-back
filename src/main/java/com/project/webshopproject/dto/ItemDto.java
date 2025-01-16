package com.project.webshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemDto {
    private String itemName;    // 상품 이름
    private String itemImg;     // 상품 이미지 URL
    private Integer itemPrice;  // 상품 가격
}
