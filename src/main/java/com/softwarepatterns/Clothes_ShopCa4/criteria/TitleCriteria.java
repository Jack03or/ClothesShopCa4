package com.softwarepatterns.Clothes_ShopCa4.criteria;

import java.util.ArrayList;
import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public class TitleCriteria implements ProductCriteria {

    private String title;

    public TitleCriteria(String title) {
        this.title = title;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> filteredProducts = new ArrayList<Product>();

        for (Product product : products) {
            if (product.getTitle() != null && product.getTitle().toLowerCase().contains(title.toLowerCase())) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }
}
