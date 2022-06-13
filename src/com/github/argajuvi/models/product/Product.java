package com.github.argajuvi.models.product;

public abstract class Product {

	private final int ID;
    private final String name;
    private int price;
    private final ProductType type;

    public Product(int ID, String name, int price) {
        this.name = name;
        this.price = price;
        this.ID = ID;
        
        
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

    public int getID() {
		return ID;
	}

}
