package com.softwarepatterns.Clothes_ShopCa4.criteria;

import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public interface ProductCriteria {

    List<Product> meetCriteria(List<Product> products);

}
