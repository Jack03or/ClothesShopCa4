package com.softwarepatterns.Clothes_ShopCa4.strategy;

import java.math.BigDecimal;

public class NoDiscountStrategy implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(BigDecimal subtotal) {
        return BigDecimal.ZERO;
    }
}
