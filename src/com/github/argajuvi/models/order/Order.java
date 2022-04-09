package com.github.argajuvi.models.order;

import com.github.argajuvi.models.product.Product;

public class Order {

    private final Product product;
    private final int quantity;

    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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
