package com.project.webshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemFindRequestDto {
    private String itemName;
    private String itemImgUrl;
    private Integer itemPrice;
    private Integer itemCount;
}
