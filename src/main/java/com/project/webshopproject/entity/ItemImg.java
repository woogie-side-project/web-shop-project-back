package com.project.webshopproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "itemImage")
@Getter
@Setter
@NoArgsConstructor
public class ItemImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemImgId;  // 제품 이미지 고유번호

    @ManyToOne(fetch = FetchType.LAZY) // 엔티티를 조회할 때 Items 엔티티를 바로 불러오지 않고,필요할 때 불러옴 → 성능 최적화
    @JoinColumn(name = "item_id", nullable = false)
    private Items items; // 연결된 상품

    @Column(length = 300)
    private String itemImg; // 제품 상세 이미지
}
