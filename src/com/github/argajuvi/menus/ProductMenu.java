package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.database.Database;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.receipt.Receipt;
import com.github.argajuvi.models.user.User;
import com.github.argajuvi.utils.Closer;
import com.github.argajuvi.utils.Utils;
import com.github.argajuvi.utils.Views;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
                while (rs.next()) {
                    checkProduct = true;

                    //get product index
                    for (int i = 0; i < Main.PRODUCT_LIST.size(); i++) {
                        if (Main.PRODUCT_LIST.get(i).getId() == choice) {
                            productIdx = i;
                            break;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (!checkProduct) {
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

        User user = Main.CURRENT_USER;


        //masukkan ke db
        //masukin ke receipt dengan status pending (0), purchase_date masih null
//        System.out.println(Main.userId);
        //cari dulu di table receipts yang user_id(sama) sama statusnya pending (0)
        int receiptId = 0;
        boolean newReceipt = true;
        try (Closer closer = new Closer()) {
            ResultSet rs = closer.add(db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 0", user.getId()));

            while (rs.next()) {
                receiptId = rs.getInt("id");

                newReceipt = false;
                ResultSet rsOrder = closer.add(db.getResults("SELECT * FROM orders, products WHERE receipt_id = ? AND orders.product_id = products.id", receiptId));

                int totalPrice = 0;
                while (rsOrder.next()) {
                    totalPrice += rsOrder.getInt("price");
                }
                db.execute("UPDATE receipts SET total_price = ? WHERE id = ?", totalPrice, receiptId);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (newReceipt) {
            // ketika belum pernah masukin ke cart atau sudah checkout
            db.execute("INSERT INTO receipts (user_id, total_price, status) VALUES (?, ?, false)", user.getId(), (quantity * chosenProduct.getPrice()));

            try {
                ResultSet rs = db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 0", user.getId());
                while (rs.next()) {
                    receiptId = rs.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        db.execute("INSERT INTO orders VALUES (NULL, ?, ?, ?)", receiptId, chosenProduct.getId(), quantity);

        ResultSet rs;
        int idOrder = 0;
        try {
            rs = db.getResults("SELECT * FROM orders WHERE receipt_id = ?", receiptId);

            int countOrder = user.getCart().size();
            int count = 0;

            while (rs.next()) {
                count++;
                if (countOrder == count) idOrder = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Order order = new Order(idOrder, chosenProduct, quantity);
        user.getCart().add(order);

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

        //hapus dari db ketika name dan quantity sama
        int idOrder = cart.get(choice - 1).getId();
        Database db = Database.getInstance();
        db.execute("DELETE FROM orders WHERE id = ?", idOrder);

        cart.remove(choice - 1);
        System.out.println("Successfully removed order from the cart");

        Utils.waitForEnter();
    }

    public static void showCheckout() {
        Utils.clearScreen();

        User user = Main.CURRENT_USER;
        List<Order> cart = user.getCart();

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

        Date now = new Date();


        //ubah receipt yang pending jadi selesai (1)
        Database db = Database.getInstance();
        int receiptId = 0;
        try {
            ResultSet rs = db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 0", user.getId());
            while (rs.next()) {
                receiptId = rs.getInt("id");
                db.execute("UPDATE receipts SET status = 1, purchase_date = ? WHERE id = ?", now, receiptId);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Receipt receipt = new Receipt(receiptId, orderList, now, totalOfTotalPrice);
        //nambah receipt ke data local
        Main.CURRENT_USER.getReceiptList().add(receipt);

        cart.clear();

        System.out.println("Successfully purchase products!");
        Utils.waitForEnter();
    }
}
