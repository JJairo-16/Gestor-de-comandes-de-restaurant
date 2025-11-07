package utils;

import java.util.Scanner;
import utils.ui.Prettier;

public class LineGetter {
    // #region Regles
    // * Nom del client
    private static final int MIN_CLIENT_NAME_LEN = 3;
    private static final int MAX_CLIENT_NAME_LEN = 20;

    // * Nom del producte
    private static final int MIN_PRODUCT_NAME_LEN = 2;
    private static final int MAX_PRODUCT_NAME_LEN = 15;

    // #endregion

    private LineGetter() {}

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Obté el nom del client amb una longitud d'entre {@value LineGetter#MIN_CLIENT_NAME_LEN} i {@value LineGetter#MAX_CLIENT_NAME_LEN} caràcters.
     */
    public static String getClientName() {
        String input;

        while (true) {
            System.out.print("Introdueixi el nom del client, si us plau: ");
            input = scanner.nextLine();

            if (input.isBlank()) {
                Prettier.warn("El nom no pot estar en blanc. Si us plau, torni a intentar-ho.");
                continue;
            }

            input = input.trim();

            if (input.length() >= MIN_CLIENT_NAME_LEN && input.length() <= MAX_CLIENT_NAME_LEN) {
                return input;
            }

            String msg = String.format("La longitud del nom introduït està fora del rang permès (%d-%d caràcters). Si us plau, torni a intentar-ho.", MIN_CLIENT_NAME_LEN, MAX_CLIENT_NAME_LEN);
            Prettier.warn(msg);
        }
    }

    /**
     * Obté el nom del producte amb una longitud d'entre {@value LineGetter#MIN_PRODUCT_NAME_LEN} i {@value LineGetter#MAX_PRODUCT_NAME_LEN} caràcters.
     */
    public static String getProductName() {
        String input;

        while (true) {
            System.out.print("Introdueixi el nom del producte, si us plau: ");
            input = scanner.nextLine();

            if (input.isBlank()) {
                Prettier.warn("El nom no pot estar en blanc. Si us plau, torni a intentar-ho.");
                continue;
            }

            input = input.trim();

            if (input.length() >= MIN_PRODUCT_NAME_LEN && input.length() <= MAX_PRODUCT_NAME_LEN) {
                return input;
            }

            String msg = String.format("La longitud del nom introduït està fora del rang permès (%d-%d caràcters). Si us plau, torni a intentar-ho.", MIN_PRODUCT_NAME_LEN, MAX_PRODUCT_NAME_LEN);
            Prettier.warn(msg);
        }
    }

    /** Obté el preu del producte (en €). */
    public static double getProductPrice() {
        String input;
        double value;

        while (true) {
            System.out.print("Introdueixi el preu del producte (EUR), si us plau: ");
            input = scanner.nextLine();

            if (input.isBlank()) {
                Prettier.warn("El preu del producte no pot estar en blanc. Si us plau, torni a intentar-ho.");
                continue;
            }

            input = input.trim();

            try {
                value = Double.parseDouble(input);

                if (value < 0) {
                    Prettier.warn("El preu del producte no pot ser negatiu. Si us plau, torni a intentar-ho.");
                    continue;
                }

                value = Math.round(value * 100.0) / 100.0;

                return value;

            } catch (NumberFormatException e) {
                Prettier.warn("El format del preu introduït no és valid. Si us plau, torni a intentar-ho.");
            } catch (Exception e) {
                Prettier.warn("Ha ocorregut un error desconegut. Si us plau, torni a intentar-ho.");
            }
        }
    }

    /** Obté la quantitat d'unitats del producte. */
    public static int getProductQuantity() {
        String input;
        int value;

        while (true) {
            System.out.print("Introdueixi la quantitat d'unitats del producte, si us plau: ");
            input = scanner.nextLine();

            if (input.isBlank()) {
                Prettier.warn("La quantitat d'unitats no pot estar en blanc. Si us plau, torni a intentar-ho.");
                continue;
            }

            input = input.trim();

            try {
                value = Integer.parseInt(input);

                if (value < 1) {
                    Prettier.warn("La quantitat d'unitats no pot ser menor a 1. Si us plau, torni a intentar-ho.");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                Prettier.warn("El format del nombre introduït no és valid. Si us plau, torni a intentar-ho.");
            } catch (Exception e) {
                Prettier.warn("Ha ocorregut un error desconegut. Si us plau, torni a intentar-ho.");
            }
        }
    }
}
