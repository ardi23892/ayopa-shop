package com.github.argajuvi;

import com.github.argajuvi.menus.InitialMenu;
import com.github.argajuvi.menus.Menu;
import com.github.argajuvi.models.product.BookProduct;
import com.github.argajuvi.models.product.ClothingProduct;
import com.github.argajuvi.models.product.FoodProduct;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.user.User;

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
        Menu initialMenu = new InitialMenu();
        initialMenu.showMenu();
    }

}

// Testing design-pattern branch commit
