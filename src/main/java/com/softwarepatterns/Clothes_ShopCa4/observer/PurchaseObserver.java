package com.softwarepatterns.Clothes_ShopCa4.observer;

import com.softwarepatterns.Clothes_ShopCa4.model.Cart;
import com.softwarepatterns.Clothes_ShopCa4.model.Order;

public interface PurchaseObserver {

    void update(Cart cart, Order order);

}
