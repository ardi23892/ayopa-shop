package com.github.argajuvi.models.product;

import java.time.LocalDate;

public class FoodProduct extends Product {

    private final LocalDate expireDate;

    public FoodProduct(String name, int price, LocalDate expireDate) {
        super(name, price);
        this.expireDate = expireDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

}
