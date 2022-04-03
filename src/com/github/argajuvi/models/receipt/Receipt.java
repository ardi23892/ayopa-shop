package com.github.argajuvi.models.receipt;

import com.github.argajuvi.models.order.Order;

import java.time.LocalDate;
import java.util.List;

public class Receipt {

    /**
     * To be able to generate the {@link Receipt#id} automatically.
     *
     * We need a tracker that starts from {@code 1} and automatically increment
     * as more receipts are created.
     */
    private static int ID_TRACKER = 1;

    private final int id;
    private final List<Order> orderList;
    private final LocalDate purchaseDate;
    private final int totalPrice;

    public Receipt(List<Order> orderList, LocalDate purchaseDate, int totalPrice) {
        this.id = ID_TRACKER++;
        this.orderList = orderList;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
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
