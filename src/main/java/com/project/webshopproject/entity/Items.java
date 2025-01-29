package com.project.webshopproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false,length = 300 )
    private String itemMainImg; // 제품 메인 이미지

    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImg> itemImages = new ArrayList<>();  // 여러 이미지들

    @Column(nullable = false)
    private Integer itemPrice;  // 가격

    @Column(nullable = false)
    private Integer itemStock;  // 상품 수량
}
