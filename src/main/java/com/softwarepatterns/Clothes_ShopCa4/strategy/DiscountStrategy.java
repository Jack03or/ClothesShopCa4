package com.softwarepatterns.Clothes_ShopCa4.strategy;

import java.math.BigDecimal;

public interface DiscountStrategy {

    BigDecimal calculateDiscount(BigDecimal subtotal);
}
