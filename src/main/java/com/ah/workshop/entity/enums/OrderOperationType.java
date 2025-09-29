package com.ah.workshop.entity.enums;

/**
 * 訂單操作enum
 */
public enum OrderOperationType {
    CREATE("新增"),
    UPDATE("編輯"),
    DELETE("刪除"),
    SHIP("出貨");

    private final String description;

    OrderOperationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}