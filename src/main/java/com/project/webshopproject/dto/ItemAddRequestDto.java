package com.project.webshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ItemAddRequestDto {
    private String itemName;    // 상품 이름
    private Integer itemPrice;  // 상품 가격
    private Integer itemStock; // 상품 재고
    private List<String> itemImg; // 상품  이미지
}
