package com.github.argajuvi.models.product;

public abstract class Product {

	private final int ID;
    private final String name;
    private int price;
    private final Type type;

    public Product(int ID, String name, int price) {
        this.name = name;
        this.price = price;
        this.ID = ID;
        
        
        if (this instanceof FoodProduct) {
            this.type = Type.FOOD;
        } else if (this instanceof BookProduct) {
            this.type = Type.BOOK;
        } else if (this instanceof ClothingProduct) {
            this.type = Type.CLOTHING;
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

    public Type getType() {
        return type;
    }

    public int getID() {
		return ID;
	}

	public enum Type {

        FOOD("Food"),
        CLOTHING("Clothing"),
        BOOK("Book");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}
