import services.TicketManager;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private TicketManager manager;

    public void run() {
        manager = new TicketManager();
        manager.newTicket();

        manager.addLine("Aigua", 2.50, 2);
        manager.addLine("Pa", 1.60, 2);

        String ticket = manager.getTicket();
        System.out.println(ticket);

    }
}