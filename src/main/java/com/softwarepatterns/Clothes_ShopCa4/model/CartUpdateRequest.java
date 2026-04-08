package com.softwarepatterns.Clothes_ShopCa4.model;

public class CartUpdateRequest {

    private String username;
    private Long cartItemId;
    private int quantity;

    public CartUpdateRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
