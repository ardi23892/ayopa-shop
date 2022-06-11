package com.github.argajuvi.models.product;

import java.util.Date;

public class FoodProduct extends Product {

    private final Date expireDate;

    public FoodProduct(int ID, String name, int price, Date expireDate) {
        super(ID, name, price);
        this.expireDate = expireDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

}
