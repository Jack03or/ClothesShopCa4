package com.softwarepatterns.Clothes_ShopCa4.criteria;

import java.util.ArrayList;
import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;

public class ManufacturerCriteria implements ProductCriteria {

    private String manufacturer;

    public ManufacturerCriteria(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> filteredProducts = new ArrayList<Product>();

        for (Product product : products) {
            if (product.getManufacturer() != null
                    && product.getManufacturer().equalsIgnoreCase(manufacturer)) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }
}
