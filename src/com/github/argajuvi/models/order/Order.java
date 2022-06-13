package com.github.argajuvi.models.order;

import com.github.argajuvi.models.product.Product;

public class Order {

	private final int id;
    private final Product product;
    private final int quantity;

    public Order(int id, Product product, int quantity) {
    	this.id = id;
        this.product = product;
        this.quantity = quantity;
    }
    
    public int getId() {
		return id;
	}

	public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return product.getPrice() * quantity;
    }

}
