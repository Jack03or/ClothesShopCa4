package com.softwarepatterns.Clothes_ShopCa4.observer;

import org.springframework.stereotype.Component;

import com.softwarepatterns.Clothes_ShopCa4.model.Cart;
import com.softwarepatterns.Clothes_ShopCa4.model.Order;
import com.softwarepatterns.Clothes_ShopCa4.service.CartService;

@Component
public class CartClearObserver implements PurchaseObserver {

    private CartService cartService;

    public CartClearObserver(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void update(Cart cart, Order order) {
        cartService.clearCart(cart);
    }
}
