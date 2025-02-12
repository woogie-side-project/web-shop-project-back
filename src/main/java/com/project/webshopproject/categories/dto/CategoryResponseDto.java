package com.project.webshopproject.categories.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String name;

    public CategoryResponseDto(Long id, String name) {
        this.categoryId = id;
        this.name = name;
    }
}
