package com.softwarepatterns.Clothes_ShopCa4.factory;

import org.springframework.stereotype.Component;

import com.softwarepatterns.Clothes_ShopCa4.model.User;
import com.softwarepatterns.Clothes_ShopCa4.strategy.DiscountStrategy;
import com.softwarepatterns.Clothes_ShopCa4.strategy.LoyaltyDiscountStrategy;
import com.softwarepatterns.Clothes_ShopCa4.strategy.NoDiscountStrategy;

@Component
public class DiscountStrategyFactory {

    public DiscountStrategy createDiscountStrategy(User user) {
        if (user != null && user.isHasLoyaltyCard()) {
            return new LoyaltyDiscountStrategy();
        }

        return new NoDiscountStrategy();
    }
}
