package com.github.argajuvi.utils;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {

    public final static Scanner SCANNER;
    public final static DateTimeFormatter DATE_FORMATTER;

    static {
        SCANNER = new Scanner(System.in);
        DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    /**
     * Clears the console screen
     */
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Scans the console for integer input, but it MUST be integer.
     * <p>
     * This input scanner already clears the buffer automatically.
     * <p>
     * The word "absolute" from this function means it will try again and again
     * until the scanner gets integer as its value.
     *
     * @param prefix The string printed before asking for input,
     *               maybe something like ">> " can make it look pretty.
     */
    public static int scanAbsoluteInt(String prefix) {
        while (true) {
            System.out.print(prefix);

            try {
                return SCANNER.nextInt();
            } catch (InputMismatchException ignored) {
                System.out.println("Input must be integer");
            } finally {
                // clears the buffer
                SCANNER.nextLine();
            }
        }
    }

    /**
     * This function will scan for the absolute confirmation of 'yes or no' question.
     * It only asks for 'y' for yes and 'n' for no. Anything else? Denied
     * 
     * @see Utils#scanAbsoluteInt(String)
     */
    public static boolean scanAbsoluteConfirmation(String prefix) {
        while (true) {
            System.out.print(prefix);

            String errorMessage = "Input must be 'y' or 'n'";
            String line = SCANNER.nextLine();

            if (!line.equals("y") && !line.equals("n")) {
                System.out.println(errorMessage);
                continue;
            }

            return line.equals("y");
        }
    }

    /**
     * Ever had those in console app where it says "Press enter to continue..."?
     * Yeah, this is it.
     */
    public static void waitForEnter() {
        System.out.print("Press enter to continue...");
        SCANNER.nextLine();
    }

    /**
     * Formats the price (from integer) to IDR (Indonesian Rupiah) format.
     */
    public static String formatPriceIDR(int price) {
        NumberFormat formatter = NumberFormat.getInstance();
        String formattedPrice = formatter.format(price).replace(",", ".");

        return "Rp " + formattedPrice + ",00";
    }

}
