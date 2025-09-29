package com.ah.workshop.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * ProductMain: 商品庫存
 */
@Entity
@Data
public class ProductStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;       // 庫存流水號

    @Column(nullable = false)
    private Long productId;     // 對應商品編號

    @Column(nullable = false)
    private LocalDateTime date; // 入庫日期

    @Column(nullable = false)
    private Integer cost;       // 成本

    @Column(nullable = false)
    private Integer quantity;   // 數量

}