package com.softwarepatterns.Clothes_ShopCa4.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softwarepatterns.Clothes_ShopCa4.model.Review;
import com.softwarepatterns.Clothes_ShopCa4.model.ReviewRequest;
import com.softwarepatterns.Clothes_ShopCa4.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public String addReview(@RequestBody ReviewRequest request) {
        return reviewService.addReview(request);
    }

    @GetMapping("/product")
    public List<Review> getReviewsForProduct(@RequestParam Long productId) {
        return reviewService.getReviewsForProduct(productId);
    }
}
