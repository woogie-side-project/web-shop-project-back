package com.project.webshopproject.product.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "productImage")
@Getter
@NoArgsConstructor
public class ProductImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImgId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    @Column(length = 255, nullable = false)
    private String image;

    @Column(nullable = false)
    private boolean isMain;

    @Column(nullable = false)
    private Integer orderNo;

    @Builder
    public ProductImg(String image, boolean isMain, Integer orderNo, Products products){
        this.image = image;
        this.isMain = isMain;
        this.orderNo = orderNo;
        this.products = products;
    }
}
