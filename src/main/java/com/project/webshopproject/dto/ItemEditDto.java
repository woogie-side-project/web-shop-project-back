package com.project.webshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemEditDto {
    private String itemName;
    private String itemImgUrl;
    private Integer itemPrice;
    private Integer itemCount;
}
