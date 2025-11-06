import services.TicketManager;

import utils.ui.TicketMenu;
import utils.LineGetter;

import utils.ui.Cleaner;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private TicketManager manager;
    
    private Cleaner cls = new Cleaner();

    public void run() {
        // * Declaració de variables
        int option;
        boolean ticketCreated;
        String ticket;

        // * Gestor de comandes
        manager = new TicketManager();
        
        // * Bucle principal
        while (true) {
            cls.clear();

            // * Menú
            option = TicketMenu.loadMenu();

            if (option == 0) { // ? Sortir
                break;
            }

            ticketCreated = manager.existsTicket();

            switch (option) {
                case 1: // ? Nova comanda
                    cls.clear();
                    System.out.println("== Nova comanda ==");
                    
                    // * Crear comanda
                    String clientName = LineGetter.getClientName();
                    manager.newTicket(clientName);
                    System.out.println();

                    // * Afegir líneas
                    getLines();
                    cls.clear();

                    // * Mostrar tiquet
                    ticket = manager.getTicket(TicketManager.SINGLE_TICKET);
                    System.out.println(ticket);

                    System.out.println();
                    TicketMenu.pause();
                    break;
                
                case 2: // ? Modificar comanda
                    if (!ticketCreated) { // ? No s'ha creat una comanda
                        ticketNotExists();
                        break;
                    }

                    cls.clear();
                    System.out.println("== Modificar comanda ==");

                    // * Afegir líneas
                    getLines();
                    cls.clear();

                    // * Mostrar tiquet
                    ticket = manager.getTicket(TicketManager.UPDATE_TICKET);
                    System.out.println(ticket);

                    System.out.println();
                    TicketMenu.pause();
                    break;
                
                case 3: // ? Mostrar tiquet
                    if (!ticketCreated) { // ? No s'ha creat una comanda
                        ticketNotExists();
                        break;
                    }

                    cls.clear();

                    // * Mostrar tiquet
                    ticket = manager.getTicket(TicketManager.LAST_TICKET);
                    System.out.println(ticket);

                    System.out.println();
                    TicketMenu.pause();
                    break;
            }
        }

        System.out.println();
        System.out.println("FINS LA PROPERA!");
    }

    /** Mostra el missatge corresponent si no hi ha tiquet. */
    private void ticketNotExists() {
        System.out.println();
        
        System.out.println("Aquesta opció no està disponible fins que creï una comanda (opció 1). Si us plau, creï una prèviament.");

        System.out.println();

        TicketMenu.pause();
    }

    /** Introdueix línies al tiquet. */
    private void getLines() {
        String name;
        double price;
        int quantity;

        boolean willContinue;

        while (true) {
            name = LineGetter.getProductName();
            price = LineGetter.getProductPrice();
            quantity = LineGetter.getProductQuantity();

            manager.addLine(name, price, quantity);

            willContinue = TicketMenu.getYesOrNo("Vols introduir un altre producte?");
            if (!willContinue) {
                break;
            }
            
            System.out.println();
        }
    }
}