package com.ah.workshop.service;

import com.ah.workshop.entity.PromotionImage;
import com.ah.workshop.repository.PromotionImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PromotionImageService {

    @Autowired
    private PromotionImageRepository promotionImageRepository;

    public List<PromotionImage> findAllOrdered() {
        return promotionImageRepository.findAllByOrderByDisplayOrderAsc();
    }

    public PromotionImage save(PromotionImage image) {
        return promotionImageRepository.save(image);
    }

    public void deleteById(Long id) {
        promotionImageRepository.deleteById(id);
    }
}