package utils.ui;

import java.util.Scanner;

public class TicketMenu {
    private TicketMenu() {}

    private static Scanner scanner = new Scanner(System.in);

    /** Obté un valor bolea basat en sí o no. */
    public static boolean getYesOrNo(String prompt) {
        // * Declaració de variables
        String input;
        boolean yes;

        // * Bucle principal
        while (true) {
            System.out.printf("%s (s/n)", prompt);
            input = scanner.nextLine();

            // * Validació inicial
            if (input.isBlank()) {
                Prettier.warn("La resposta no pot estar en blanc. Si us plau, torni a intentar-ho.");
                continue;
            }

            input = input.trim();

            // * Retorna en cas de s/n
            yes = input.equalsIgnoreCase("s");
            if (yes || input.equalsIgnoreCase("n")) {
                return yes;
            }
        }
    }

    public static void pause() {
        System.out.print("Prem enter per continuar... ");
        scanner.nextLine();
    }

    /** Carrega el menú i obté l'opció. */
    public static int loadMenu() {
        showMenu();

        System.out.println();

        return getOption(0, 3);
    }

    /** Mostra el menú. */
    private static void showMenu() {
        System.out.println("== GESTOR DE COMANDES ==");
        System.out.println("0. Sortir");
        System.out.println("1. Crear nova comanda");
        System.out.println("2. Afegir línea a la comanda actual");
        System.out.println("3. Veure tiquet actual");
    }

    /** Obté l'opció de l'usuari. */
    private static int getOption(int min, int max) {
        // * Declaració de variables
        String input;
        int value;

        // * Bucle principal
        while (true) {
            System.out.printf("Introdueixi una opció (%d-%d), si us plau: ", min, max);
            input = scanner.nextLine();

            // * Validació inicial
            if (input.isBlank()) {
                Prettier.warn("L'opció a elegir no por estar en blanc. Si us plau, torni a intentar-ho.");
                continue;
            }

            input = input.trim();

            try { // ? Obtenir valor
                value = Integer.parseInt(input);

                if (value >= min && value <= max) { // ? Fora del rang
                    return value;
                }

                String msg = String.format("L'opció elegida està fora del rang permès (%d-%d). Si us plau, torni a intentar-ho.", min, max);
                Prettier.warn(msg);

            } catch (NumberFormatException e) {
                Prettier.warn("El format de l'opció introduïda no es valid. Si us plau, torni a intentar-ho.");
            } catch (Exception e) {
                Prettier.warn("Ha ocorregut un error desconegut. Si us plau, torni a intentar-ho.");
            }
        }
    }
}
