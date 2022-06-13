package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.database.Database;
import com.github.argajuvi.models.user.User;
import com.github.argajuvi.utils.Closer;
import com.github.argajuvi.utils.Utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class InitialMenu implements Menu {

    Database db = Database.getInstance();

    @Override
    public void showMenu() throws ParseException {
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
                    this.loginUser();
                    break;
                case 2:
                    this.registerUser();
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
    public void loginUser() throws ParseException {
        Scanner scanner = Utils.SCANNER;

        String username;
        String password;
        Menu menu;

        System.out.print("Input Username: ");
        username = scanner.nextLine();
        System.out.print("Input Password: ");
        password = scanner.nextLine();

        List<User> userList = Main.USER_LIST;
        Optional<User> optionalUser = userList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        if (!optionalUser.isPresent()) {
            System.out.println("Username doesn't exists! Make sure the account is registered!");
            Utils.waitForEnter();
            return;
        }

        User user = optionalUser.get();
        if (user.getPassword().equals(password)) {
            if (username.equals("admin")) {
                menu = new AdminMenu();
            } else {
                menu = new UserMenu();
            }

            Main.CURRENT_USER = user;
            menu.showMenu();
        }
    }

    //	FUNCTION REGISTER DATA USER BARU
    public void registerUser() {
        Scanner scanner = Utils.SCANNER;

        String username;
        String password;

        // LOOPING SAMPAI KRITERIA USERNAME DAN PASSWORD TERPENUHI
        do {
            System.out.print("Input new username [> 5 characters]: ");
            username = scanner.nextLine();

            System.out.print("Input new password [> 5 characters]: ");
            password = scanner.nextLine();
        } while (username.length() < 5 || password.length() < 5);

        User newUser = new User(0, username, password);
        boolean registerResult = this.insertUser(newUser);

        if (!registerResult) {
            return;
        }

        System.out.println("User is successfully registered!");
        Utils.waitForEnter();

        Main.USER_LIST.add(newUser);
    }

    /**
     * @return {@code true} if user registration succeeded, otherwise {@code false}
     */
    public boolean insertUser(User newUser) {
        boolean isRegistered = true;

        try (Closer closer = new Closer()) {
            ResultSet rs = closer.add(db.getResults("SELECT * FROM users WHERE username = ?", newUser.getUsername()));
            isRegistered = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isRegistered) {
            System.out.println("User has already been registered before, maybe try login with it?");
            Utils.waitForEnter();

            return false;
        }

        db.execute("INSERT INTO users VALUES (NULL, ?, ?)", newUser.getUsername(), newUser.getPassword());

        try (ResultSet rs = db.getResults("SELECT id FROM users WHERE username = ?", newUser.getUsername())) {
            int id = rs.getInt("id");
            newUser.setId(id);
        } catch (Exception ignored) {
        }

        return true;
    }

}
