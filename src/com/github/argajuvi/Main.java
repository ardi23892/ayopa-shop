package com.github.argajuvi;

import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.BookProduct;
import com.github.argajuvi.models.product.ClothingProduct;
import com.github.argajuvi.models.product.FoodProduct;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private final List<Product> productList;

    public Main() {
        this.productList = new ArrayList<>();

        while (true) {
            Utils.clearScreen();

            System.out.println(
                    " █████╗ ██╗   ██╗ ██████╗ ██████╗  █████╗     ███████╗██╗  ██╗ ██████╗ ██████╗ \n" +
                    "██╔══██╗╚██╗ ██╔╝██╔═══██╗██╔══██╗██╔══██╗    ██╔════╝██║  ██║██╔═══██╗██╔══██╗\n" +
                    "███████║ ╚████╔╝ ██║   ██║██████╔╝███████║    ███████╗███████║██║   ██║██████╔╝\n" +
                    "██╔══██║  ╚██╔╝  ██║   ██║██╔═══╝ ██╔══██║    ╚════██║██╔══██║██║   ██║██╔═══╝ \n" +
                    "██║  ██║   ██║   ╚██████╔╝██║     ██║  ██║    ███████║██║  ██║╚██████╔╝██║     \n" +
                    "╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝     ╚═╝  ╚═╝    ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝     \n");

            System.out.println(
                    "1. Login\n" +
                    "2. Register\n" +
                    "0. Exit\n");

            int choice = Utils.scanAbsoluteInt(">> ", "Input must be integer");
            switch (choice) {
                case 1:
                    // TODO: run login menu
                    break;
                case 2:
                    // TODO: run register menu
                    break;
                case 0:
                    System.exit(0);
                    return;
                default:
                    System.out.println("Option is not available!");
                    Utils.waitForEnter();
                    break;
            }
        }
    }

    private void showLogin() {
        throw new UnsupportedOperationException("Login menu has yet been build");
    }

    private void showRegister() {
        throw new UnsupportedOperationException("Register menu has yet been build");
    }

    private void showProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products found.");
        } else {
            String rowFormat = "| %3s | %-40s | %-20s | %-12s |\n";
            String line = "----------------------------------------------------------------\n";
            int count = 0;

            System.out.print(line);
            System.out.printf(rowFormat, "No.", "Product Name", "Product Price", "Product Type");
            System.out.print(line);

            for (Product product : productList) {
                count++;

                String productType = "Invalid";
                if (product instanceof BookProduct) {
                    productType = "Book";
                } else if (product instanceof ClothingProduct) {
                    productType = "Clothing";
                } else if (product instanceof FoodProduct) {
                    productType = "Food";
                }

                System.out.printf(
                        rowFormat,
                        count + "", product.getName(), product.getPrice() + "", productType
                );
            }

            System.out.print(line);
        }
    }

    private void showOrderProductMenu() {
        Utils.clearScreen();
        this.showProducts();

        if (productList.isEmpty()) {
            Utils.waitForEnter();
            return;
        }

        int choice;
        while (true) {
            choice = Utils.scanAbsoluteInt(
                    "Product to buy ['0' to go back]: ",
                    "Input must be integer");

            if (choice == 0) {
                return;
            }
            if (choice < 1 || choice > productList.size()) {
                System.out.println("Cannot find product");
                continue;
            }

            break;
        }

        Product chosenProduct = productList.get(choice - 1);
        int quantity;

        while (true) {
            quantity = Utils.scanAbsoluteInt(">> ", "Input must be integer");
            if (quantity < 1) {
                System.out.println("Product quantity must be greater than 1");
                continue;
            }

            break;
        }

        Order order = new Order(chosenProduct, quantity);
        // TODO: add order to user's cart

        Utils.waitForEnter();
    }

    private void showBuyProductsMenu() {
        while (true) {
            Utils.clearScreen();

            System.out.println(
                    "1. Order products\n" +
                    "2. Check my cart\n" +
                    "3. Checkout\n" +
                    "0. Back\n");

            int choice = Utils.scanAbsoluteInt(">> ", "Input must be integer");
            switch (choice) {
                case 1:
                    this.showOrderProductMenu();
                    break;
                case 2:
                    // TODO: view current ordered products in the cart
                    break;
                case 3:
                    // TODO: initiate the purchase
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Option is not available!");
                    Utils.waitForEnter();
                    break;
            }
        }
    }

    private void showUserMenu() {
        while (true) {
            Utils.clearScreen();

            System.out.println(
                    "Welcome, <NAME> to AyopaShop\n" +
                    "-------------------------\n" +
                    "1. Check purchase history\n" +
                    "2. Buy products\n" +
                    "3. Logout\n" +
                    "0. Exit\n");

            int choice = Utils.scanAbsoluteInt(">> ", "Input must be integer");
            switch (choice) {
                case 1:
                    // TODO: user checks their purchase history (using receipt)
                    break;
                case 2:
                    this.showBuyProductsMenu();
                    break;
                case 3:
                    // TODO: logout from current user
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Option is not available!");
                    Utils.waitForEnter();
                    break;
            }
        }
    }

    private void showAdminMenu() {
        throw new UnsupportedOperationException("Admin menu has yet been build");
    }

    public static void main(String[] args) {
        new Main();
    }

}
