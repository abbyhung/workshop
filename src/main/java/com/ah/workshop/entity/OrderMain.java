package com.ah.workshop.entity;

import java.time.LocalDateTime;

import com.ah.workshop.entity.enums.OrderStatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * 訂單主檔實體
 * 
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@Entity
@Data
public class OrderMain {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;           // 訂單編號

    @Column(nullable = false)
    private LocalDateTime date;     // 訂單時間

    @Column(nullable = false, length = 60)
    private String custName;        // 客戶姓名

    @Column(nullable = false, length = 10)
    private String custNumber;      // 客戶電話

    @Column(nullable = false, length = 60)
    private String custShip;        // 出貨店名

    @Column(length = 500)
    private String comment;         // 備註

    @Column(nullable = false)
    private OrderStatusType status; // 狀態

    @Column(nullable = false)
    private Integer totalAmount;    // 總金額

}
