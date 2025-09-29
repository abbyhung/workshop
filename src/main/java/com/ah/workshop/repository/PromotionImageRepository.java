package com.ah.workshop.repository;

import com.ah.workshop.entity.PromotionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PromotionImageRepository extends JpaRepository<PromotionImage, Long> {
    // 增加一個方法，讓圖片可以依照 displayOrder 排序
    List<PromotionImage> findAllByOrderByDisplayOrderAsc();
}