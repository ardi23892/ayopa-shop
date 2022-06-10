package com.github.argajuvi.models.product;

import java.time.LocalDate;

public class ProductFactory {

    public Product getProduct(ProductType productType, String name, int price, Object... params) {
        String author;
        char size;
        int publishYear;
        LocalDate expDate;

        if (productType == ProductType.CLOTHING) {
            size = (char) params[0];
            return new ClothingProduct(name, price, size);
        } else if (productType == ProductType.FOOD) {
            expDate = (LocalDate) params[0];
            return new FoodProduct(name, price, expDate);
        } else if (productType == ProductType.BOOK) {
            publishYear = (int) params[0];
            author = params[1].toString();
            return new BookProduct(name, price, publishYear, author);
        }

        return null;
    }
}
