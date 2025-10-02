package com.ah.workshop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ah.workshop.dto.ProductOperationRequest;
import com.ah.workshop.dto.ProductQureyResult;
import com.ah.workshop.entity.ProductMain;
import com.ah.workshop.entity.ProductOperateHistory;
import com.ah.workshop.entity.ProductStock;
import com.ah.workshop.entity.enums.OrderStatusType;
import com.ah.workshop.entity.enums.ProductOperationType;
import com.ah.workshop.exception.OrderOperationException;
import com.ah.workshop.repository.OrderMainRepository;
import com.ah.workshop.repository.ProductMainRepository;
import com.ah.workshop.repository.ProductOperateHistoryRepository;
import com.ah.workshop.repository.ProductStockRepository;

/**
 * 商品Service
 */
@Service
public class ProductService {

	@Autowired
	private ProductMainRepository productMainRepository;

	@Autowired
	private ProductOperateHistoryRepository productOperateHistoryRepository;

	@Autowired
	private ProductStockRepository productStockRepository;

	@Autowired
	private OrderMainRepository orderMainRepository;

	/**
	 * 查詢
	 * 
	 * @param name
	 * @param description
	 * @param category
	 * @param published
	 * @return
	 */
	public List<ProductQureyResult> findProductList(String name, String description, String category,
			boolean published) {
		List<ProductQureyResult> products = productMainRepository.findProductList(name, description, category,
				published);

		for (ProductQureyResult productQureyResult : products) {
			Long qty = orderMainRepository.findTotalQuantityByProductAndStatus(productQureyResult.getProductId(),
					OrderStatusType.CREATED);

			productQureyResult.setPreOrderStock(qty);
		}

		return products;
	}

	/**
	 * 查詢商品庫存記錄
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductStock> findProductStockRecord(Long productId) {
		return productStockRepository.findAvailableStockOrderedByDate(productId);
	}

	/**
	 * 查詢商品單筆
	 * 
	 * @param productId
	 * @return
	 */
	public ProductQureyResult findProduct(Long productId) {
		List<ProductQureyResult> products = productMainRepository.findProduct(productId);

		if (products.isEmpty())
			throw new OrderOperationException("查無商品ID為「" + productId + "」！");

		for (ProductQureyResult productQureyResult : products) {
			Long qty = orderMainRepository.findTotalQuantityByProductAndStatus(productQureyResult.getProductId(),
					OrderStatusType.CREATED);

			productQureyResult.setTotalStock(productQureyResult.getTotalStock() - qty);
		}

		return products.get(0);
	}

	/**
	 * 新增商品及庫存
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	public ProductMain createProduct(ProductOperationRequest request) {
		// 建立並儲存 Product 物件
		ProductMain product = new ProductMain();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setCategory(request.getCategory());
		ProductMain savedProduct = productMainRepository.save(product);
		LocalDateTime now = LocalDateTime.now();

		// 新增庫存
		ProductStock stock = new ProductStock();
		stock.setProductId(savedProduct.getProductId());
		stock.setDate(now);
		stock.setCost(request.getCost());
		stock.setQuantity(request.getQuantity());
		ProductStock savedStock = productStockRepository.save(stock);

		// 新增操作記錄
		ProductOperateHistory history = new ProductOperateHistory();
		history.setProductId(savedProduct.getProductId()); // 關聯到剛剛新增的商品 ID
		history.setStockId(savedStock.getStockId()); // 關聯到剛剛新增的商品庫存 ID
		history.setOperate(ProductOperationType.CREATE);
		history.setQuantity(request.getQuantity());
		history.setCost(request.getCost());
		history.setOperationTime(now);

		productOperateHistoryRepository.save(history);

		return savedProduct;
	}

	/**
	 * 更新商品
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	public void updateProduct(ProductOperationRequest request) {
		ProductMain product = productMainRepository.findById(request.getProductId())
				.orElseThrow(() -> new OrderOperationException("找不到 ID 為 " + request.getProductId() + " 的商品"));

		// 更新商品資訊
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setCategory(request.getCategory());
		productMainRepository.save(product);

		// 新增操作記錄
		ProductOperateHistory history = new ProductOperateHistory();
		history.setProductId(product.getProductId());
		history.setOperate(ProductOperationType.UPDATE);
		history.setOperationTime(LocalDateTime.now());
		productOperateHistoryRepository.save(history);
	}

	/**
	 * 更新庫存
	 * 
	 * @param request
	 */
	@Transactional
	public void restock(ProductOperationRequest request) {
		ProductMain product = productMainRepository.findById(request.getProductId())
				.orElseThrow(() -> new OrderOperationException("找不到 ID 為 " + request.getProductId() + " 的商品"));
		LocalDateTime now = LocalDateTime.now();

		// 新增庫存
		ProductStock stock = new ProductStock();
		stock.setProductId(product.getProductId());
		stock.setDate(now);
		stock.setCost(request.getCost());
		stock.setQuantity(request.getQuantity());
		ProductStock savedStock = productStockRepository.save(stock);

		// 新增操作記錄
		ProductOperateHistory history = new ProductOperateHistory();
		history.setProductId(product.getProductId()); // 關聯到剛剛新增的商品 ID
		history.setStockId(savedStock.getStockId()); // 關聯到剛剛新增的商品庫存 ID
		history.setOperate(ProductOperationType.RESTOCK);
		history.setQuantity(request.getQuantity());
		history.setCost(request.getCost());
		history.setOperationTime(now);

		productOperateHistoryRepository.save(history);
	}

