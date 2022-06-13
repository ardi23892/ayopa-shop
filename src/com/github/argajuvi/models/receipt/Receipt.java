package com.github.argajuvi.models.receipt;

import com.github.argajuvi.models.order.Order;

import java.sql.Date;
import java.util.List;

public class Receipt {

    private final int id;
    private final List<Order> orderList;
    private final Date purchaseDate;
    private final int totalPrice;

    public Receipt(int id, List<Order> orderList, Date purchaseDate, int totalPrice) {
        this.id = id;
        this.orderList = orderList;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

}
