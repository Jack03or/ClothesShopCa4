package com.softwarepatterns.Clothes_ShopCa4.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;
import com.softwarepatterns.Clothes_ShopCa4.model.StockAdjustmentRequest;
import com.softwarepatterns.Clothes_ShopCa4.service.ProductService;
import com.softwarepatterns.Clothes_ShopCa4.service.ProductVariantService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private ProductVariantService productVariantService;

    public ProductController(ProductService productService, ProductVariantService productVariantService) {
        this.productService = productService;
        this.productVariantService = productVariantService;
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        if (!productService.validProduct(product)) {
            return "Invalid product data.";
        }

        productService.saveProduct(product);
        return "Product added successfully.";
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {
        return productService.searchProducts(title, manufacturer, category, size, sortBy, sortDirection);
    }

    @PutMapping("/wholesaler-stock")
    public String addWholesalerStock(@RequestBody StockAdjustmentRequest request) {
        return productVariantService.addWholesalerStock(request.getProductVariantId(), request.getQuantity());
    }
}
