package com.ah.workshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 存放一頁式畫面上方展示商品的圖片網址實體
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
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