package com.github.argajuvi.models.product;

import java.sql.Date;

public class ProductFactory {

    public static Product createClothProduct(int id, String name, int price, char size) {
        return new ClothingProduct(id, name, price, size);
    }

    public static Product createFoodProduct(int id, String name, int price, Date expDate) {
        return new FoodProduct(id, name, price, expDate);
    }

    public static Product createBookProduct(int id, String name, int price, int publishYear, String author) {
        return new BookProduct(id, name, price, publishYear, author);
    }

}
