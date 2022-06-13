package com.github.argajuvi.models.product;

public abstract class Product {

	private final int id;
    private final String name;
    private int price;
    private final ProductType type;

    public Product(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
        
        if (this instanceof FoodProduct) {
            this.type = ProductType.FOOD;
        } else if (this instanceof BookProduct) {
            this.type = ProductType.BOOK;
        } else if (this instanceof ClothingProduct) {
            this.type = ProductType.CLOTHING;
        } else {
            throw new IllegalStateException("Invalid product type!");
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

}
