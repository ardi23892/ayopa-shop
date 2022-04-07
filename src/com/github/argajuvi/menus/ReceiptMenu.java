package com.github.argajuvi.menus;

import com.github.argajuvi.Main;
import com.github.argajuvi.models.order.Order;
import com.github.argajuvi.models.product.BookProduct;
import com.github.argajuvi.models.product.ClothingProduct;
import com.github.argajuvi.models.product.FoodProduct;
import com.github.argajuvi.models.product.Product;
import com.github.argajuvi.models.receipt.Receipt;
import com.github.argajuvi.utils.Utils;

import java.util.List;

public class ReceiptMenu implements Menu{

    @Override
    public void showMenu() {
        while (true) {
            Utils.clearScreen();

            List<Receipt> receiptList = Main.currentUser.getReceiptList();
            this.showReceiptsView();

            if (receiptList.isEmpty()) {
                Utils.waitForEnter();
                return;
            }

            int receiptId;
            Receipt foundReceipt = null;

            while (true) {
                receiptId = Utils.scanAbsoluteInt("Choose receipt ID to see detail ['0' to go back]: ");
                String notFoundMessage = "Cannot find receipt";

                if (receiptId == 0) {
                    return;
                }
                if (receiptId < 1) {
                    System.out.println(notFoundMessage);
                    continue;
                }


                for (Receipt receipt : receiptList) {
                    if (receipt.getId() == receiptId) {
                        foundReceipt = receipt;
                        break;
                    }
                }

                if (foundReceipt == null) {
                    System.out.println(notFoundMessage);
                    continue;
                }

                break;
            }

            System.out.println();
            System.out.println("---------------------------------------");
            System.out.println("Receipt ID   : " + foundReceipt.getId());
            System.out.println("Purchase Date: " + Utils.DATE_FORMATTER.format(foundReceipt.getPurchaseDate()));
            System.out.println("Total Price  : " + Utils.formatPriceIDR(foundReceipt.getTotalPrice()));
            System.out.println();

            List<Order> orderList = foundReceipt.getOrderList();
            int count = 0;

            for (Order order : orderList) {
                Product product = order.getProduct();

                count++;
                System.out.println(count + ". [" + product.getType().getName() + "] " + product.getName());
                System.out.println("   - Price       : " + Utils.formatPriceIDR(product.getPrice()));

                if (product instanceof BookProduct) {
                    BookProduct book = (BookProduct) product;

                    System.out.println("   - Author      : " + book.getAuthor());
                    System.out.println("   - Publish Year: " + book.getPublishYear());
                } else if (product instanceof ClothingProduct) {
                    ClothingProduct cloth = (ClothingProduct) product;

                    System.out.println("   - Size: " + cloth.getSize());
                } else if (product instanceof FoodProduct) {
                    FoodProduct food = (FoodProduct) product;

                    System.out.println("   - Expire Date : " + Utils.DATE_FORMATTER.format(food.getExpireDate()));
                }
            }

            System.out.println("---------------------------------------");

            Utils.waitForEnter();
        }
    }

    private void showReceiptsView() {
        List<Receipt> receiptList = Main.currentUser.getReceiptList();

        if (receiptList.isEmpty()) {
            System.out.println("You haven't purchase anything from our shop.");
        } else {
            String rowFormat = "| %-15s | %-13s | %-20s |\n";
            String line = "-----------------------------------------------------------\n";

            System.out.print(line);
            System.out.printf(rowFormat, "Receipt ID", "Purchase Date", "Total Price");
            System.out.print(line);

            for (Receipt receipt : receiptList) {
                System.out.printf(
                        rowFormat,
                        receipt.getId() + "",
                        Utils.DATE_FORMATTER.format(receipt.getPurchaseDate()),
                        Utils.formatPriceIDR(receipt.getTotalPrice())
                );
            }

            System.out.print(line);
        }
    }
}
