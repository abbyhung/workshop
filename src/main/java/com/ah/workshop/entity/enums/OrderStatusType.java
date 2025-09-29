package com.ah.workshop.entity.enums;

/**
 * 訂單狀態enum
 */
public enum OrderStatusType {
    CREATED("已成立"),
    DELETED("刪除"),
    SHIPPED("已出貨");

    private final String description;

    OrderStatusType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}