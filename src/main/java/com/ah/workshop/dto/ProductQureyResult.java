package com.ah.workshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品查詢結果dto。
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Data
@NoArgsConstructor
@AllArgsConstructor // Lombok: 產生一個包含所有參數的建構子，這對 JPQL 非常重要
public class ProductQureyResult {
    
    private Long productId;            // 商品ID
    private String name;               // 商品名稱
    private String description;        // 商品描述
    private String category;           // 商品分類
    private Integer price;             // 商品價格
    private boolean published;         // 是否上架

    // 加上額外的統計欄位
    private Long totalStock;           // 當前庫存
    private Long preOrderStock;        // 尚未出貨
}
