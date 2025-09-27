// 檔案路徑: src/main/java/com/ah/workshop/entity/Product.java
package com.ah.workshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // 告訴 JPA 這是一個實體類別，對應到資料庫的一張資料表
@Data   // Lombok 註解：自動生成 getter, setter, toString 等方法
public class Product {

    @Id // 標示此欄位為主鍵 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主鍵生成策略：自動增長
    private Long id;

    @Column(nullable = false, length = 100) // 資料表欄位設定：不允許 null, 長度 100
    private String name;

    @Column(length = 50)
    private String category;

    @Column(nullable = false)
    private Integer price;

    private boolean published = true; // Java 中的 boolean 會對應到資料庫的 bit 或 boolean 類型
}