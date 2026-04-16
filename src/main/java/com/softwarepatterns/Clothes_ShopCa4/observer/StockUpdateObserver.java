package com.softwarepatterns.Clothes_ShopCa4.observer;

import org.springframework.stereotype.Component;

import com.softwarepatterns.Clothes_ShopCa4.model.Cart;
import com.softwarepatterns.Clothes_ShopCa4.model.CartItem;
import com.softwarepatterns.Clothes_ShopCa4.model.Order;
import com.softwarepatterns.Clothes_ShopCa4.service.ProductVariantService;

@Component
public class StockUpdateObserver implements PurchaseObserver {

    private ProductVariantService productVariantService;

    public StockUpdateObserver(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    @Override
    public void update(Cart cart, Order order) {
        for (CartItem cartItem : cart.getItems()) {
            productVariantService.reduceStockAfterPurchase(cartItem.getProductVariant().getId(), cartItem.getQuantity());
        }
    }
}
