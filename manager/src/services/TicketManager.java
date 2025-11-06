package services;

public class TicketManager {
    // * Ticket
    private String ticketCode;
    private double baseTotal;
    
    private static final int IVA = 10;

    // * Capçaleres
    public static final String SINGLE_TICKET = "                    TICKET";
    public static final String UPDATE_TICKET = "              TICKET ACTUALITZAT";
    public static final String LAST_TICKET = "                 ÚLTIM TICKET";

    // #region Tiquet
    /**
     * Crea un nou tiquet i elimina l'anterior.
     */
    public void newTicket() {
        ticketCode = "";
        baseTotal = 0;
    }

    public boolean existsTicket() {
        return ticketCode != null;
    }

    /**
     * <p>Retorna el tiquet.</p>
     * 
     * <strong>Valors retornats:</strong>
     * <ul>
     * <li>Línea de producte: nom | preu unitari | quantitat | subtotal.</li>
     * <li>Total sense IVA.<li>
     * <li>IVA (del {@value TicketManager#IVA}%).</li>
     * <li>Total amb IVA.</li>
     * 
     * @throws IllegalStateException Si no s'ha creat un tiquet prèviament.
     */
    public String getTicket(String head) {
        // * Validació inicial
        if (ticketCode == null) {
            throw new IllegalStateException("No hi ha ningún tiquet creat.");
        }

        // * Totals
        double ivaAmount = baseTotal * (IVA / 100.0);
        double total = baseTotal + ivaAmount;

        // * Construir tiquet
        StringBuilder sb = new StringBuilder();

        // Capçalera
        sb.append("================================================").append("\n");
        sb.append(head).append("\n");
        sb.append("================================================").append("\n\n");

        // Cap
        sb.append(String.format("%-10s %5s %10s %5s %10s %5s%n",
                "Producte", "Quant.", "Unitari", "", "Subtotal", ""));
        sb.append("------------------------------------------------").append("\n");

        // Línies
        sb.append(ticketCode);
        sb.append("------------------------------------------------").append("\n");

        // Totals
        sb.append(String.format("%-20s %21.2f EUR%n", "Total sense IVA:", baseTotal));

        String ivaString = "IVA (" + IVA + "%):";
        sb.append(String.format("%-20s %21.2f EUR%n", ivaString, ivaAmount));

        sb.append(String.format("%-20s %21.2f EUR%n", "Total sense IVA:", total));
        sb.append("================================================");

        return sb.toString();
    }

    /**
     * <p>Retorna el tiquet.</p>
     * 
     * <strong>Valors retornats:</strong>
     * <ul>
     * <li>Línea de producte: nom | preu unitari | quantitat | subtotal.</li>
     * <li>Total sense IVA.<li>
     * <li>IVA (del {@value TicketManager#IVA}%).</li>
     * <li>Total amb IVA.</li>
     * 
     * @throws IllegalStateException Si no s'ha creat un tiquet prèviament.
     */
    public String getTicket() {
        // * Validació inicial
        if (ticketCode == null) {
            throw new IllegalStateException("No hi ha ningún tiquet creat.");
        }

        // * Obtindre tiquet
        return getTicket(SINGLE_TICKET);
    }

    // #endregion

    // #region Privats
    /**
     * Afegeix una línea al tiquet.
     * 
     * @param name → Nom del producte.
     * @param price → Preu del producte.
     * @param quantity → Quantitat del producte.
     * 
     * @throws IllegalStateException Si no s'ha creat un tiquet prèviament.
     */
    public void addLine(String name, double price, int quantity) {
        // * Validació inicial
        if (ticketCode == null) {
            throw new IllegalStateException("No hi ha ningún tiquet creat.");
        }

        //  * Preparar línea
        double subTotal = Math.round(price * quantity * 100.0) / 100.0;

        String line = String.format("%-10s %5d %10.2f EUR %10.2f EUR%n",
                name, quantity, price, subTotal);

        // * Actualitzar tiquet
        ticketCode += line;
        baseTotal += subTotal;
    }

    // #endregion

}
