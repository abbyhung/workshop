package com.ah.workshop.dto;

import lombok.Data;

@Data
public class ProductQureyRequest {
    // 欄位名稱要和前端傳來的 JSON key 完全對應
    private String name;
    private String description;
    private String category;
    
    private boolean published; // 是否上架
}
