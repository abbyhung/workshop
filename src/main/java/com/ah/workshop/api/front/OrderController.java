package com.ah.workshop.api.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ah.workshop.dto.OrderInfoRequest;
import com.ah.workshop.dto.ProductQureyResult;
import com.ah.workshop.service.OrderService;
import com.ah.workshop.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    
    /**
     * 建立訂單
     * @param request
     * @return
     */
    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderInfoRequest request) {
    	Long orderId = orderService.createOrder(request);

        return ResponseEntity.ok(orderId);
    }
    
    /**
     * 查詢訂單
     * @param orderId
     * @param custNumber
     * @return
     */
    @GetMapping("/query")
    public List<OrderInfoRequest> query(
    		@RequestParam(required = false) Long orderId,
    		@RequestParam(required = false) String custNumber) {
        return orderService.queryOrderInfo(orderId, custNumber);
    }

    /**
     * 刪除訂單
     * @param id
     * @return
     * @throws Exception 
     */
    @PostMapping("/deleteOrder")
    public ResponseEntity<?> deleteOrder(@RequestParam Long id) throws Exception {
    	orderService.deleteOrder(id);
		return ResponseEntity.ok("更新成功");
    }
	
}
