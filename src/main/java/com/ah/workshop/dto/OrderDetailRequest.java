package com.ah.workshop.dto;

import lombok.Data;

@Data
public class OrderDetailRequest {
	private Long orderId;
	private Long productId;
    private String name;
    private Integer unitprice;
    
    private Integer quantity;
}
