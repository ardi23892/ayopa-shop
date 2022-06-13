package com.github.argajuvi.utils;

import com.github.argajuvi.Main;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.product.ProductType;
import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class Views {

    /**
     * Shows the list of products in a table view
     *
     * @param filterType the type of product should be shown on the view,
     *                   insert {@code null} to not use any filters
     */
    public static void showProductsView(@Nullable ProductType filterType) {
        if (Main.PRODUCT_LIST.isEmpty()) {
            System.out.println("No products found.");
        } else {
            String rowFormat = "| %3s | %-40s | %-20s | %-12s |\n";
            String line = "----------------------------------------------------------------------------------------\n";

            System.out.print(line);
            System.out.printf(rowFormat, "ID.", "Product Name", "Product Price", "Product Type");
            System.out.print(line);

            List<Product> filteredProductList = Main.PRODUCT_LIST;
            if (filterType != null) {
                // Filters the product list to a specific type of product
                filteredProductList = Main.PRODUCT_LIST.stream()
                        .filter(product -> product.getType() == filterType)
                        .collect(Collectors.toList());
            }

            for (Product product : filteredProductList) {
                System.out.printf(
                        rowFormat,
                        product.getId() + "", product.getName(),
                        Utils.formatPriceIDR(product.getPrice()),
                        product.getType().getName()
                );
            }

            System.out.print(line);
        }
    }

    /**
     * @see Views#showProductsView(ProductType)
     */
    public static void showProductsView() {
        showProductsView(null);
    }

    public static void showCartView() {
        List<Order> cart = Main.CURRENT_USER.getCart();

        if (cart.isEmpty()) {
            System.out.println("You haven't ordered anything.");
        } else {
            String rowFormat = "| %3s | %-40s | %-20s | %-12s | %8s | %-20s |\n";
            String line = "--------------------------------------------------------------------------------------------------------------------------\n";
            int count = 0;

            System.out.print(line);
            System.out.printf(
                    rowFormat,
                    "ID.",
                    "Product Name", "Product Price", "Product Type",
                    "Quantity", "Total Price"
            );
            System.out.print(line);

            int totalOfTotalPrice = 0;
            for (Order order : cart) {
                count++;

                Product product = order.getProduct();
                totalOfTotalPrice += order.getTotalPrice();

                System.out.printf(
                        rowFormat,
                        count + "",
                        product.getName(),
                        Utils.formatPriceIDR(product.getPrice()), product.getType().getName(),
                        order.getQuantity() + "",
                        Utils.formatPriceIDR(order.getTotalPrice())
                );
            }

            System.out.print(line);
            System.out.printf("| %-46s | %-69s |\n", "Total Price", Utils.formatPriceIDR(totalOfTotalPrice));
            System.out.print(line);
        }
    }

}
