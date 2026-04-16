package com.softwarepatterns.Clothes_ShopCa4.template;

import org.springframework.stereotype.Component;

import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductVariantRepository;

@Component
public class PurchaseStockUpdate extends StockUpdateTemplate {

    public PurchaseStockUpdate(ProductVariantRepository productVariantRepository) {
        super(productVariantRepository);
    }

    @Override
    protected String validateStockChange(ProductVariant productVariant, int quantity) {
        if (productVariant.getStockQuantity() < quantity) {
            return "Not enough stock available.";
        }

        return null;
    }

    @Override
    protected void changeStock(ProductVariant productVariant, int quantity) {
        productVariant.setStockQuantity(productVariant.getStockQuantity() - quantity);
    }

    @Override
    protected String getSuccessMessage() {
        return "Stock reduced successfully.";
    }
}
