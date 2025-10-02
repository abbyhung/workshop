package com.ah.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ah.workshop.entity.ProductOperateHistory;

/**
 * 商品操作記錄Repository
 */
public interface ProductOperateHistoryRepository extends JpaRepository<ProductOperateHistory, Long> {
	/**
	 * 根據商品 ID 查詢所有操作記錄，並依照操作日期由新至舊排序。
	 * 
	 * @param productId 商品 ID
	 * @return 符合條件的操作記錄
	 */
	@Query("SELECT poh FROM ProductOperateHistory poh WHERE poh.productId = :productId ORDER BY poh.operationTime DESC")
	List<ProductOperateHistory> findOperateHistoryRecordList(@Param("productId") Long productId);

}