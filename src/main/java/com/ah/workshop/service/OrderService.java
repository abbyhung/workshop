package com.ah.workshop.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ah.workshop.dto.OrderDetailRequest;
import com.ah.workshop.dto.OrderInfoRequest;
import com.ah.workshop.entity.OrderDetail;
import com.ah.workshop.entity.OrderMain;
import com.ah.workshop.entity.OrderOperateHistory;
import com.ah.workshop.entity.ProductMain;
import com.ah.workshop.entity.ProductStock;
import com.ah.workshop.entity.enums.OrderOperationType;
import com.ah.workshop.entity.enums.OrderStatusType;
import com.ah.workshop.exception.OrderOperationException;
import com.ah.workshop.repository.OrderDetailRepository;
import com.ah.workshop.repository.OrderMainRepository;
import com.ah.workshop.repository.OrderOperateHistoryRepository;
import com.ah.workshop.repository.ProductMainRepository;
import com.ah.workshop.repository.ProductStockRepository;

/*
 * 訂單Service
 */
@Service
public class OrderService {

	@Autowired
	private OrderMainRepository orderMainRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderOperateHistoryRepository orderOperateHistoryRepository;

	@Autowired
	private ProductMainRepository productMainRepository;

	@Autowired
	private ProductStockRepository productStockRepository;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 查詢多筆訂單
	 * @param orderId
	 * @param custNumber
	 * @return
	 */
	public List<OrderInfoRequest> queryOrderInfo(Long orderId, String custNumber) {
		// 以訂單編號或客戶電話查詢訂單
        List<OrderMain> orders = orderMainRepository.findByOrderIdOrCustNumber(orderId, custNumber);
        
        // 如果找不到任何主訂單，直接回傳空列表
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        
        return queryOrderInfo(orders);
    }
	
	/**
	 * 新增訂單
	 * @param request
	 */
	@Transactional
	public Long createOrder(OrderInfoRequest request) {
		LocalDateTime now = LocalDateTime.now();
		// 新增訂單
		OrderMain main = new OrderMain();
		main.setStatus(OrderStatusType.CREATED);
		main.setDate(now);
		main.setCustShip(request.getCustShip());
		main.setCustNumber(request.getCustNumber());
		main.setCustName(request.getCustName());
		main.setComment(request.getComment());
		main.setTotalAmount(request.getTotalAmount());
		
		OrderMain savedMain = orderMainRepository.save(main);
		
		// 依據訂單明細新增
		for (OrderDetailRequest item : request.getDetails()) {
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(savedMain.getOrderId());
			detail.setAmount(item.getQuantity() * item.getUnitprice());
			detail.setProductId(item.getProductId());
			detail.setQuantity(item.getQuantity());
			detail.setUnitprice(item.getUnitprice());
			
			orderDetailRepository.save(detail);
		}
		
		// 新增操作記錄
		OrderOperateHistory operateHistory = new OrderOperateHistory();
		operateHistory.setOperate(OrderOperationType.CREATE);
		operateHistory.setOperationTime(now);
		operateHistory.setOrderId(savedMain.getOrderId());
		orderOperateHistoryRepository.save(operateHistory);
		
		return savedMain.getOrderId();
	}
	
	/**
	 * 更新訂單
	 * @param request
	 */
	@Transactional
	public void updateOrder(OrderInfoRequest request) {
		Optional<OrderMain> optional = orderMainRepository.findById(request.getOrderId());
		if(!optional.isPresent()) 
        	throw new OrderOperationException("查無訂單ID為「" + request.getOrderId() + "」！");
		
		LocalDateTime now = LocalDateTime.now();
		// 更新訂單
		OrderMain main = optional.get();
		main.setCustShip(request.getCustShip());
		main.setCustNumber(request.getCustNumber());
		main.setCustName(request.getCustName());
		main.setComment(request.getComment());
		main.setTotalAmount(request.getTotalAmount());
		
		OrderMain savedMain = orderMainRepository.save(main);
		
		// 先刪除後新增
		orderDetailRepository.deleteByOrderId(savedMain.getOrderId());
		
		// 依據訂單明細新增
		for (OrderDetailRequest item : request.getDetails()) {
			OrderDetail detail = new OrderDetail();
			detail.setOrderId(savedMain.getOrderId());
			detail.setAmount(item.getQuantity() * item.getUnitprice());
			detail.setProductId(item.getProductId());
			detail.setQuantity(item.getQuantity());
			detail.setUnitprice(item.getUnitprice());
			
			orderDetailRepository.save(detail);
		}
		
		// 新增操作記錄
		OrderOperateHistory operateHistory = new OrderOperateHistory();
		operateHistory.setOperate(OrderOperationType.UPDATE);
		operateHistory.setOperationTime(now);
		operateHistory.setOrderId(savedMain.getOrderId());
		orderOperateHistoryRepository.save(operateHistory);
	}
	
	/**
	 * 刪除訂單
	 * @param orderId
	 */
	@Transactional
	public void deleteOrder(Long orderId) {
		Optional<OrderMain> optional = orderMainRepository.findById(orderId);
	    OrderMain order = orderMainRepository.findById(orderId)
	            .orElseThrow(() -> new OrderOperationException("找不到 ID 為 " + orderId + " 的訂單"));

	    if (order.getStatus() != OrderStatusType.CREATED) {
	        throw new OrderOperationException("訂單狀態為「" + order.getStatus().getDescription() + "」，不允許刪除！");
	    }
		
		LocalDateTime now = LocalDateTime.now();
		// 刪除訂單
		OrderMain main = optional.get();
		main.setStatus(OrderStatusType.DELETED);
		OrderMain savedMain = orderMainRepository.save(main);
		
		// 新增操作記錄
		OrderOperateHistory operateHistory = new OrderOperateHistory();
		operateHistory.setOperate(OrderOperationType.DELETE);
		operateHistory.setOperationTime(now);
		operateHistory.setOrderId(savedMain.getOrderId());
		orderOperateHistoryRepository.save(operateHistory);
	}
	
