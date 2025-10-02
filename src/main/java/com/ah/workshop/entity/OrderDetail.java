package com.ah.workshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 訂單明細實體
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Entity
@Data
public class OrderDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                // 流水號
	
	@Column(nullable = false)
    private Long orderId;           // 訂單編號
	
	@Column(nullable = false)
    private Long productId;         // 商品編號

    @Column(nullable = false)
    private Integer unitprice;      // 單價

    @Column(nullable = false)
    private Integer quantity;       // 數量

    @Column(nullable = false)
    private Integer amount;         // 小計

}
