package com.github.argajuvi.models.product;

public enum ProductType {

    FOOD("Food"),
    CLOTHING("Clothing"),
    BOOK("Book");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
