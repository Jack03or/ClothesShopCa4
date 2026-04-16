package com.softwarepatterns.Clothes_ShopCa4.model;

public class StockAdjustmentRequest {

    private Long productVariantId;
    private int quantity;

    public StockAdjustmentRequest() {

    }

    public Long getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Long productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
