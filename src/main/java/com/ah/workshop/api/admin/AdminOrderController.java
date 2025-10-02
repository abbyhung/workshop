package com.ah.workshop.api.admin;

import com.ah.workshop.dto.OrderInfoRequest;
import com.ah.workshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 處理後台訂單管理相關的 API Controller。
 * <p>
 * 基礎路徑為 /api/admin/orders
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 根據狀態查詢訂單列表。
     *
     * @param isUnshipped 查詢條件。傳入 `true` 查詢所有未出貨 (狀態為 CREATED) 的訂單，
     * 傳入 `false` 查詢所有非 CREATED 狀態的訂單。
     * @return 訂單資訊 DTO 列表 (List<OrderInfoRequest>)
     */
    @GetMapping("/query")
    public List<OrderInfoRequest> query(@RequestParam(name = "unship", required = true) Boolean isUnshipped) {
        return orderService.queryOrderInfo(isUnshipped);
    }

    /**
     * 將指定 ID 的訂單狀態更新為「已出貨」。
     *
     * @param id 要出貨的訂單 ID
     * @return 成功時回傳包含 "更新成功" 訊息的 ResponseEntity
     */
    @PostMapping("/ship")
    public ResponseEntity<String> ship(@RequestParam Long id) {
        // 假設 orderService.orderShip 會在找不到訂單或狀態不對時拋出 RuntimeException
        // 該例外會由 GlobalExceptionHandler 統一捕捉並回傳 4xx/5xx 錯誤給前端
        orderService.orderShip(id);
        
        // 只有在 Service 成功執行完畢後，才會回傳 200 OK
        return ResponseEntity.ok("更新成功");
    }
}