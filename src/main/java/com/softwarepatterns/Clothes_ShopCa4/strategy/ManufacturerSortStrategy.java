package com.softwarepatterns.Clothes_ShopCa4.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public class ManufacturerSortStrategy implements ProductSortStrategy {

    @Override
    public void sort(List<Product> products, String sortDirection) {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product firstProduct, Product secondProduct) {
                return firstProduct.getManufacturer().compareToIgnoreCase(secondProduct.getManufacturer());
            }
        });

        if (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) {
            Collections.reverse(products);
        }
    }
}
