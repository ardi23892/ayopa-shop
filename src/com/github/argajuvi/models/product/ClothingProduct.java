package com.github.argajuvi.models.product;

public class ClothingProduct extends Product {

    private final char size;

    public ClothingProduct(int ID, String name, int price, char size) {
        super(ID, name, price);
        this.size = size;
    }

    public char getSize() {
        return size;
    }

}
