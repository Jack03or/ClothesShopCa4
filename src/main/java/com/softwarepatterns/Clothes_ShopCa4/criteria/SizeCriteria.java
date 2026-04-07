package com.softwarepatterns.Clothes_ShopCa4.criteria;

import java.util.ArrayList;
import java.util.List;

import com.softwarepatterns.Clothes_ShopCa4.model.Product;
import com.softwarepatterns.Clothes_ShopCa4.model.ProductVariant;

public class SizeCriteria implements ProductCriteria {

    private String size;

    public SizeCriteria(String size) {
        this.size = size;
    }

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        List<Product> filteredProducts = new ArrayList<Product>();

        for (Product product : products) {
            boolean matchingSizeFound = false;

            for (ProductVariant variant : product.getVariants()) {
                if (variant.getSize().equalsIgnoreCase(size)) {
                    matchingSizeFound = true;
                    break;
                }
            }

            if (matchingSizeFound) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }
}
