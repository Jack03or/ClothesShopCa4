package com.softwarepatterns.Clothes_ShopCa4.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softwarepatterns.Clothes_ShopCa4.model.CartAddRequest;
import com.softwarepatterns.Clothes_ShopCa4.model.CartSummaryResponse;
import com.softwarepatterns.Clothes_ShopCa4.model.CartUpdateRequest;
import com.softwarepatterns.Clothes_ShopCa4.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestBody CartAddRequest request) {
        return cartService.addToCart(request.getUsername(), request.getProductVariantId(), request.getQuantity());
    }

    @GetMapping
    public CartSummaryResponse getCart(@RequestParam String username) {
        return cartService.getCartSummaryByUsername(username);
    }

    @PutMapping("/update")
    public String updateCartItem(@RequestBody CartUpdateRequest request) {
        return cartService.updateCartItem(request.getUsername(), request.getCartItemId(), request.getQuantity());
    }

    @DeleteMapping("/remove")
    public String removeCartItem(@RequestParam String username, @RequestParam Long cartItemId) {
        return cartService.removeCartItem(username, cartItemId);
    }
}
