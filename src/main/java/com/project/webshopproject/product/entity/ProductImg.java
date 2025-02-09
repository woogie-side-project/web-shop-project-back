package com.project.webshopproject.product.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "itemImage")
@Getter
@NoArgsConstructor
public class ProductImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemImageId;

    @ManyToOne(fetch = FetchType.LAZY) // 엔티티를 조회할 때 Items 엔티티를 바로 불러오지 않고,필요할 때 불러옴 → 성능 최적화
    @JoinColumn(name = "item_id", nullable = false)
    private Product product;

    @Column(length = 255, nullable = false)
    private String image;

    @Column(nullable = false)
    private boolean isMain;

    @Column(nullable = false)
    private Integer orderNo;

    @Builder
    public ProductImg(String image, boolean isMain, Integer orderNo){
        this.image = image;
        this.isMain = isMain;
        this.orderNo = orderNo;
    }
}
