package com.ah.workshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 訂單明細dto。
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Data
public class OrderDetailRequest {
	private Long orderId;      // 訂單編號
	
    @NotNull(message = "商品 ID 不可為空")
	private Long productId;    // 商品編號
    private String name;       // 商品名稱
    private Integer unitprice; // 單價
    
    @NotNull(message = "數量不可為空")
    @Min(value = 1, message = "購買數量至少為 1")
    private Integer quantity;  // 數量
}
