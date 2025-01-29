package com.project.webshopproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;  // 제품 고유번호

    @Column(nullable = false, length = 50)
    private String itemName;  // 제품 이름

    @Column(length = 300)
    private String itemImg;  // 이미지 경로 (URL)

    @Column(nullable = false)
    private Integer itemPrice;  // 가격

    @Column(nullable = false)
    private Integer itemStock;  // 상품 수량
}
