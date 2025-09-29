package com.ah.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ah.workshop.entity.OrderOperateHistory;

public interface OrderOperateHistoryRepository extends JpaRepository<OrderOperateHistory, Long> {

	@Query("SELECT poh FROM OrderOperateHistory poh WHERE poh.orderId = :orderId ORDER BY poh.operationTime DESC")
	List<OrderOperateHistory> findOperateHistoryRecordList(@Param("orderId") Long orderId);

}