	/**
	 * 出貨
	 * @param orderId
	 */
	@Transactional
	public void orderShip(Long orderId) {
		Optional<OrderMain> optional = orderMainRepository.findById(orderId);

		if(!optional.isPresent()) 
        	throw new OrderOperationException("查無訂單ID為「" + orderId + "」！");
		
        List<Long> orderIds = Arrays.asList(orderId);
        List<OrderDetail> allDetails = orderDetailRepository.findByOrderIdIn(orderIds);

		if(allDetails.isEmpty()) 
        	throw new OrderOperationException("訂單ID為「" + orderId + "」，無訂單明細無法出貨！");
        
        for (OrderDetail orderDetail : allDetails) {
            // 1. 針對最舊的庫存進行異動
            List<ProductStock> productStocks = productStockRepository.findAvailableStockForUpdate(orderDetail.getProductId());
            // 記錄剩餘數量與庫存異動
            int remainQuantity = orderDetail.getQuantity();
            for (ProductStock stock : productStocks) {
				if(stock.getQuantity() >= remainQuantity) {
					int temp = stock.getQuantity() - remainQuantity;
					
					productService.ship(stock.getProductId(), stock.getStockId(), orderId, 
							temp, remainQuantity, orderDetail.getUnitprice());
					remainQuantity = 0;
				} else {
					int temp = stock.getQuantity();
					productService.ship(stock.getProductId(), stock.getStockId(), orderId, 
							0, stock.getQuantity(), orderDetail.getUnitprice());
					remainQuantity = remainQuantity - temp;
				}
				
				if(remainQuantity == 0) break;
			}
            
            ProductMain productMain = productMainRepository.findById(orderDetail.getProductId()).get();
            
            if(remainQuantity > 0) {
            	throw new OrderOperationException("商品名稱為「" + productMain.getName() + "」庫存不足，無法出貨請先補貨！");
            }
		}
        
        // 3. 更新訂單狀態 & 新增操作記錄
		LocalDateTime now = LocalDateTime.now();
		OrderMain main = optional.get();
		main.setStatus(OrderStatusType.SHIPPED);
		OrderMain savedMain = orderMainRepository.save(main);
		
		// 新增操作記錄
		OrderOperateHistory operateHistory = new OrderOperateHistory();
		operateHistory.setOperate(OrderOperationType.SHIP);
		operateHistory.setOperationTime(now);
		operateHistory.setOrderId(savedMain.getOrderId());
		orderOperateHistoryRepository.save(operateHistory);
	}
	
	/**
	 * 訂單管理查詢
	 * @param unship
	 * @return
	 */
	public List<OrderInfoRequest> queryOrderInfo(Boolean unship) {
		// === 查詢主檔 (OrderMain) ===
        List<OrderMain> orders = null;
        
        if(unship != null) {
        	if(unship)
        		orders = orderMainRepository.findByStatusOrderByOrderIdAsc(OrderStatusType.CREATED);
        	else {
        		orders = orderMainRepository.findByStatusNotOrderByOrderIdAsc(OrderStatusType.CREATED);
        	}
        }
        
        // 如果找不到任何主訂單，直接回傳空列表
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        
        return queryOrderInfo(orders);
    }
	
	private List<OrderInfoRequest> queryOrderInfo(List<OrderMain> orders) {
        // === 收集所有訂單編號 ===
        List<Long> orderIds = orders.stream()
                                    .map(OrderMain::getOrderId)
                                    .collect(Collectors.toList());

        // === 一次性查詢所有相關的訂單明細 (OrderDetail) ===
        List<OrderDetail> allDetails = orderDetailRepository.findByOrderIdIn(orderIds);
        
        // === 將明細列表轉換成 Map，方便快速查找 ===
        Map<Long, List<OrderDetail>> detailsMap = allDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getOrderId));

        // === 組合最終的結果清單 ===
        return orders.stream().map(order -> {
            OrderInfoRequest dto = new OrderInfoRequest();
            
            // 複製 OrderMain 的屬性到 DTO
            dto.setOrderId(order.getOrderId());
            dto.setDate(order.getDate());
            dto.setCustName(order.getCustName());
            dto.setCustNumber(order.getCustNumber());
            dto.setCustShip(order.getCustShip());
            dto.setComment(order.getComment());
            dto.setTotalAmount(order.getTotalAmount());

            if (order.getStatus() != null) {
                dto.setStatus(order.getStatus().name());
                dto.setStatusDescription(order.getStatus().getDescription());
            }

            // 從 Map 中獲取該訂單的明細列表
            List<OrderDetail> details = detailsMap.getOrDefault(order.getOrderId(), Collections.emptyList());

            // 將 List<OrderDetail> 轉換成 List<OrderDetailRequest>
            List<OrderDetailRequest> detailDtos = details.stream().map(detail -> {
                OrderDetailRequest detailDto = new OrderDetailRequest();
                detailDto.setOrderId(detail.getOrderId());
                detailDto.setQuantity(detail.getQuantity());
                detailDto.setUnitprice(detail.getUnitprice());
                
                ProductMain productMain = productMainRepository.findById(detail.getProductId()).get();
                
                if(productMain != null) {
                    detailDto.setProductId(productMain.getProductId());
                    detailDto.setName(productMain.getName());
                }
                
                return detailDto;
            }).collect(Collectors.toList());
            
            dto.setDetails(detailDtos);
            
            return dto;
        }).collect(Collectors.toList());
	}
	
}