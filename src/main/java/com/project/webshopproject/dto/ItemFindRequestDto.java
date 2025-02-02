package com.project.webshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ItemFindRequestDto {
    private String itemName;
    private List<String> itemImgs;
    private Integer itemPrice;
    private Integer itemStock;
}
