package com.github.argajuvi.models.product;

public class ClothingProduct extends Product {

    private final char size;

    public ClothingProduct(int id, String name, int price, char size) {
        super(id, name, price);
        this.size = size;
    }

    public char getSize() {
        return size;
    }

}
