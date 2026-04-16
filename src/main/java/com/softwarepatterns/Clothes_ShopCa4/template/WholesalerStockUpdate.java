package com.softwarepatterns.Clothes_ShopCa4.template;

import org.springframework.stereotype.Component;

import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductVariantRepository;

@Component
public class WholesalerStockUpdate extends StockUpdateTemplate {

    public WholesalerStockUpdate(ProductVariantRepository productVariantRepository) {
        super(productVariantRepository);
    }

    @Override
    protected void changeStock(ProductVariant productVariant, int quantity) {
        productVariant.setStockQuantity(productVariant.getStockQuantity() + quantity);
    }

    @Override
    protected String getSuccessMessage() {
        return "Stock updated from wholesaler purchase.";
    }
}
