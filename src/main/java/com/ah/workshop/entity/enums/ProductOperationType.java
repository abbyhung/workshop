package com.ah.workshop.entity.enums;

/**
 * 商品操作enum
 */
public enum ProductOperationType {
    CREATE("新增"),
    UPDATE("編輯"),
    DELETE("刪除"),
    RESTOCK("進貨"),
    SHIP("出貨"),
    PUBLISH("上架"),
    UNPUBLISH("下架");

    private final String description;

    ProductOperationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}