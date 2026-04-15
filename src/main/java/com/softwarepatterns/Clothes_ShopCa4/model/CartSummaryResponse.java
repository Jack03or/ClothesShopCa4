package com.softwarepatterns.Clothes_ShopCa4.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartSummaryResponse {

    private Long id;
    private String username;
    private boolean loyaltyCard;
    private BigDecimal subtotal;
    private BigDecimal discountAmount;
    private BigDecimal totalPrice;
    private List<CartItem> items = new ArrayList<CartItem>();

    public CartSummaryResponse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(boolean loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}
