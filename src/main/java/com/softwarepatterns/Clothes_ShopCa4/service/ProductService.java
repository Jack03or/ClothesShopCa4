package com.softwarepatterns.Clothes_ShopCa4.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;
import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductRepository;

@Service
public class ProductService {

    private static final Set<String> VALID_SIZES = Set.of("XS", "S", "M", "L", "XL");

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        for (ProductVariant variant : product.getVariants()) {
            variant.setSize(variant.getSize().toUpperCase());
        }

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public boolean validProduct(Product product) {
        if (product.getTitle() == null || product.getTitle().isBlank()) {
            return false;
        }

        if (product.getManufacturer() == null || product.getManufacturer().isBlank()) {
            return false;
        }

        if (product.getCategory() == null || product.getCategory().isBlank()) {
            return false;
        }

        if (product.getImageUrl() == null || product.getImageUrl().isBlank()) {
            return false;
        }

        if (product.getPrice() == null || product.getPrice().doubleValue() <= 0) {
            return false;
        }

        if (product.getVariants() == null || product.getVariants().isEmpty()) {
            return false;
        }

        for (ProductVariant variant : product.getVariants()) {
            if (variant.getSize() == null || !VALID_SIZES.contains(variant.getSize().toUpperCase())) {
                return false;
            }

            if (variant.getStockQuantity() < 0) {
                return false;
            }
        }

        return true;
    }
}
