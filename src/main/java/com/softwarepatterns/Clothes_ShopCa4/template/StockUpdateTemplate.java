package com.softwarepatterns.Clothes_ShopCa4.template;

import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductVariantRepository;

public abstract class StockUpdateTemplate {

    private ProductVariantRepository productVariantRepository;

    public StockUpdateTemplate(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    public String updateStock(Long productVariantId, int quantity) {
        if (productVariantId == null) {
            return "Product variant is required.";
        }

        if (quantity <= 0) {
            return "Quantity must be greater than zero.";
        }

        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElse(null);

        if (productVariant == null) {
            return "Product variant not found.";
        }

        String validationMessage = validateStockChange(productVariant, quantity);

        if (validationMessage != null) {
            return validationMessage;
        }

        changeStock(productVariant, quantity);
        productVariantRepository.save(productVariant);

        return getSuccessMessage();
    }

    protected String validateStockChange(ProductVariant productVariant, int quantity) {
        return null;
    }

    protected abstract void changeStock(ProductVariant productVariant, int quantity);

    protected abstract String getSuccessMessage();
}
