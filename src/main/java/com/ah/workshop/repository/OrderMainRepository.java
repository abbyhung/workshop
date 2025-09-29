package com.ah.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ah.workshop.dto.ProductQureyResult;
import com.ah.workshop.entity.OrderMain;
import com.ah.workshop.entity.enums.OrderStatusType;

public interface OrderMainRepository extends JpaRepository<OrderMain, Long> {
	
	//自動產生 WHERE orderId = ? OR custNumber = ? 的查詢
	List<OrderMain> findByOrderIdOrCustNumber(Long orderId, String custNumber);
	
    /**
     * 查詢所有狀態為 CREATED 的訂單，並依訂單編號升冪排序。
     * @param status 要查詢的狀態 (傳入 OrderStatusType.CREATED)
     * @return 符合條件的訂單列表
     */
    List<OrderMain> findByStatusOrderByOrderIdAsc(OrderStatusType status);

    /**
     * 查詢所有狀態「不為」指定狀態的訂單，並依訂單編號升冪排序。
     * @param status 要排除的狀態 (傳入 OrderStatusType.CREATED)
     * @return 符合條件的訂單列表
     */
    List<OrderMain> findByStatusNotOrderByOrderIdAsc(OrderStatusType status);
    
    /**
     * 根據商品 ID，加總所有處於特定狀態的訂單中，該商品的訂購數量。
     * @param productId 商品 ID
     * @param status 訂單狀態
     * @return 該商品的訂購總數
     */
    @Query("SELECT COALESCE(SUM(od.quantity), 0L) " +
           "FROM OrderDetail od JOIN OrderMain om ON od.orderId = om.orderId " +
           "WHERE od.productId = :productId AND om.status = :status")
    Long findTotalQuantityByProductAndStatus(
            @Param("productId") Long productId,
            @Param("status") OrderStatusType status
    );
}