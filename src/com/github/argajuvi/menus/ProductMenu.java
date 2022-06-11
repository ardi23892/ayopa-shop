package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.database.Database;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.receipt.Receipt;
import com.github.argajuvi.utils.Utils;
import com.github.argajuvi.utils.Views;

import sun.security.pkcs11.Secmod.DbMode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductMenu {
	
    public static void showOrderProductMenu() {
    	Database db = Database.getInstance();
        Utils.clearScreen();
        Views.showProductsView();

        if (Main.PRODUCT_LIST.isEmpty()) {
            Utils.waitForEnter();
            return;
        }

        int choice, productIdx = 0;
        while (true) {
            choice = Utils.scanAbsoluteInt("Product to buy ['0'] to go back: ");

            if (choice == 0) {
                return;
            }
            
            //cek product ada atau tidak
            boolean checkProduct = false;
            try {
				ResultSet rs = db.getResults("SELECT * FROM products WHERE id = ?", choice);
				while(rs.next()) {
					checkProduct = true;
					
					//get product index
					for(int i = 0; i < Main.PRODUCT_LIST.size(); i++) {
						if(Main.PRODUCT_LIST.get(i).getID() == choice) {
							productIdx = i;
							break;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
            if(!checkProduct){
            	System.out.println("Cannot find product");
                continue;
            }

            break;
        }

        Product chosenProduct = Main.PRODUCT_LIST.get(productIdx);
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
        Main.CURRENT_USER.getCart().add(order);
        
        //masukkan ke db
        //masukin ke receipt dengan status pending (0), purchase_date masih null
//        System.out.println(Main.userId);
        db.execute("INSERT INTO receipts VALUES(NULL, ?, NULL, ?, 0)", Main.userId, (quantity * chosenProduct.getPrice()));
        
        int receiptId = 0;
        try {
			ResultSet rs = db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 0", Main.userId);
			while(rs.next()) {
				receiptId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        db.execute("INSERT INTO orders VALUES(NULL, ?, ?, ?)", receiptId, chosenProduct.getID(), quantity);

        System.out.println("Product is added to the cart!");
        Utils.waitForEnter();
    }

    public static void showCartProducts() {
        Utils.clearScreen();

        List<Order> cart = Main.CURRENT_USER.getCart();
        Views.showCartView();

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

        List<Order> cart = Main.CURRENT_USER.getCart();
        Views.showCartView();

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
        Main.CURRENT_USER.getReceiptList().add(receipt);
        cart.clear();

        System.out.println("Successfully purchase products!");
        Utils.waitForEnter();
    }
}
