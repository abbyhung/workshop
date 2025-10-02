package com.ah.workshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


/**
 * 商品主檔實體
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Entity
@Data
public class ProductMain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, length = 500) // 名稱 可能很長
    private String name;

    @Column(nullable = false, length = 500) // 描述 可能很長
    private String description;

    @Column(nullable = false, length = 500) // 分類 可能很長
    private String category;

    @Column(nullable = false) // 售價
    private Integer price;
    
    private boolean published = true; // 是否上架
    
    
    
    
}