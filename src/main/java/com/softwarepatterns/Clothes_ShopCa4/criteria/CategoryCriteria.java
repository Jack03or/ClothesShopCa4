package com.softwarepatterns.Clothes_ShopCa4.criteria;

import java.util.ArrayList;
import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public class CategoryCriteria implements ProductCriteria {

    private String category;

    public CategoryCriteria(String category) {
        this.category = category;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> filteredProducts = new ArrayList<Product>();

        for (Product product : products) {
            if (product.getCategory() != null
                    && product.getCategory().toLowerCase().contains(category.toLowerCase())) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }
}
