package com.github.argajuvi.models.user;

import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final int id;

    private final String username, password;
    private final List<Receipt> receiptList;

    /**
     * As the user is ordering product, to simulate having a cart for each user,
     * I'm using this list that stores each user's order and cleared it once they purchased the orders.
     * <p>
     * I'm taking the minimal approach to achieve that simulation, so that's why it's a list.
     */
    private final List<Order> cart;

    public User(int id, String username, String password) {
        this.id = id;

        this.username = username;
        this.password = password;

        this.receiptList = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public List<Order> getCart() {
        return cart;
    }

}
