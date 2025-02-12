package com.project.webshopproject.categories.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productCategory")
@Getter
@NoArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;

    @Column(nullable = false, length = 255)
    private String name;

    @Builder
    public ProductCategory(Long id, String name) {
        this.productCategoryId = id;
        this.name = name;
    }

}
