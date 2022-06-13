package com.github.argajuvi.menus;

import java.text.ParseException;

import com.github.argajuvi.Main;
import com.github.argajuvi.database.Database;
import com.github.argajuvi.utils.Utils;

public class UserMenu implements Menu {

	Database db = Database.getInstance();
	
    @Override
    public void showMenu() throws ParseException {
        while (true) {
            Utils.clearScreen();

            System.out.println(
                    "Welcome, " + Main.CURRENT_USER.getUsername() + " to AyopaShop\n" +
                    "-------------------------\n" +
                    "1. Check purchase history\n" +
                    "2. Buy products\n" +
                    "0. Logout\n");

            int choice = Utils.scanAbsoluteInt(">> ");
            switch (choice) {
                case 1:
                    Menu receiptMenu = new ReceiptMenu();
                    receiptMenu.showMenu();
                    break;
                case 2:
                    this.showBuyProductsMenu();
                    break;
                case 0:
                    Main.CURRENT_USER = null;
                    return;
                default:
                    System.out.println("Option is not available!");
                    Utils.waitForEnter();
                    break;
            }
        }
    }

    private void showBuyProductsMenu() throws ParseException {
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
                    ProductMenu.showOrderProductMenu();
                    break;
                case 2:
                    ProductMenu.showCartProducts();
                    break;
                case 3:
                    ProductMenu.showCheckout();
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
}
