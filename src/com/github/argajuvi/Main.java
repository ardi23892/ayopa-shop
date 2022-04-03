package com.github.argajuvi;

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
                    // TODO: show all products and select one to add to the cart
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
