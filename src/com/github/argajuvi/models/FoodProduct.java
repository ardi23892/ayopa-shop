package com.github.argajuvi.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FoodProduct extends Product {

    private final static DateTimeFormatter FORMATTER;

    private final LocalDate expireDate;

    static {
        FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public FoodProduct(String name, int price, LocalDate expireDate) {
        super(name, price);
        this.expireDate = expireDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public String getFormattedDate() {
        return FORMATTER.format(expireDate);
    }

}
