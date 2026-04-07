package com.softwarepatterns.Clothes_ShopCa4.strategy;

import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public interface ProductSortStrategy {

    void sort(List<Product> products, String sortDirection);

}
