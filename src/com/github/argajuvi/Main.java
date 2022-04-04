package com.github.argajuvi;

import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.BookProduct;
import com.github.argajuvi.models.product.ClothingProduct;
import com.github.argajuvi.models.product.FoodProduct;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.user.User;
import com.github.argajuvi.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final List<Product> productList;
    private final List<User> userList;

    private User currentUser = null;

    public Main() {
        this.productList = new ArrayList<>();
        this.userList = new ArrayList<>();

        // PRE-REGISTER DATA ADMIN
        userList.add(registerAdmin(userList));

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

            int choice = Utils.scanAbsoluteInt(">> ");
            switch (choice) {
                case 1:
                    loginUser(userList);
                    break;
                case 2:
                    userList.add(registerUser(userList));
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

    //	FUNCTION UNTUK LOGIN USER
    public void loginUser(List<User> users) {
        Scanner scanner = Utils.SCANNER;

        String username;
        String password;

        System.out.print("Input Username: ");
        username = scanner.nextLine();
        System.out.print("Input Password: ");
        password = scanner.nextLine();

        // VALIDASI DATA LOGIN
        try {
            // LOOPING UNTUK MENCARI USERNAME DALAM VECTOR
            for (int i = 0; i <= users.size(); i++) {
                User user = users.get(i);

                if (username.equals(user.getUsername())) {
                    if (password.equals(user.getPassword())) {
                        currentUser = user;

                        // VALIDASI APAKAH LOGIN UNTUK ADMIN ATAU USER
                        if (username.equals("admin")) {
                            // SHOW MENU ADMIN KALAU YANG LOGIN ADMIN
                            this.showAdminMenu();
                            break;
                        } else {
                            // SHOW MENU ADMIN KALAU YANG LOGIN USER BIASA
                            this.showUserMenu();
                            break;
                        }
                    } else {
                        System.out.println("Incorrect password! Make sure to enter the correct password next time!");
                        Utils.waitForEnter();
                        break;
                    }
                }
            }
            // VALIDASI JIKA TIDAK DITEMUKAN DATA LOGIN PADA ARRAYLIST (DENGAN ARRAY OUT OF RANGE)
        } catch (Exception e) {
            System.out.println("Username doesn't exists! Make sure the account is registered!");
            Utils.waitForEnter();
        }
    }

    //	FUNCTION REGISTER DATA USER BARU
    public User registerUser(List<User> users) {
        Scanner scanner = Utils.SCANNER;
        boolean registering = true;

        String username;
        String password;
        // LOOPING SAMPAI REGISTRASI BERHASIL
        do {
            // LOOPING SAMPAI KRITERIA USERNAME DAN PASSWORD TERPENUHI
            do {
                System.out.print("Input new username [> 5 characters]: ");
                username = scanner.nextLine();
                System.out.print("Input new password [> 5 characters]: ");
                password = scanner.nextLine();
            } while (username.length() < 5 || password.length() < 5);

            try {
                for (int i = 0; i <= users.size(); i++) {
                    if (username.equals(users.get(i).getUsername())) {
                        System.out.println("User has already been registered before, maybe try login with it?");
                        Utils.waitForEnter();
                        break;
                    }
                }
            } catch (Exception e) {
                registering = false;
            }
        } while (registering);

        System.out.println("User is successfully registered!");
        Utils.waitForEnter();

        return new User(username, password);
    }

    //	PRE REGISTER DATA ADMIN
    public User registerAdmin(List<User> users) {
        return new User("admin", "admin123");
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
            choice = Utils.scanAbsoluteInt("Product to buy ['0' to go back]: ");

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
            quantity = Utils.scanAbsoluteInt(">> ");
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

            int choice = Utils.scanAbsoluteInt(">> ");
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
                    "Welcome, " + currentUser.getUsername() + " to AyopaShop\n" +
                    "-------------------------\n" +
                    "1. Check purchase history\n" +
                    "2. Buy products\n" +
                    "3. Logout\n" +
                    "0. Exit\n");

            int choice = Utils.scanAbsoluteInt(">> ");
            switch (choice) {
                case 1:
                    // TODO: user checks their purchase history (using receipt)
                    break;
                case 2:
                    this.showBuyProductsMenu();
                    break;
                case 3:
                    currentUser = null;
                    return;
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
