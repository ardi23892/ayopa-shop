package com.github.argajuvi;

import com.github.argajuvi.database.Database;
import com.github.argajuvi.menus.InitialMenu;
import com.github.argajuvi.menus.Menu;
import com.github.argajuvi.models.product.BookProduct;
import com.github.argajuvi.models.product.ClothingProduct;
import com.github.argajuvi.models.product.FoodProduct;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.user.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static List<Product> PRODUCT_LIST = Stream.of(
            new BookProduct(
                    "Java OOP Done Right",
                    473_426,
                    2021,
                    " Alan Mellor"),
            new BookProduct(
                    "Tate no Yusha no Nariagari Vol. 1",
                    317_102,
                    2013,
                    "Aneko Yusagi"),
            new FoodProduct(
                    "Lay's, 1 Ounce (Pack of 104)",
                    831_476,
                    LocalDate.now().plus(1, ChronoUnit.MONTHS)),
            new ClothingProduct(
                    "Shingeki no Kyojin Zip Hoodie",
                    387_792,
                    'L')
    ).collect(Collectors.toList());
    
    

    public static List<User> USER_LIST = Stream.of(
            new User("admin", "admin123"),
            new User("example", "example123"),
            new User("johndoe", "doerMu123")
    ).collect(Collectors.toList());

    /**
     * Stores the currently logged-in user.
     */
    public static User CURRENT_USER = null;

    public static void main(String[] args) {
        Database db = Database.getInstance();
        try {
            db.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
                "  purchase_date DATE NOT NULL, " +
                "  total_price INT NOT NULL, " +
                "" +
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

        Menu initialMenu = new InitialMenu();
        initialMenu.showMenu();
    }

}
