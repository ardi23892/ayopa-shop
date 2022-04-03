package com.github.argajuvi.models.product;

public class ClothingProduct extends Product {

    private final char size;

    public ClothingProduct(String name, int price, char size) {
        super(name, price);
        this.size = size;
    }

    public char getSize() {
        return size;
    }

}
