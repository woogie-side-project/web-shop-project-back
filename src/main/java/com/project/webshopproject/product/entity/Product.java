package com.project.webshopproject.product.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 50)
    private String name;

    @OneToOne
    @JoinColumn(name = "product_main_id")
    private ProductImg mainImage;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImg> productImages = new ArrayList<>();  // 모든 이미지

    @ManyToOne
    @JoinColumn(name = "sub_category_id", nullable = false)
    private SubCategory subCategory; // 서브 카테고리

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Builder
    public Product(String name, ProductImg mainImage, List<ProductImg> productImages,
                   SubCategory subCategory, Integer price, Integer stock) {
        this.name = name;
        this.mainImage = mainImage;
        if (productImages != null) {
            this.productImages = productImages;
        }
        this.subCategory = subCategory;
        this.price = price;
        this.stock = stock;
    }

}
