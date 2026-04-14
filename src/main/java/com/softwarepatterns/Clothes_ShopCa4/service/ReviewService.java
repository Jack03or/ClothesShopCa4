package com.softwarepatterns.Clothes_ShopCa4.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;
import com.softwarepatterns.Clothes_ShopCa4.model.Review;
import com.softwarepatterns.Clothes_ShopCa4.model.ReviewRequest;
import com.softwarepatterns.Clothes_ShopCa4.model.User;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductRepository;
import com.softwarepatterns.Clothes_ShopCa4.repository.ReviewRepository;
import com.softwarepatterns.Clothes_ShopCa4.repository.UserRepository;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String addReview(ReviewRequest request) {
        if (request.getProductId() == null) {
            return "Product is required.";
        }

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            return "Username is required.";
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            return "Rating must be between 1 and 5.";
        }

        if (request.getComment() == null || request.getComment().isBlank()) {
            return "Comment is required.";
        }

        Product product = productRepository.findById(request.getProductId()).orElse(null);

        if (product == null) {
            return "Product not found.";
        }

        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        if (user == null) {
            return "User not found.";
        }

        Review review = new Review();
        review.setProductId(request.getProductId());
        review.setUsername(request.getUsername());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);

        return "Review added successfully.";
    }

    public List<Review> getReviewsForProduct(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
}
