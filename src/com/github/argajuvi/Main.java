package com.github.argajuvi;

import com.github.argajuvi.database.Database;
import com.github.argajuvi.menus.InitialMenu;
import com.github.argajuvi.menus.Menu;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.product.ProductFactory;
import com.github.argajuvi.models.product.ProductType;
import com.github.argajuvi.models.receipt.Receipt;
import com.github.argajuvi.models.user.User;
import com.github.argajuvi.utils.Closer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static List<Product> PRODUCT_LIST = new ArrayList<>();
    public static List<User> USER_LIST = new ArrayList<>();

    /**
     * Stores the currently logged-in user.
     */
    public static User CURRENT_USER = null;

    private void createTables(Database db) {
        db.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "  id INT AUTO_INCREMENT, " +
                "  username VARCHAR(255) NOT NULL, " +
                "  password VARCHAR(255) NOT NULL, " +
                "" +
                "  PRIMARY KEY (id)" +
                ");");

        db.execute(
                "CREATE TABLE IF NOT EXISTS products (" +
                "  id INT AUTO_INCREMENT, " +
                "  name VARCHAR(255) NOT NULL, " +
                "  price INT NOT NULL, " +
                "  type INT NOT NULL, " +
                "" +
                "  size CHAR(1), " +
                "" +
                "  publish_year INT, " +
                "  author VARCHAR(255), " +
                "" +
                "  expire_date DATE, " +
                "" +
                "  PRIMARY KEY (id)" +
                ");");

        db.execute(
                "CREATE TABLE IF NOT EXISTS receipts (" +
                "  id INT AUTO_INCREMENT, " +
                "  user_id INT NOT NULL, " +
                "  purchase_date DATE, " +
                "  total_price INT NOT NULL, " +
                "  status INT NOT NULL, " +
                "  PRIMARY KEY (id), " +
                "  FOREIGN KEY (user_id) REFERENCES users (id)" +
                ");");

        db.execute(
                "CREATE TABLE IF NOT EXISTS orders (" +
                "  id INT AUTO_INCREMENT, " +
                "  receipt_id INT NOT NULL, " +
                "  product_id INT NOT NULL, " +
                "  quantity INT NOT NULL, " +
                "" +
                "  PRIMARY KEY (id), " +
                "  FOREIGN KEY (receipt_id) REFERENCES receipts (id), " +
                "  FOREIGN KEY (product_id) REFERENCES products (id)" +
                ");");

        db.execute("CREATE TABLE IF NOT EXISTS seeder (last_seed DATE NOT NULL);");
    }

    private void seedData(Database db) {
        boolean isSeeded = false;

        try (ResultSet rs = db.getResults("SELECT * FROM seeder")) {
            isSeeded = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isSeeded) {
            return;
        }

        String insertProductQuery = "INSERT INTO products VALUES (" +
                                    // id, name, price, type
                                    "  NULL, ?, ?, ?, " +
                                    // size
                                    "  ?, " +
                                    // publish_year, author
                                    "  ?, ?, " +
                                    // expire_date
                                    "  ?)";

        db.execute(insertProductQuery,
                "Java OOP Done Right", 473_000, ProductType.BOOK.ordinal(),
                null,
                2021, "Alan Mellor",
                null);

        db.execute(insertProductQuery,
                "Tate no Yusha no Nariagari Vol. 1", 317_000, ProductType.BOOK.ordinal(),
                null,
                2013, "Aneko Yusagi",
                null);

        db.execute(insertProductQuery,
                "Shingeki no Kyojin Zip Hoodie", 387_000, ProductType.CLOTHING.ordinal(),
                "L",
                null, null,
                null);

        LocalDate expiredDate = LocalDate.now().plus(1, ChronoUnit.MONTHS);
        db.execute(insertProductQuery,
                "Lays 1 Ounce (Pack of 104)", 831_476, ProductType.FOOD.ordinal(),
                null,
                null, null,
                Date.valueOf(expiredDate));

        String insertUserQuery = "INSERT INTO users VALUES (NULL, ?, ?)";
        db.execute(insertUserQuery, "admin", "admin123");
        db.execute(insertUserQuery, "example", "example123");
        db.execute(insertUserQuery, "johndoe", "doerMu123");

        // mark as done
        db.execute("INSERT INTO seeder VALUES (NOW())");
    }

    public Main() throws ParseException {
        Database db = Database.getInstance();
        try {
            db.connect();

            this.createTables(db);
            this.seedData(db);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<Integer, Integer> productMap = new HashMap<>();

        int i = 0;
        try (ResultSet rs = db.getResults("SELECT * FROM products")) {
            String name;
            int price, type;

            while (rs.next()) {
                int id = rs.getInt("id");

                name = rs.getString("name");
                type = rs.getInt("type");
                price = rs.getInt("price");

                productMap.put(id, i++);
                Product product = null;

                if (type == ProductType.CLOTHING.ordinal()) {
                    String size = rs.getString("size");
                    char sizeChar = size.charAt(0);

                    product = ProductFactory.createClothProduct(id, name, price, sizeChar);
                } else if (type == ProductType.BOOK.ordinal()) {
                    int publishYear = rs.getInt("publish_year");
                    String author = rs.getString("author");

                    product = ProductFactory.createBookProduct(id, name, price, publishYear, author);
                } else if (type == ProductType.FOOD.ordinal()) {
                    Date date = rs.getDate("expire_date");

                    product = ProductFactory.createFoodProduct(id, name, price, date);
                }

                if (product != null) {
                    PRODUCT_LIST.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int userIdx = 0;
        try (Closer closer = new Closer()) {
            ResultSet rs = closer.add(db.getResults("SELECT * FROM users"));

            String username;
            String password;

            while (rs.next()) {
                int userId = rs.getInt("id");
                username = rs.getString("username");
                password = rs.getString("password");

                USER_LIST.add(new User(userId, username, password));

                //cek product dan receipt nya, yang receiptnya sudah di checkout
                ResultSet rsReceipt = closer.add(db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 1", userId));
                while (rsReceipt.next()) {
                    int receiptId = rsReceipt.getInt("id");

                    ResultSet rsOrder = closer.add(db.getResults("SELECT * FROM orders WHERE receipt_id = ? ", receiptId));
                    List<Order> newOrder = new ArrayList<>();

                    while (rsOrder.next()) {
                        int orderId = rsOrder.getInt("id");
                        int productId = rsOrder.getInt("product_id");
                        int quantity = rsOrder.getInt("quantity");

                        newOrder.add(new Order(orderId, PRODUCT_LIST.get(productMap.get(productId)), quantity));
                    }

                    Date purchaseDate = rsReceipt.getDate("purchase_date");
                    int totalPrice = rsReceipt.getInt("total_price");

                    USER_LIST.get(userIdx).getReceiptList().add(new Receipt(receiptId, newOrder, purchaseDate, totalPrice));
                }

                //cek product dan receiptnya, yang reciptnya belum di checkout (cart)
                rsReceipt = closer.add(db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 0", userId));
                while (rsReceipt.next()) {
                    int receiptId = rsReceipt.getInt("id");
                    ResultSet rsOrder = db.getResults("SELECT * FROM orders WHERE receipt_id = ?", receiptId);

                    while (rsOrder.next()) {
                        int orderId = rsOrder.getInt("id");
                        int productId = rsOrder.getInt("product_id");
                        int quantity = rsOrder.getInt("quantity");

                        USER_LIST.get(userIdx).getCart().add(new Order(orderId, PRODUCT_LIST.get(productMap.get(productId)), quantity));
                    }
                }

                userIdx++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Menu initialMenu = new InitialMenu();
        initialMenu.showMenu();
    }

    public static void main(String[] args) throws ParseException {
        new Main();
    }

}
