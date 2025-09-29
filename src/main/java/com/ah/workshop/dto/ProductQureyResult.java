package com.ah.workshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // Lombok: 產生一個包含所有參數的建構子，這對 JPQL 非常重要
public class ProductQureyResult {
    // 重新宣告所有需要的欄位
    private Long productId;
    private String name;
    private String description;
    private String category;
    private Integer price;
    private boolean published;

    // 加上額外的統計欄位
    private Long totalStock;
    private Long preOrderStock;
}
