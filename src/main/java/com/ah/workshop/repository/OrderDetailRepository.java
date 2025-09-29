package com.ah.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ah.workshop.entity.OrderDetail;

/**
 * 商品庫存Repository
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	// 自動產生 WHERE orderId IN (?, ?, ...) 的查詢
    List<OrderDetail> findByOrderIdIn(List<Long> orderIds);

	/**
     * 根據訂單 ID 刪除所有訂單明細
     * @param orderId 訂單 ID
     */
    @Modifying
    @Query("DELETE FROM OrderDetail od WHERE od.orderId = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);

}