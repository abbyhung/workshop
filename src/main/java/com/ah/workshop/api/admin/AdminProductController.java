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

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    /**
     * 查詢
     * @param request
     * @return
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
     * 查詢
     * @param request
     * @return
     */
    @GetMapping("/queryOne")
    public List<ProductQureyResult> queryOne(
    		@RequestParam(required = true) Long productId) {
        return productService.findProduct(productId);
    }

    /**
     * 查詢
     * @param request
     * @return
     */
    @GetMapping("/queryStockRecord")
    public List<ProductStock> queryStockRecord(
            @RequestParam(required = true) Long productId) {
        return productService.findProductStockRecord(productId);
    }

    /**
     * 新增商品
     * @param request
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductOperationRequest request) {
        productService.createProduct(request);

        return ResponseEntity.ok("更新成功");
    }

    /**
     * 更新商品描述
     * @param request
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductOperationRequest request) {
        productService.updateProduct(request);

        return ResponseEntity.ok("更新成功");
    }


    /**
     * 更新庫存
     * @param request
     * @return
     */
    @PostMapping("/restock")
    public ResponseEntity<?> restock(@RequestBody ProductOperationRequest request) {
        productService.restock(request);
        return ResponseEntity.ok("更新成功");
    }


    /**
     * 刪除商品
     * @param id
     * @return
     */
    @PostMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("更新成功");
    }


    /**
     * 上架/下架
     * @param id
     * @return
     */
    @PostMapping("/setProductPublish")
    public ResponseEntity<?> setProductPublish(@RequestBody ProductOperationRequest request) {
        productService.setProductPublish(request);
        return ResponseEntity.ok("更新成功");
    }
}
