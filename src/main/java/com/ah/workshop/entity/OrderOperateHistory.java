package com.ah.workshop.entity;

import java.time.LocalDateTime;

import com.ah.workshop.entity.enums.OrderOperationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 訂單操作記錄實體
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Entity
@Data
public class OrderOperateHistory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                     // 流水號
	
	@Column(nullable = false)
    private Long orderId;                // 訂單編號

    @Column(nullable = false)
    private LocalDateTime operationTime; // 操作時間

    @Column(nullable = false, length = 20)
    private OrderOperationType operate;  // 操作類型

}
