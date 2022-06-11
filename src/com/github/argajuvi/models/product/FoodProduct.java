package com.github.argajuvi.models.product;

import java.util.Date;

public class FoodProduct extends Product {

    private final Date expireDate;

    public FoodProduct(String name, int price, Date expireDate) {
        super(name, price);
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

}
