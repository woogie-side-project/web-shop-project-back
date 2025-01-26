package com.project.webshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRequestDto {
    private String itemName;    // 상품 이름
    private Integer itemPrice;  // 상품 가격
    private Integer itemCount; // 상품 수량
}
