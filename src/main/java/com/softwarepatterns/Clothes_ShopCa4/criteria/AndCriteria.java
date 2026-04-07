package com.softwarepatterns.Clothes_ShopCa4.criteria;

import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public class AndCriteria implements ProductCriteria {

    private ProductCriteria firstCriteria;
    private ProductCriteria secondCriteria;

    public AndCriteria(ProductCriteria firstCriteria, ProductCriteria secondCriteria) {
        this.firstCriteria = firstCriteria;
        this.secondCriteria = secondCriteria;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> firstFilteredProducts = firstCriteria.meetCriteria(products);
        return secondCriteria.meetCriteria(firstFilteredProducts);
    }
}
