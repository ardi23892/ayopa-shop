package com.github.argajuvi.models.product;

import java.time.LocalDate;

public class ProductFactory {

    public static Product createClothProduct(String name, int price, char size) {
        return new ClothingProduct(name, price, size);
    }

    public static Product createFoodProduct(String name, int price, LocalDate expDate) {
        return new FoodProduct(name, price, expDate);
    }

    public static Product createBookProduct(String name, int price, int publishYear, String author) {
        return new BookProduct(name, price, publishYear, author);
    }

}
