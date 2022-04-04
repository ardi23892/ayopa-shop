package com.github.argajuvi.models.user;

import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String username, password;
    private final List<Receipt> receiptList;
    private final List<Order> currentCart;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.receiptList = new ArrayList<>();
        this.currentCart = new ArrayList<>();
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

    public List<Order> getCurrentCart() {
        return currentCart;
    }

}
