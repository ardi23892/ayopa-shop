package com.github.argajuvi;

import com.github.argajuvi.utils.Utils;

public class Main {

    public Main() {
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

            int choice;
            while (true) {
                System.out.print(">> ");
                choice = Utils.scanInt(-1);

                if (choice == -1) {
                    System.out.println("Input must be integer");
                    continue;
                }

                break;
            }

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

            int choice;
            while (true) {
                System.out.print(">> ");
                choice = Utils.scanInt(-1);

                if (choice == -1) {
                    System.out.println("Input must be integer");
                    continue;
                }

                break;
            }

            switch (choice) {
                case 1:
                    // TODO: user checks their purchase history (using struct)
                    break;
                case 2:
                    // TODO: user starts to buy products
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
