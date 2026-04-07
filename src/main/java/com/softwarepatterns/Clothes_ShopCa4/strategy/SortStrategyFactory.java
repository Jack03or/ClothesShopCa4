package com.softwarepatterns.Clothes_ShopCa4.strategy;

public class SortStrategyFactory {

    public ProductSortStrategy getSortStrategy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return null;
        }

        if (sortBy.equalsIgnoreCase("title")) {
            return new TitleSortStrategy();
        }

        if (sortBy.equalsIgnoreCase("manufacturer")) {
            return new ManufacturerSortStrategy();
        }

        if (sortBy.equalsIgnoreCase("price")) {
            return new PriceSortStrategy();
        }

        return null;
    }
}
