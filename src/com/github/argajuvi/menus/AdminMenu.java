package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.models.product.*;
import com.github.argajuvi.models.user.User;
import com.github.argajuvi.utils.Utils;
import com.github.argajuvi.utils.Views;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AdminMenu implements Menu {

    @Override
    public void showMenu() {
        while (true) {
            Utils.clearScreen();

            System.out.println("Welcome Administrator\n" +
                               "-------------------------\n" +
                               "1. Add Clothing Product\n" +
                               "2. Add Food Product\n" +
                               "3. Add Book Product\n" +
                               "4. Show Product List\n" +
                               "5. Update Product\n" +
                               "6. Delete Product\n" +
                               "7. Show User List\n" +
                               "0. Logout\n");

            int choice = Utils.scanAbsoluteInt(">> ");
            if (choice == 0) {
                Main.CURRENT_USER = null;
                return;
            }

            if (choice >= 1 && choice <= 3) {
                addProduct(choice);
            } else if (choice == 4) {
                showProductMenu();
            } else if (choice == 5) {
                updateProduct();
            } else if (choice == 6) {
                deleteProduct();
            } else if (choice == 7) {
                showUserList();
            }

        }
    }

    private void addProduct(int choice) {
        String productName = null;
        boolean isProductNameOk = false;

        int productPrice = 0;
        boolean isProductPriceOk = false;

        while (!isProductNameOk) {
            System.out.print("Input product name [ 2 - 40 characters ]: ");
            productName = Utils.SCANNER.nextLine();

            if (productName.length() < 2 || productName.length() > 40) {
                System.out.println("Product name only consists of 2 - 40 characters!");
            } else isProductNameOk = true;
        }

        while (!isProductPriceOk) {
            productPrice = Utils.scanAbsoluteInt("Input product price [ Minimum Rp 2 ]: Rp ");

            if (productPrice < 2) {
                System.out.println("Read again. Minimum Rp 2!");
            } else isProductPriceOk = true;
        }

        Product product;

        if (choice == 1) {
            char size = 0;
            boolean isProductSizeOk = false;

            while (!isProductSizeOk) {
                System.out.print("Input product size [ S/M/L ]: ");
                size = Utils.SCANNER.nextLine().charAt(0);

                if (size != 'S' && size != 'M' && size != 'L') {
                    System.out.println("Only 'S' or 'M' or 'L'!");
                } else isProductSizeOk = true;
            }

            product = ProductFactory.createClothProduct(productName, productPrice, size);
        } else if (choice == 2) {
            String strExpDate;
            LocalDate expDate = null;
            boolean isEXPDateOk = false;

            LocalDate today = LocalDate.now();

            while (!isEXPDateOk) {
                System.out.print("Input product expired date [ min 2 days from now; format dd-MM-yyyy ]: ");
                strExpDate = Utils.SCANNER.nextLine();

                try {
                    expDate = LocalDate.parse(strExpDate, Utils.DATE_FORMATTER);

                    if (expDate.isBefore(today.plusDays(2))) {
                        System.out.println("Expired date must at least 2 days from now!");
                    } else {
                        isEXPDateOk = true;
                        expDate = LocalDate.parse(strExpDate, Utils.DATE_FORMATTER);
                    }
                } catch (DateTimeParseException ignored) {
                    System.out.println("Wrong date format!");
                }
            }

            product = ProductFactory.createFoodProduct(productName, productPrice, expDate);
        } else {
            int publicationYear = 0;
            String author = null;
            boolean isBookYearOk = false;

            while (!isBookYearOk) {
                publicationYear = Utils.scanAbsoluteInt("Input book's year of publication [ min 1 ]: ");

                if (publicationYear < 1) {
                    System.out.println("Minimum input is 1!");
                } else isBookYearOk = true;
            }

            boolean isAuthorOk = false;

            while (!isAuthorOk) {
                System.out.print("Input author's name: ");
                author = Utils.SCANNER.nextLine();

                if (author.isEmpty()) {
                    System.out.println("Author's name cannot be empty!");
                } else isAuthorOk = true;
            }

            product = ProductFactory.createBookProduct(productName, productPrice, publicationYear, author);
        }

        Main.PRODUCT_LIST.add(product);
        System.out.println("Product successfully added");
        Utils.waitForEnter();
    }

    private void showProductMenu() {
        if (Main.PRODUCT_LIST.isEmpty()) {
            System.out.println("No products found.");
        } else {

            while (true) {
                Utils.clearScreen();

                System.out.println("Show Product Menu\n");
                System.out.println("-----------------\n");
                System.out.println(
                        "1. Show All Product\n" +
                        "2. Show Food Product\n" +
                        "3. Show Clothing Product\n" +
                        "4. Show Book Product\n" +
                        "0. Back\n"
                );

                int choose = Utils.scanAbsoluteInt(">> ");

                if (choose == 0) {
                    return;
                }
                if (choose < 1 || choose > 4) {
                    System.out.println("Your Input is Invalid");
                    continue;
                }

                if (choose == 1) {
                    System.out.println("All Products\n");
                    Views.showProductsView();
                } else {
                    ProductType filter = ProductType.values()[choose - 2];
                    System.out.println(filter.getName() + " Products\n");
                    Views.showProductsView(filter);
                }

                Utils.waitForEnter();
            }
        }
    }

    private void updateProduct() {
        String rowFormat = "| %3s | %-40s | %-20s | %-12s |\n";
        String line = "----------------------------------------------------------------------------------------\n";
        int count = 0;
        System.out.println("Update Product\n");
        System.out.print(line);
        System.out.printf(rowFormat, "No.", "Product Name", "Product Price", "Product Type");
        System.out.print(line);

        for (Product product : Main.PRODUCT_LIST) {
            count++;

            System.out.printf(
                    rowFormat,
                    count + "",
                    product.getName(),
                    Utils.formatPriceIDR(product.getPrice()),
                    product.getType().getName()
            );
        }
        System.out.print(line);

        int update;
        int price;
        while (true) {
            update = Utils.scanAbsoluteInt("Input the product id to be changed [1 - " + count + "] (0 to back): ");
            if (update >= 1 && update <= count) break;
            else if (update == 0) return;
            else System.out.println("Your Input is Invalid");
        }

        System.out.println("Product Name : " + Main.PRODUCT_LIST.get(update - 1).getName());
        price = Utils.scanAbsoluteInt("Update Price from " + Main.PRODUCT_LIST.get(update - 1).getPrice() + " to ");
        Main.PRODUCT_LIST.get(update - 1).setPrice(price);

        System.out.println("Successfully Updated!");
        Utils.waitForEnter();
    }

    private void deleteProduct() {
        String rowFormat = "| %3s | %-40s | %-20s | %-12s |\n";
        String line = "----------------------------------------------------------------------------------------\n";
        int count = 0;
        System.out.println("Delete Product\n");
        System.out.print(line);
        System.out.printf(rowFormat, "No.", "Product Name", "Product Price", "Product Type");
        System.out.print(line);

        for (Product product : Main.PRODUCT_LIST) {
            count++;

            System.out.printf(
                    rowFormat,
                    count + "",
                    product.getName(),
                    Utils.formatPriceIDR(product.getPrice()),
                    product.getType().getName()
            );
        }
        System.out.print(line);

        int del;
        while (true) {
            del = Utils.scanAbsoluteInt("Input the product id to be deleted [1 - " + count + "] (0 to delete): ");
            if (del >= 1 && del <= count) break;
            else if (del == 0) return;
            else System.out.println("Your Input is Invalid");
        }

        System.out.println(Main.PRODUCT_LIST.get(del - 1).getName() + " has been deleted");
        Main.PRODUCT_LIST.remove(del - 1);

        System.out.println("Successfully Deleted!");
        Utils.waitForEnter();
    }

    private void showUserList() {
        if (Main.USER_LIST.isEmpty()) {
            System.out.println("No users found.");
        } else {
            String rowFormat = "| %3s | %-20s |\n";
            String line = "------------------------------\n";
            int count = 0;

            System.out.println("User List");
            System.out.print(line);

            for (User user : Main.USER_LIST) {
                System.out.printf(rowFormat, ++count, user.getUsername());
            }

            System.out.print(line);

        }

        Utils.waitForEnter();
    }
}
