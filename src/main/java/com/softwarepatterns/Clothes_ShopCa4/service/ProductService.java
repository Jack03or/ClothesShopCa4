package com.softwarepatterns.Clothes_ShopCa4.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.criteria.AndCriteria;
import com.softwarepatterns.Clothes_ShopCa4.criteria.CategoryCriteria;
import com.softwarepatterns.Clothes_ShopCa4.criteria.ManufacturerCriteria;
import com.softwarepatterns.Clothes_ShopCa4.criteria.ProductCriteria;
import com.softwarepatterns.Clothes_ShopCa4.criteria.SizeCriteria;
import com.softwarepatterns.Clothes_ShopCa4.criteria.TitleCriteria;
import com.softwarepatterns.Clothes_ShopCa4.model.Product;
import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductRepository;

@Service
public class ProductService {

    private static final Set<String> VALID_SIZES = createValidSizes();

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

    public List<Product> searchProducts(String title, String manufacturer, String category, String size,
            String sortBy, String sortDirection) {
        List<Product> filteredProducts = new ArrayList<>(productRepository.findAll());
        ProductCriteria currentCriteria = null;

        if (title != null && !title.isBlank()) {
            currentCriteria = new TitleCriteria(title);
        }

        if (manufacturer != null && !manufacturer.isBlank()) {
            ProductCriteria manufacturerCriteria = new ManufacturerCriteria(manufacturer);
            currentCriteria = currentCriteria == null ? manufacturerCriteria
                    : new AndCriteria(currentCriteria, manufacturerCriteria);
        }

        if (category != null && !category.isBlank()) {
            ProductCriteria categoryCriteria = new CategoryCriteria(category);
            currentCriteria = currentCriteria == null ? categoryCriteria
                    : new AndCriteria(currentCriteria, categoryCriteria);
        }

        if (size != null && !size.isBlank()) {
            ProductCriteria sizeCriteria = new SizeCriteria(size);
            currentCriteria = currentCriteria == null ? sizeCriteria
                    : new AndCriteria(currentCriteria, sizeCriteria);
        }

        if (currentCriteria != null) {
            filteredProducts = currentCriteria.meetCriteria(filteredProducts);
        }

        sortProducts(filteredProducts, sortBy, sortDirection);
        return filteredProducts;
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

    private void sortProducts(List<Product> products, String sortBy, String sortDirection) {
        if (sortBy == null || sortBy.isBlank()) {
            return;
        }

        Comparator<Product> comparator = null;

        if (sortBy.equalsIgnoreCase("title")) {
            comparator = Comparator.comparing(Product::getTitle, String.CASE_INSENSITIVE_ORDER);
        } else if (sortBy.equalsIgnoreCase("manufacturer")) {
            comparator = Comparator.comparing(Product::getManufacturer, String.CASE_INSENSITIVE_ORDER);
        } else if (sortBy.equalsIgnoreCase("price")) {
            comparator = Comparator.comparing(Product::getPrice);
        }

        if (comparator == null) {
            return;
        }

        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        products.sort(comparator);
    }

    private static Set<String> createValidSizes() {
        Set<String> sizes = new HashSet<String>();
        sizes.add("XS");
        sizes.add("S");
        sizes.add("M");
        sizes.add("L");
        sizes.add("XL");
        return sizes;
    }
}
