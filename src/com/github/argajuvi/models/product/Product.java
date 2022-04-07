package com.github.argajuvi.models.product;

public abstract class Product {

    private final String name;
    private int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
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

	public String getTypeName() {
        String productType = "Invalid";

        if (this instanceof BookProduct) {
            productType = "Book";
        } else if (this instanceof ClothingProduct) {
            productType = "Clothing";
        } else if (this instanceof FoodProduct) {
            productType = "Food";
        }

        return productType;
    }

}
