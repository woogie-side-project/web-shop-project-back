package com.project.webshopproject.product.entity;

import com.project.webshopproject.categories.entity.CategoryType;
import com.project.webshopproject.categories.entity.ProductCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @OneToOne
    @JoinColumn(name = "product_main_id")
    private ProductImg mainImage;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImg> productImages = new ArrayList<>();  // 모든 이미지

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name="categoryType",nullable = false, length = 10)
    private CategoryType categoryType;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Builder
    public Products(ProductCategory category, String name, Integer price, Integer stock, CategoryType categoryType) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.categoryType = categoryType;
    }

}
