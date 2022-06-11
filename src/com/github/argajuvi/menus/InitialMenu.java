package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.database.Database;
import com.github.argajuvi.models.user.User;
import com.github.argajuvi.utils.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import javax.jws.soap.SOAPBinding.Use;

public class InitialMenu implements Menu {

	Database db = Database.getInstance();
	
    @Override
    public void showMenu() {
        List<User> userList = Main.USER_LIST;

        while (true) {
            Utils.clearScreen();

            System.out.println(
                    " â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•— â–ˆâ–ˆâ•—   â–ˆâ–ˆâ•— â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•— â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•—  â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•—     â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•—â–ˆâ–ˆâ•—  â–ˆâ–ˆâ•— â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•— â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•— \n" +
                    "â–ˆâ–ˆâ•”â•�â•�â–ˆâ–ˆâ•—â•šâ–ˆâ–ˆâ•— â–ˆâ–ˆâ•”â•�â–ˆâ–ˆâ•”â•�â•�â•�â–ˆâ–ˆâ•—â–ˆâ–ˆâ•”â•�â•�â–ˆâ–ˆâ•—â–ˆâ–ˆâ•”â•�â•�â–ˆâ–ˆâ•—    â–ˆâ–ˆâ•”â•�â•�â•�â•�â•�â–ˆâ–ˆâ•‘  â–ˆâ–ˆâ•‘â–ˆâ–ˆâ•”â•�â•�â•�â–ˆâ–ˆâ•—â–ˆâ–ˆâ•”â•�â•�â–ˆâ–ˆâ•—\n" +
                    "â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•‘ â•šâ–ˆâ–ˆâ–ˆâ–ˆâ•”â•� â–ˆâ–ˆâ•‘   â–ˆâ–ˆâ•‘â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•”â•�â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•‘    â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•—â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•‘â–ˆâ–ˆâ•‘   â–ˆâ–ˆâ•‘â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•”â•�\n" +
                    "â–ˆâ–ˆâ•”â•�â•�â–ˆâ–ˆâ•‘  â•šâ–ˆâ–ˆâ•”â•�  â–ˆâ–ˆâ•‘   â–ˆâ–ˆâ•‘â–ˆâ–ˆâ•”â•�â•�â•�â•� â–ˆâ–ˆâ•”â•�â•�â–ˆâ–ˆâ•‘    â•šâ•�â•�â•�â•�â–ˆâ–ˆâ•‘â–ˆâ–ˆâ•”â•�â•�â–ˆâ–ˆâ•‘â–ˆâ–ˆâ•‘   â–ˆâ–ˆâ•‘â–ˆâ–ˆâ•”â•�â•�â•�â•� \n" +
                    "â–ˆâ–ˆâ•‘  â–ˆâ–ˆâ•‘   â–ˆâ–ˆâ•‘   â•šâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•”â•�â–ˆâ–ˆâ•‘     â–ˆâ–ˆâ•‘  â–ˆâ–ˆâ•‘    â–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•‘â–ˆâ–ˆâ•‘  â–ˆâ–ˆâ•‘â•šâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ–ˆâ•”â•�â–ˆâ–ˆâ•‘     \n" +
                    "â•šâ•�â•�  â•šâ•�â•�   â•šâ•�â•�    â•šâ•�â•�â•�â•�â•�â•� â•šâ•�â•�     â•šâ•�â•�  â•šâ•�â•�    â•šâ•�â•�â•�â•�â•�â•�â•�â•šâ•�â•�  â•šâ•�â•� â•šâ•�â•�â•�â•�â•�â•� â•šâ•�â•�     \n");

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
        
        //VALIDASI DATA LOGIN
        String query;
        
        boolean usernameCheck = false;
        String currPassword = "";
        
        try {
        	ResultSet rs = db.getResults("SELECT * FROM users WHERE username = ?", username);
			while(rs.next()) {
				//USERNAME DITEMUKAN
				Main.userId = rs.getInt("id");
				usernameCheck = true;
				currPassword = rs.getString("password");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        
        if(!usernameCheck){
        	System.out.println("Username doesn't exists! Make sure the account is registered!");
        	Utils.waitForEnter();
        }
        
        
        if(currPassword.equals(password)) {
        	if(username.equals("admin")) {
				menu = new AdminMenu();
			}else {
				menu = new UserMenu();
			}
        	
        	User currUser = new User(username, password);
        	
        	Main.CURRENT_USER = currUser;
        	menu.showMenu();
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

            User newUser = new User(username, password);
            registering = insertUser(newUser);
            
        } while (registering);

        System.out.println("User is successfully registered!");
        Utils.waitForEnter();
        
        return new User(username, password);
    }
    
    //Insert Query with Unique user
    public boolean insertUser(User newUser) {
    	String query;
        
        boolean isUnique = true;
        try {
        	ResultSet rs = db.getResults("SELECT * FROM users WHERE username = ?", newUser.getUsername());
			while(rs.next()) {
				isUnique = false;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        
        if(isUnique) {
        	db.execute("INSERT INTO users VALUES(NULL, ?, ?)", newUser.getUsername(), newUser.getPassword());    			
        }else {
        	System.out.println("User has already been registered before, maybe try login with it?");
            Utils.waitForEnter();
            return true;
        }
        return false;
    }

}
