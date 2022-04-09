package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.models.user.User;
import com.github.argajuvi.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class InitialMenu implements Menu {

    @Override
    public void showMenu() {
        List<User> userList = Main.USER_LIST;

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
                    this.loginUser(userList);
                    break;
                case 2:
                    userList.add(this.registerUser(userList));
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
        Menu menu;

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
                        Main.CURRENT_USER = user;

                        // VALIDASI APAKAH LOGIN UNTUK ADMIN ATAU USER
                        if (username.equals("admin")) {
                            // SHOW MENU ADMIN KALAU YANG LOGIN ADMIN
                            menu = new AdminMenu();
                            menu.showMenu();
                            break;
                        } else {
                            // SHOW MENU ADMIN KALAU YANG LOGIN USER BIASA
                            menu = new UserMenu();
                            menu.showMenu();
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

}
