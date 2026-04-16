package com.softwarepatterns.Clothes_ShopCa4.service;

import org.springframework.stereotype.Service;

import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.repository.ProductVariantRepository;
import com.softwarepatterns.Clothes_ShopCa4.template.PurchaseStockUpdate;
import com.softwarepatterns.Clothes_ShopCa4.template.WholesalerStockUpdate;

@Service
public class ProductVariantService {

    private ProductVariantRepository productVariantRepository;
    private PurchaseStockUpdate purchaseStockUpdate;
    private WholesalerStockUpdate wholesalerStockUpdate;

    public ProductVariantService(ProductVariantRepository productVariantRepository,
            PurchaseStockUpdate purchaseStockUpdate, WholesalerStockUpdate wholesalerStockUpdate) {
        this.productVariantRepository = productVariantRepository;
        this.purchaseStockUpdate = purchaseStockUpdate;
        this.wholesalerStockUpdate = wholesalerStockUpdate;
    }

    public ProductVariant saveProductVariant(ProductVariant productVariant) {
        return productVariantRepository.save(productVariant);
    }

    public String reduceStockAfterPurchase(Long productVariantId, int quantity) {
        return purchaseStockUpdate.updateStock(productVariantId, quantity);
    }

    public String addWholesalerStock(Long productVariantId, int quantity) {
        return wholesalerStockUpdate.updateStock(productVariantId, quantity);
    }
}
