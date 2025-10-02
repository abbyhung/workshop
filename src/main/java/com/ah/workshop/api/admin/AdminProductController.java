package com.ah.workshop.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ah.workshop.dto.ProductOperationRequest;
import com.ah.workshop.dto.ProductQureyResult;
import com.ah.workshop.entity.ProductStock;
import com.ah.workshop.service.ProductService;

/**
 * 處理後台商品管理相關的 API Controller。
 * <p>
 * 基礎路徑為 /api/admin/products
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    /**
     * 根據條件模糊搜尋商品列表，並包含庫存總數。
     *
     * @param name        商品名稱關鍵字 (可選)
     * @param description 商品描述關鍵字 (可選)
     * @param category    商品分類關鍵字 (可選)
     * @param published   是否上架 (預設為 true)
     * @return 符合條件的商品 DTO 列表
     */
    @GetMapping("/query")
    public List<ProductQureyResult> queryProduct(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "true") boolean published) {
        return productService.findProductList(name, description, category, published);
    }

    /**
     * 根據商品 ID 精準查詢單一商品資訊 (包含庫存總數)。
     *
     * @param productId 商品 ID (必填)
     * @return 單一商品的 DTO
     */
    @GetMapping("/queryOne")
    public ProductQureyResult queryOne(
    		@RequestParam(required = true) Long productId) {
        return productService.findProduct(productId);
    }

    /**
     * 根據商品 ID 查詢該商品的所有庫存紀錄。
     *
     * @param productId 商品 ID (必填)
     * @return 該商品的庫存紀錄列表
     */
    @GetMapping("/queryStockRecord")
    public List<ProductStock> queryStockRecord(
            @RequestParam(required = true) Long productId) {
        return productService.findProductStockRecord(productId);
    }

    /**
     * 建立一個新商品及其初始庫存。
     *
     * @param request 包含商品資料與初始庫存的請求 DTO
     * @return 成功的文字訊息
     */
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductOperationRequest request) {
        productService.createProduct(request);

        return ResponseEntity.ok("更新成功");
    }

    /**
     * 更新一個已存在的商品基本資料。
     *
     * @param request 包含商品 ID 和要更新資料的請求 DTO
     * @return 成功的文字訊息
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductOperationRequest request) {
        productService.updateProduct(request);
        return ResponseEntity.ok("更新成功");
    }


    /**
     * 為已存在的商品增加庫存 (進貨)。
     *
     * @param request 包含商品 ID 和進貨數量的請求 DTO
     * @return 成功的文字訊息
     */
    @PostMapping("/restock")
    public ResponseEntity<?> restock(@RequestBody ProductOperationRequest request) {
        productService.restock(request);
        return ResponseEntity.ok("更新成功");
    }


    /**
     * 刪除一個商品。
     *
     * @param id 要刪除的商品 ID
     * @return 成功的文字訊息
     */
    @PostMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("更新成功");
    }


    /**
     * 設定商品的上架或下架狀態。
     *
     * @param request 包含商品 ID 和 published 狀態的請求 DTO
     * @return 成功的文字訊息
     */
    @PostMapping("/setProductPublish")
    public ResponseEntity<?> setProductPublish(@RequestBody ProductOperationRequest request) {
        productService.setProductPublish(request);
        return ResponseEntity.ok("更新成功");
    }
}
