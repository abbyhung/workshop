package com.ah.workshop.entity;

import java.time.LocalDateTime;

import com.ah.workshop.entity.enums.ProductOperationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 商品操作記錄實體
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Entity
@Data
public class ProductOperateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                       // id

    @Column(nullable = false)
    private Long productId;                // 商品編號

    @Column
    private Long orderId;                  // 訂單編號

    @Column
    private Long stockId;                  // 庫存ID

    @Column(nullable = false, length = 20)
    private ProductOperationType operate;  // 操作類型
    
    @Column
    private Integer cost;                  // 成本
    
    @Column
    private Integer price;                 // 售價
    
    @Column
    private Integer quantity;              // 數量

    @Column(nullable = false)
    private LocalDateTime operationTime;   // 操作時間
}