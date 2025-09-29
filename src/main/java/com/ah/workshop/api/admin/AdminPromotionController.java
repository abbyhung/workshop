package com.ah.workshop.api.admin;

import com.ah.workshop.entity.PromotionImage;
import com.ah.workshop.service.PromotionImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/promotions")
public class AdminPromotionController {

    @Autowired
    private PromotionImageService promotionImageService;

    @GetMapping
    public List<PromotionImage> getAll() {
        return promotionImageService.findAllOrdered();
    }

    @PostMapping
    public PromotionImage create(@RequestBody PromotionImage image) {
        return promotionImageService.save(image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        promotionImageService.deleteById(id);
    }
}