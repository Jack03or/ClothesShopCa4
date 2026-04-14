package com.softwarepatterns.Clothes_ShopCa4.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softwarepatterns.Clothes_ShopCa4.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductIdOrderByCreatedAtDesc(Long productId);

}
