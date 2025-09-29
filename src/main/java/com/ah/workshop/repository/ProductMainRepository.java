package com.ah.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ah.workshop.dto.ProductQureyResult;
import com.ah.workshop.entity.ProductMain;

public interface ProductMainRepository extends JpaRepository<ProductMain, Long> {
    /**
     * 根據商品名稱、描述、分類進行模糊查詢，並篩選上架狀態。
     * @param name 商品名稱的關鍵字
     * @param description 商品描述的關鍵字
     * @param category 商品分類的關鍵字
     * @param published 是否上架
     * @return 符合條件的商品列表
     */
	@Query( "SELECT NEW com.ah.workshop.dto.ProductQureyResult( "
	      + "       pm.productId, pm.name, pm.description, "
          + "       pm.category, pm.price, pm.published, "
	      + "       COALESCE(SUM(ps.quantity), 0L), 0L)"
          + "  FROM ProductMain pm "
          + "  LEFT JOIN ProductStock ps ON pm.productId = ps.productId "
          + " WHERE pm.name LIKE :name "
          + "   AND pm.description LIKE :description "
          + "   AND pm.category LIKE :category "
          + "   AND pm.published = :published "
          + " GROUP BY pm.productId, pm.name, pm.description, pm.category, pm.price, pm.published")
    List<ProductQureyResult> findProductList(@Param("name") String name, @Param("description") String description, 
			@Param("category") String category, @Param("published") boolean published);
	

	@Query( "SELECT NEW com.ah.workshop.dto.ProductQureyResult( "
	      + "       pm.productId, pm.name, pm.description, "
          + "       pm.category, pm.price, pm.published, "
	      + "       COALESCE(SUM(ps.quantity), 0L), 0L)"
          + "  FROM ProductMain pm "
          + "  LEFT JOIN ProductStock ps ON pm.productId = ps.productId "
          + " WHERE pm.productId = :productId "
          + " GROUP BY pm.productId, pm.name, pm.description, pm.category, pm.price, pm.published")
    List<ProductQureyResult> findProduct(@Param("productId") Long productId);
}