package services;

public class TicketManager {
    // * Ticket
    private String clientName;
    private String ticketCode;
    private double baseTotal;

    // #region Regles
    private static final int IVA = 10;

    // * Capçaleres
    public static final String SINGLE_TICKET = "TICKET";
    public static final String UPDATE_TICKET = "TICKET ACTUALITZAT";
    public static final String LAST_TICKET = "ÚLTIM TICKET";

    // * Longituds
    public static final int MAX_PRODUCT_NAME_LEN = 15;
    private static final int TICKET_WIDTH = 60;

    // * Tabs
    private static final int TAB = 8; // ? Amplada d'una tabulació (nombre de caràcters que avança cada '\t')
    private static final int STOP_QTY = 16; // ? Columna on ha d'alinear-se el camp de quantitat (Quant.)
    private static final int STOP_UNIT = 32; // ? Columna on ha d'alinear-se el camp del preu unitari (Unitari)
    private static final int STOP_SUB = 48; // ? Columna on ha d'alinear-se el camp del subtotal (Subtotal)

    // #endregion

    // #region Helpers de tab
    /**
     * Calcula la pròxima posició de tabulació a partir de la columna actual.
     * 
     * @param col Columna actual (longitud del text escrit fins ara).
     * @return La columna a la qual saltarà el cursor després d’un '\t'.
     */
    private int nextTabPos(int col) {
        // * Declaració de variables
        int mod = col % TAB; // ? Residu del nombre de caràcters respecte la mida d'una tabulació (TAB)
        int result;

        if (mod == 0) { // ? Es necessari un tab?
            result = col + TAB;
        } else {
            result = col + (TAB - mod); // ? Avançar tant com sigui necessari
        }

        return result;
    }

    /**
     * Insereix \t fins a arribar (o sobrepassar) la parada objectiu.
     * Si ja s’ha sobrepassat, insereix almenys un \t com a separador.
     */
    private int tabTo(StringBuilder sb, int col, int target) {
        if (col >= target) { // ? Separador mínim (overflowing)
            sb.append('\t');
            return nextTabPos(col);
        }
        
        // * Completar
        while (col < target) {
            sb.append('\t');
            col = nextTabPos(col);
        }
        return col;
    }

    // #endregion

    // #region Formatters
    /**
     * Dona format a la línea de la comanda (amb tabulacions).
     */
    private String formatLine(String name, int quantity, double price, double subtotal) {
        // * Declaració de variables
        StringBuilder sb = new StringBuilder();
        int col = 0;

        // * Producte
        sb.append(name);
        col += name.length();
        col = tabTo(sb, col, STOP_QTY);

        // * Quantitat
        String q = String.valueOf(quantity);
        sb.append(q);
        col += q.length();
        col = tabTo(sb, col, STOP_UNIT);

        // * Preu unitari
        String p = String.format("%.2f EUR", price);
        sb.append(p);
        col += p.length();
        tabTo(sb, col, STOP_SUB);

        // * Subtotal
        String s = String.format("%.2f EUR", subtotal);
        sb.append(s);

        return sb.toString();
    }

    /**
     * Dona format a la carcelera (amb tabulacions).
     */
    private String formatHeader() {
        StringBuilder sb = new StringBuilder();
        int col = 0;

        // * Producte
        String h1 = "Producte";
        sb.append(h1);
        col += h1.length();
        col = tabTo(sb, col, STOP_QTY);

        // * Quantitat
        String h2 = "Quant.";
        sb.append(h2);
        col += h2.length();
        col = tabTo(sb, col, STOP_UNIT);

        // * Preu unitari
        String h3 = "Unitari";
        sb.append(h3);
        col += h3.length();
        tabTo(sb, col, STOP_SUB);

        // * Subtotal
        sb.append("Subtotal");
        return sb.toString();
    }

    /**
     * Dona format a les línies dels totals (amb tabulacions).
     */
    private String formatPrice(String label, double price) {
        StringBuilder line = new StringBuilder(label);

        // * Montar tabulacions
        int col = label.length();
        tabTo(line, col, STOP_SUB);

        // * Formar línea
        line.append(String.format("%.2f EUR", price));
        return line.toString() + "\n";
    }

    private String lineOf(char c, int length) {
        return Character.toString(c).repeat(length);
    }

    /**
     * Dona format al títol del tiquet.
     */
    private String titleOf(String label) {
        int space;

        // * Obtindre longitud inicial
        space = TICKET_WIDTH - label.length();
        space = Math.floorDiv(space, 2);
        
        return lineOf(' ', space) + label;
    }

    // #endregion

    // #region Tiquet
    /**
     * Crea un nou tiquet i elimina l'anterior.
     */
    public void newTicket(String clientName) {
        this.clientName = clientName;
        ticketCode = "";
        baseTotal = 0;
    }

    public boolean existsTicket() {
        return !(ticketCode == null || ticketCode.isEmpty());
    }

    /**
     * <p>
     * Retorna el tiquet.
     * </p>
     * 
     * <strong>Valors retornats:</strong>
     * <ul>
     * <li>Línea de producte: nom | preu unitari | quantitat | subtotal.</li>
     * <li>Total sense IVA.
     * <li>
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

        // * Calcular iva
        double ivaAmount = baseTotal * (IVA / 100.0);
        double total = baseTotal + ivaAmount;

        // * Obtindre separadors
        String separator = lineOf('-', TICKET_WIDTH);
        String strongSeparator = lineOf('=', TICKET_WIDTH);

        // * Construir tiquet
        StringBuilder sb = new StringBuilder();

        // Cap
        sb.append(strongSeparator).append("\n");
        sb.append(titleOf(head)).append("\n");
        sb.append(strongSeparator).append("\n\n");

        // Client
        sb.append("Client: " + clientName + "\n\n");

        // Productes
        sb.append(ticketCode);
        sb.append(separator).append("\n");

        // Totals
        sb.append(formatPrice("Total sense IVA:", baseTotal));
        sb.append(formatPrice("IVA (" + IVA + "%):", ivaAmount));
        sb.append(formatPrice("Total amb IVA:", total));
        sb.append(strongSeparator);

        return sb.toString();
    }

    /**
     * <p>
     * Retorna el tiquet.
     * </p>
     * 
     * <strong>Valors retornats:</strong>
     * <ul>
     * <li>Línea de producte: nom | preu unitari | quantitat | subtotal.</li>
     * <li>Total sense IVA.
     * <li>
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

    /**
     * Afegeix una línea al tiquet.
     * 
     * @param name     → Nom del producte.
     * @param price    → Preu del producte.
     * @param quantity → Quantitat del producte.
     * 
     * @throws IllegalStateException Si no s'ha creat un tiquet prèviament.
     */
    public void addLine(String name, double price, int quantity) {
        // * Validació inicial
        if (ticketCode == null) {
            throw new IllegalStateException("No hi ha ningún tiquet creat.");
        }

        // * Crear tiquet
        if (ticketCode.isEmpty()) {
            ticketCode += formatHeader() + "\n";
            ticketCode += lineOf('-', TICKET_WIDTH) + "\n";
        }

        double subTotal = Math.round(price * quantity * 100.0) / 100.0;

        // * Actualizar tiquet
        ticketCode += formatLine(name, quantity, price, subTotal) + "\n";
        baseTotal += subTotal;
    }

    // #endregion
}
