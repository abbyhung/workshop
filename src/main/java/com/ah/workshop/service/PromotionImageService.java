package com.ah.workshop.service;

import com.ah.workshop.entity.PromotionImage;
import com.ah.workshop.repository.PromotionImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 上方圖片Service
 */
@Service
public class PromotionImageService {

    @Autowired
    private PromotionImageRepository promotionImageRepository;

    /**
     * 查詢全部圖片網址
     * @return
     */
    public List<PromotionImage> findAllOrdered() {
        return promotionImageRepository.findAllByOrderByDisplayOrderAsc();
    }

    /**
     * 新增圖片網址
     * @param image
     * @return
     */
    public PromotionImage save(PromotionImage image) {
        return promotionImageRepository.save(image);
    }

    /**
     * 刪除圖片網址
     * @param id
     */
    public void deleteById(Long id) {
        promotionImageRepository.deleteById(id);
    }
}