package com.github.argajuvi;

import com.github.argajuvi.database.Database;
import com.github.argajuvi.menus.InitialMenu;
import com.github.argajuvi.menus.Menu;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.BookProduct;
import com.github.argajuvi.models.product.ClothingProduct;
import com.github.argajuvi.models.product.FoodProduct;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.receipt.Receipt;
import com.github.argajuvi.models.user.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Main {
	
    public static List<Product> PRODUCT_LIST = new ArrayList<>(); /*= Stream.of(
            new BookProduct(
                    "Java OOP Done Right",
                    473_426,
                    2021,
                    "Alan Mellor"),
            new BookProduct(
                    "Tate no Yusha no Nariagari Vol. 1",
                    317_102,
                    2013,
                    "Aneko Yusagi"),
            new ClothingProduct(
                    "Shingeki no Kyojin Zip Hoodie",
                    387_792,
                    'L')
    ).collect(Collectors.toList());*/
    
    public static List<User> USER_LIST = new ArrayList<>(); /* = Stream.of(
            new User("admin", "admin123"),
            new User("example", "example123"),
            new User("johndoe", "doerMu123")
    ).collect(Collectors.toList());*/

    /**
     * Stores the currently logged-in user.
     */
    public static User CURRENT_USER = null;
    public static int userId = 0;

    public static void main(String[] args) throws ParseException {
        Database db = Database.getInstance();
        try {
            db.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        
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
                "  purchase_date DATE NULL, " +
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
        
        db.execute(
        		"CREATE TABLE IF NOT EXISTS seeder (" +
        		" check_data INT NOT NULL);");
        
        boolean check = false;
        try {
			ResultSet rs = db.getResults("SELECT * FROM seeder");
			while(rs.next()) {
				check = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        if(!check) {
        	db.execute("INSERT INTO seeder VALUES(1)");
        	
        	Date date = formatter.parse(LocalDate.now().plus(1, ChronoUnit.MONTHS).toString());
        	
        	db.execute("INSERT INTO products(id, name, price, type, size, publish_year, author, expire_date) VALUES (NULL, 'Java OOP Done Right', 473000, 2, NULL, 2021, 'Alan Mellor', NULL), "
        			+ "(NULL, 'Tate no Yusha no Nariagari Vol. 1', 317000, 2, NULL, 2013, 'Aneko Yusagi', NULL), "
        			+ "(NULL, 'Shingeki no Kyojin Zip Hoodie', 387000, 1, 'L', NULL, NULL, NULL)");
        	
        	db.execute("INSERT INTO products(id, name, price, type, size, publish_year, author, expire_date) VALUES (NULL, 'Lays 1 Ounce (Pack of 104)', 831476, 3, NULL, NULL, NULL, ?);", date);
        	
        	db.execute("INSERT INTO users(id, username, password) VALUES (NULL, 'admin', 'admin123'), "
        			+ "(NULL, 'example', 'example123'), (NULL, 'johndoe', 'doerMu123')");
        }
        
        HashMap<Integer, Integer>productMap = new HashMap<>();
        
        int i = 0;
		try {
			ResultSet rs = db.getResults("SELECT * FROM products");
			String name = "";
			int price = 0;
			int type = 0;
			
			while(rs.next()) {
				name = rs.getString("name");
				type = rs.getInt("type");
				price = rs.getInt("price");
				int id = rs.getInt("id");
				productMap.put(id, i++);
				
				if(type == 1) {
					//ClothingProduct
					String size = rs.getString("size");
					char sizeChar = size.charAt(0);
					PRODUCT_LIST.add(new ClothingProduct(id, name, price, sizeChar));
					
					
				}else if(type == 2) {
					//BookProduct
					int publishYear = rs.getInt("publish_year");
					String author = rs.getString("author");
					PRODUCT_LIST.add(new BookProduct(id, name, price, publishYear, author));
					
				}else if(type == 3) {
					//FoodProduct
					Date date = rs.getDate("expire_date");
					PRODUCT_LIST.add(new FoodProduct(id, name, price, date)); 
					
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
        
		int userIdx = 0;
		try {
			ResultSet rs = db.getResults("SELECT * FROM users");
			String username = "";
			String password = "";
			
			while(rs.next()) {
				int userId = rs.getInt("id");
				username = rs.getString("username");
				password = rs.getString("password");
				
				USER_LIST.add(new User(username, password));
				
				//cek product dan receipt nya, yang receiptnya sudah di checkout
				ResultSet rsReceipt = db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 1", userId);
				while(rsReceipt.next()) {
					int receiptId = rsReceipt.getInt("id");
//					System.out.println(receiptId);
					ResultSet rsOrder = db.getResults("SELECT * FROM orders WHERE receipt_id = ? ", receiptId);
					List<Order>newOrder = new ArrayList<>();
					while(rsOrder.next()) {
						int orderId = rsOrder.getInt("id");
						int productId = rsOrder.getInt("product_id");
						int quantity = rsOrder.getInt("quantity");
						newOrder.add(new Order(orderId, PRODUCT_LIST.get(productMap.get(productId)), quantity));
					}
					Date purchaseDate = rsReceipt.getDate("purchase_date");
					int totalPrice = rsReceipt.getInt("total_price");
					USER_LIST.get(userIdx).getReceiptList().add(new Receipt(receiptId, newOrder, purchaseDate, totalPrice));
//					System.out.println("TestCheckOut");
				}
				
				//cek product dan receiptnya, yang reciptnya belum di checkout (cart)
				rsReceipt = db.getResults("SELECT * FROM receipts WHERE user_id = ? AND status = 0", userId);
				while(rsReceipt.next()) {
					int receiptId = rsReceipt.getInt("id");
					ResultSet rsOrder = db.getResults("SELECT * FROM orders WHERE receipt_id = ?", receiptId);
					while(rsOrder.next()) {
						int orderId = rsOrder.getInt("id");
						int productId = rsOrder.getInt("product_id");
						int quantity = rsOrder.getInt("quantity");
						USER_LIST.get(userIdx).getCart().add(new Order(orderId, PRODUCT_LIST.get(productMap.get(productId)), quantity));
//						System.out.println("TestCart, userIdx = " + userIdx);
					}
				}
//				for(int x = 0; x < 3; x++) {
//					System.out.println(USER_LIST.get(userIdx).getCart().get(x).getProduct().getName());
//				}
				userIdx++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
        Menu initialMenu = new InitialMenu();
        initialMenu.showMenu();
    }

}
