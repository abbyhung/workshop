package com.ah.workshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 商品操作dto。
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Data
public class ProductOperationRequest {
    @NotBlank(message = "商品名稱不可為空")
    private String name;               // 商品名稱
    @NotBlank(message = "商品描述不可為空")
    private String description;        // 商品描述
    @NotBlank(message = "商品分類不可為空")
    private String category;           // 商品分類
    
    @NotNull(message = "商品價格不可為空")
    @Min(value = 1, message = "商品價格至少為 1")
    private Integer price;             // 商品價格
    
    @NotNull(message = "商品成本不可為空")
    @Min(value = 1, message = "商品成本至少為 1")
    private Integer cost;              // 商品成本
    
    @NotNull(message = "數量不可為空")
    @Min(value = 1, message = "數量至少為 1")
    private Integer quantity;          // 數量
    
    private Long productId;            // 商品ID
    
    private boolean published = true;  // 是否上架
}
