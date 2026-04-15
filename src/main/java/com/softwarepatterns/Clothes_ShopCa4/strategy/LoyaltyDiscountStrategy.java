package com.softwarepatterns.Clothes_ShopCa4.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoyaltyDiscountStrategy implements DiscountStrategy {

    private static final BigDecimal LOYALTY_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal calculateDiscount(BigDecimal subtotal) {
        return subtotal.multiply(LOYALTY_RATE).setScale(2, RoundingMode.HALF_UP);
    }
}
