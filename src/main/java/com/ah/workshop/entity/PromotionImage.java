package com.ah.workshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * PromotionImage: 存放一頁式畫面上方展示商品的圖片
 */
@Entity
@Data
public class PromotionImage {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500) // URL 可能很長
    private String imageUrl;

    @Column(name = "display_order")
    private int displayOrder = 0;
    
}