	/**
	 * 刪除商品
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteProduct(Long id) {
		ProductMain productMain = productMainRepository.findById(id)
				.orElseThrow(() -> new OrderOperationException("找不到 ID 為 " + id + " 的商品"));
		// 新增操作記錄
		ProductOperateHistory history = new ProductOperateHistory();
		history.setProductId(productMain.getProductId());
		history.setOperate(ProductOperationType.DELETE);
		history.setOperationTime(LocalDateTime.now());
		productOperateHistoryRepository.save(history);

		// 根據商品ID刪除資料
		productMainRepository.deleteById(id);
	}

	/**
	 * 上架/下架
	 * 
	 * @param request
	 */
	@Transactional
	public void setProductPublish(ProductOperationRequest request) {
		ProductMain product = productMainRepository.findById(request.getProductId())
				.orElseThrow(() -> new OrderOperationException("找不到 ID 為 " + request.getProductId() + " 的商品"));
		ProductOperationType operate = ProductOperationType.PUBLISH;
		if (product.isPublished()) {
			operate = ProductOperationType.UNPUBLISH;
		}

		// 更新上架狀態
		product.setPublished(!product.isPublished());
		productMainRepository.save(product);

		// 新增操作記錄
		ProductOperateHistory history = new ProductOperateHistory();
		history.setProductId(product.getProductId());
		history.setOperate(operate);
		history.setOperationTime(LocalDateTime.now());
		productOperateHistoryRepository.save(history);
	}

	/**
	 * 出貨
	 * 
	 * @param request
	 */
	@Transactional
	public void ship(Long productId, Long stockId, Long orderId, Integer quantity, Integer shipQuantity,
			Integer unitPrice) {
		ProductMain productMain = productMainRepository.findById(productId)
				.orElseThrow(() -> new OrderOperationException("找不到 ID 為 " + productId + " 的商品"));
		LocalDateTime now = LocalDateTime.now();

		ProductStock stock = productStockRepository.findById(stockId)
				.orElseThrow(() -> new OrderOperationException("找不到 ID 為 " + stockId + " 的商品庫存"));
		stock.setQuantity(quantity);
		productStockRepository.save(stock);

		// 新增操作記錄
		ProductOperateHistory history = new ProductOperateHistory();
		history.setProductId(productMain.getProductId()); // 關聯到商品 ID
		history.setStockId(stock.getStockId()); // 關聯到商品庫存 ID
		history.setOrderId(orderId);
		history.setOperate(ProductOperationType.SHIP);
		history.setQuantity(shipQuantity);
		history.setCost(stock.getCost());
		history.setPrice(unitPrice);
		history.setOperationTime(now);

		productOperateHistoryRepository.save(history);
	}
}