package com.github.argajuvi.models.receipt;

import com.github.argajuvi.models.order.Order;

import java.time.LocalDate;
import java.util.List;

public class Receipt {

    private final int id;
    private final LocalDate purchaseDate;
    private final int totalPrice;
    private final List<Order> orderList;

    public Receipt(int id, LocalDate purchaseDate, int totalPrice, List<Order> orderList) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.orderList = orderList;
    }

    public int getId() {
        return id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

}
