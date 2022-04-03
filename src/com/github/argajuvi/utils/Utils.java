package com.github.argajuvi.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {

    public final static Scanner SCANNER = new Scanner(System.in);

    /**
     * Clears the console screen
     */
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Scans the console input for integer value
     * <p>
     * This shortens the use of {@link Scanner#nextInt()} where you need to
     * clear the buffer manually using {@link Scanner#nextLine()}.
     *
     * @param defaultVal The default value given when the user
     *                   gives an invalid input (let's say they give a string)
     */
    public static int scanInt(int defaultVal) {
        int input = defaultVal;

        try {
            input = SCANNER.nextInt();
        } catch (InputMismatchException ignored) {
        } finally {
            // clears the buffer
            SCANNER.nextLine();
        }

        return input;
    }

    /**
     * Ever had those in console app where it says "Press enter to continue..."?
     * Yeah, this is it.
     */
    public static void waitForEnter() {
        System.out.print("Press enter to continue...");
        SCANNER.nextLine();
    }

}
