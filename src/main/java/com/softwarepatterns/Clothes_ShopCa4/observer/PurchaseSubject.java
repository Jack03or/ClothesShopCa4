package com.softwarepatterns.Clothes_ShopCa4.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.softwarepatterns.Clothes_ShopCa4.model.Cart;
import com.softwarepatterns.Clothes_ShopCa4.model.Order;

@Component
public class PurchaseSubject {

    private List<PurchaseObserver> observers;

    public PurchaseSubject(StockUpdateObserver stockUpdateObserver, CartClearObserver cartClearObserver) {
        observers = new ArrayList<PurchaseObserver>();
        registerObserver(stockUpdateObserver);
        registerObserver(cartClearObserver);
    }

    public void registerObserver(PurchaseObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Cart cart, Order order) {
        for (PurchaseObserver observer : observers) {
            observer.update(cart, order);
        }
    }
}
