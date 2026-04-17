package com.softwarepatterns.Clothes_ShopCa4.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminOrderResponse {

    private Long id;
    private BigDecimal subtotalPrice;
    private BigDecimal discountAmount;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private List<AdminOrderItemResponse> items = new ArrayList<AdminOrderItemResponse>();

    public AdminOrderResponse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(BigDecimal subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<AdminOrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<AdminOrderItemResponse> items) {
        this.items = items;
    }
}
