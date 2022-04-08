package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.receipt.Receipt;
import com.github.argajuvi.utils.Utils;
import com.github.argajuvi.utils.Views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductMenu {

    public static void showOrderProductMenu() {
        Utils.clearScreen();
        Views.showProductsView();

        if (Main.PRODUCT_LIST.isEmpty()) {
            Utils.waitForEnter();
            return;
        }

        int choice;
        while (true) {
            choice = Utils.scanAbsoluteInt("Product to buy ['0' to go back]: ");

            if (choice == 0) {
                return;
            }
            if (choice < 1 || choice > Main.PRODUCT_LIST.size()) {
                System.out.println("Cannot find product");
                continue;
            }

            break;
        }

        Product chosenProduct = Main.PRODUCT_LIST.get(choice - 1);
        int quantity;

        while (true) {
            quantity = Utils.scanAbsoluteInt("Product quantity: ");
            if (quantity < 1) {
                System.out.println("Product quantity must be greater than 1");
                continue;
            }

            break;
        }

        Order order = new Order(chosenProduct, quantity);
        Main.CURRENT_USER.getCart().add(order);

        System.out.println("Product is added to the cart!");
        Utils.waitForEnter();
    }

    public static void showCartProducts() {
        Utils.clearScreen();

        List<Order> cart = Main.CURRENT_USER.getCart();
        Views.showCartView();

        if (cart.isEmpty()) {
            Utils.waitForEnter();
            return;
        }

        int choice;
        System.out.println();

        while (true) {
            choice = Utils.scanAbsoluteInt("Remove order ['0' to go back]: ");

            if (choice == 0) {
                return;
            }
            if (choice < 1 || choice > cart.size()) {
                System.out.println("Cannot find order");
                continue;
            }

            break;
        }

        cart.remove(choice - 1);
        System.out.println("Successfully removed order from the cart");

        Utils.waitForEnter();
    }

    public static void showCheckout() {
        Utils.clearScreen();

        List<Order> cart = Main.CURRENT_USER.getCart();
        Views.showCartView();

        if (cart.isEmpty()) {
            Utils.waitForEnter();
            return;
        }

        boolean isConfirmed = Utils.scanAbsoluteConfirmation("Do you want to purchase those products? [y/n]: ");
        if (!isConfirmed) {
            return;
        }

        List<Order> orderList = new ArrayList<>(cart);
        int totalOfTotalPrice = 0;

        for (Order order : orderList) {
            totalOfTotalPrice += order.getTotalPrice();
        }

        Receipt receipt = new Receipt(orderList, LocalDate.now(), totalOfTotalPrice);
        Main.CURRENT_USER.getReceiptList().add(receipt);
        cart.clear();

        System.out.println("Successfully purchase products!");
        Utils.waitForEnter();
    }
}
