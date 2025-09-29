package com.ah.workshop.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ah.workshop.dto.OrderInfoRequest;
import com.ah.workshop.service.OrderService;
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 查詢訂單
     * @param unship
     * @return
     */
    @GetMapping("/query")
    public List<OrderInfoRequest> query(
    		@RequestParam(required = true) Boolean unship) {
        return orderService.queryOrderInfo(unship);
    }


    /**
     * 訂單出貨
     * @param id
     * @return
     * @throws Exception 
     */
    @PostMapping("/ship")
    public ResponseEntity<?> ship(@RequestParam Long id) {
    	orderService.orderShip(id);
		return ResponseEntity.ok("更新成功");
    }
}
