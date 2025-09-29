package com.ah.workshop.dto;

import lombok.Data;

@Data
public class ProductOperationRequest {
    // 欄位名稱要和前端傳來的 JSON key 完全對應
    private String name;
    private String description;
    private String category;
    private Integer price;
    private Integer cost;
    private Integer quantity;
    
    private Long productId;
    
    private boolean published = true; // 是否上架
}
