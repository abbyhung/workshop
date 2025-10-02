package com.ah.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ah.workshop.entity.ProductStock;

import jakarta.persistence.LockModeType;

/**
 * 商品庫存Repository
 */
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
	
	/**
	 * 根據商品 ID 查詢所有可用庫存 (數量 > 0)，並依照入庫日期由舊至新排序。
	 * 
	 * @param productId 商品 ID
	 * @return 符合條件的庫存列表
	 */
	@Query("SELECT ps FROM ProductStock ps WHERE ps.productId = :productId AND ps.quantity > 0 ORDER BY ps.date ASC")
	List<ProductStock> findAvailableStockOrderedByDate(@Param("productId") Long productId);
	
	/**
	 * 根據商品 ID 查詢所有可用庫存 (數量 > 0)，並依照入庫日期由舊至新排序。 (加入lock)
	 * @param productId
	 * @return
	 */
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT ps FROM ProductStock ps WHERE ps.productId = :productId AND ps.quantity > 0 ORDER BY ps.date ASC")
	List<ProductStock> findAvailableStockForUpdate(@Param("productId") Long productId);

}