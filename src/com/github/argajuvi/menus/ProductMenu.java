package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.receipt.Receipt;
import com.github.argajuvi.utils.Utils;
import com.sun.istack.internal.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMenu {

    public static void showOrderProductMenu() {
        Utils.clearScreen();
        showProductsView();

        if (Main.productList.isEmpty()) {
            Utils.waitForEnter();
            return;
        }

        int choice;
        while (true) {
            choice = Utils.scanAbsoluteInt("Product to buy ['0' to go back]: ");

            if (choice == 0) {
                return;
            }
            if (choice < 1 || choice > Main.productList.size()) {
                System.out.println("Cannot find product");
                continue;
            }

            break;
        }

        Product chosenProduct = Main.productList.get(choice - 1);
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
        Main.currentUser.getCart().add(order);

        System.out.println("Product is added to the cart!");
        Utils.waitForEnter();
    }

    private static void showCartView() {
        List<Order> cart = Main.currentUser.getCart();

        if (cart.isEmpty()) {
            System.out.println("You haven't ordered anything.");
        } else {
            String rowFormat = "| %3s | %-40s | %-20s | %-12s | %8s | %-20s |\n";
            String line = "--------------------------------------------------------------------------------------------------------------------------\n";
            int count = 0;

            System.out.print(line);
            System.out.printf(
                    rowFormat,
                    "No.",
                    "Product Name", "Product Price", "Product Type",
                    "Quantity", "Total Price"
            );
            System.out.print(line);

            int totalOfTotalPrice = 0;
            for (Order order : cart) {
                count++;

                Product product = order.getProduct();
                totalOfTotalPrice += order.getTotalPrice();

                System.out.printf(
                        rowFormat,
                        count + "",
                        product.getName(),
                        Utils.formatPriceIDR(product.getPrice()), product.getType().getName(),
                        order.getQuantity() + "",
                        Utils.formatPriceIDR(order.getTotalPrice())
                );
            }

            System.out.print(line);
            System.out.printf("| %-46s | %-69s |\n", "Total Price", Utils.formatPriceIDR(totalOfTotalPrice));
            System.out.print(line);
        }
    }

    public static void showCartProducts() {
        Utils.clearScreen();

        List<Order> cart = Main.currentUser.getCart();
        showCartView();

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

        List<Order> cart = Main.currentUser.getCart();
        showCartView();

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
        Main.currentUser.getReceiptList().add(receipt);
        cart.clear();

        System.out.println("Successfully purchase products!");
        Utils.waitForEnter();
    }

    /**
     * Shows the list of products in a table view
     *
     * @param filterType the type of product should be shown on the view,
     *                   insert {@code null} to not use any filters
     */
    public static void showProductsView(@Nullable Product.Type filterType) {
        if (Main.productList.isEmpty()) {
            System.out.println("No products found.");
        } else {
            String rowFormat = "| %3s | %-40s | %-20s | %-12s |\n";
            String line = "----------------------------------------------------------------------------------------\n";
            int count = 0;

            System.out.print(line);
            System.out.printf(rowFormat, "No.", "Product Name", "Product Price", "Product Type");
            System.out.print(line);

            List<Product> filteredProductList = Main.productList;
            if (filterType != null) {
                // Filters the product list to a specific type of product
                filteredProductList = Main.productList.stream()
                        .filter(product -> product.getType() == filterType)
                        .collect(Collectors.toList());
            }

            for (Product product : filteredProductList) {
                count++;

                System.out.printf(
                        rowFormat,
                        count + "", product.getName(),
                        Utils.formatPriceIDR(product.getPrice()),
                        product.getType().getName()
                );
            }

            System.out.print(line);
        }
    }

    /**
     * @see #showProductsView(Product.Type)
     */
    public static void showProductsView() {
        showProductsView(null);
    }
}
