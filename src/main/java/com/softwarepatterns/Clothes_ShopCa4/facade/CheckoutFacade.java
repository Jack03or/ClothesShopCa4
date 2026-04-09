package com.softwarepatterns.Clothes_ShopCa4.facade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.softwarepatterns.Clothes_ShopCa4.model.Cart;
import com.softwarepatterns.Clothes_ShopCa4.model.CartItem;
import com.softwarepatterns.Clothes_ShopCa4.model.Order;
import com.softwarepatterns.Clothes_ShopCa4.model.OrderItem;
import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;
import com.softwarepatterns.Clothes_ShopCa4.observer.PurchaseSubject;
import com.softwarepatterns.Clothes_ShopCa4.service.CartService;
import com.softwarepatterns.Clothes_ShopCa4.service.OrderService;

@Component
public class CheckoutFacade {

    private CartService cartService;
    private OrderService orderService;
    private PurchaseSubject purchaseSubject;

    public CheckoutFacade(CartService cartService, OrderService orderService,
            PurchaseSubject purchaseSubject) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.purchaseSubject = purchaseSubject;
    }

    public String checkout(String username) {
        Cart cart = cartService.getCartByUsername(username);

        if (cart == null) {
            return "Cart not found.";
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            return "Cart is empty.";
        }

        for (CartItem cartItem : cart.getItems()) {
            ProductVariant variant = cartItem.getProductVariant();

            if (variant.getStockQuantity() < cartItem.getQuantity()) {
                return "Not enough stock for " + cartItem.getProductTitle() + " size " + cartItem.getSize() + ".";
            }
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductTitle(cartItem.getProductTitle());
            orderItem.setManufacturer(cartItem.getManufacturer());
            orderItem.setImageUrl(cartItem.getImageUrl());
            orderItem.setSize(cartItem.getSize());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            order.getItems().add(orderItem);

            totalPrice = totalPrice.add(cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        order.setTotalPrice(totalPrice);
        orderService.saveOrder(order);
        purchaseSubject.notifyObservers(cart, order);

        return "Checkout completed successfully.";
    }
}
