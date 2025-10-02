package com.ah.workshop.api.admin;

import com.ah.workshop.entity.PromotionImage;
import com.ah.workshop.service.PromotionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 處理後台管理上方廣告圖片網址相關的 API Controller。
 * <p>
 * 基礎路徑為 /api/admin/promotions
 * @version 1.0
 * @since 2025-10-02
 * @author abbyhung
 */
@RestController
@RequestMapping("/api/admin/promotions")
public class AdminPromotionController {

    @Autowired
    private PromotionImageService promotionImageService;

    /**
     * 取得所有圖片
     * @return
     */
    @GetMapping
    public List<PromotionImage> getAll() {
        return promotionImageService.findAllOrdered();
    }

    /**
     * 新增圖片網址
     * @param image
     * @return
     */
    @PostMapping
    public PromotionImage create(@RequestBody PromotionImage image) {
        return promotionImageService.save(image);
    }

    /**
     * 刪除圖片網址
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        promotionImageService.deleteById(id);
    }
}