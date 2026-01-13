import java.awt.Color;
import java.awt.Graphics;

/**
 * Repräsentation eines stehenden Besens an der Hauswand.
 * <p>
 * Der Besen verwendet eine Position (posX,posY) als Referenzpunkt und eine
 * Größe (besenGroesse) als Basismaß für die Dimensionen des Besens. Die
 * Zeichenoperationen gehen davon aus, dass `posY` die Mitte des Besens
 * vertikal darstellt.
 * </p>
 * <p>
 * Hinweise zum Layout:
 * - Der Besen wird als Polyfon für den Stiel und als gefülltes Oval für die
 * Borste gezeichnet.
 * - Die Größe des Besens beeinflusst sowohl die Länge des Stiels als auch
 * die Größe der Borste.
 * </p>
 */
public class BesenStehend {
    private int posX; // x-Koordinate des Besens
    private int posY; // y-Koordinate der Mitte des Besens
    private int besenGroesse; // Basisgröße des Besens
    private Color besenStielFarbe; // Farbe des Besensstiels
    private Color besenBorstenFarbe; // Farbe der Besenborsten

    /**
     * Erzeugt einen neuen fliegenden Besen mit Standartfarbe.
     * 
     * @param besenGroesse Basisgröße des Besens (bestimmt Stiellänge und
     *                     Borstengröße)
     * @param posX         x-Koordinate des Besens
     * @param posY         y-Koordinate der Mitte des Besens
     */

    public BesenStehend(int besenGroesse, int posX, int posY) {
        this.besenGroesse = besenGroesse;
        this.posX = posX;
        this.posY = posY;
        this.besenStielFarbe = new Color(102, 0, 153); // Standardfarbe Lila für fancy Besen
        this.besenBorstenFarbe = new Color(229, 190, 1); // Standardfarbe gelb für die Borsten
    }

    /**
     * Zeichnet den fliegenden Besen auf dem übergebenen Graphics-Kontext.
     * 
     * Implementierung:
     * - Stiel: gefülltes Polygon, das die Farbe des Stiel verwendet.
     * - Borsten: gefülltes Oval, das die Farbe der Borsten verwendet.
     * 
     * @param g Graphics-Kontext, auf dem gezeichnet wird
     */

    public void draw(Graphics g) {
        // Stamm berechnen und zeichnen
        int stielBreite = besenGroesse / 4; // Stamm proportional zur Größe
        int stielLaenge = besenGroesse * 2; // Höhe des Stiels
        int stielX = posX - stielBreite / 2; // x so wählen, dass Stiel zentriert ist
        int stielY = posY; // y ist Oberkante des Stiels

        g.setColor(besenStielFarbe);
        g.fillRect(stielX, stielY, stielBreite, stielLaenge);

        // Krone berechnen und zeichnen
        int borstenDurchmesser = (int) (besenGroesse * 1.5); // etwas größer als Basis
        int kroneX = posX - borstenDurchmesser / 2; // Krone zentrieren
        int kroneY = posY + stielLaenge - borstenDurchmesser / 1000; // Krone ragt zur Hälfte über den Stamm

        g.setColor(besenBorstenFarbe);
        int[] xPunkte = { kroneX, kroneX + borstenDurchmesser, kroneX + borstenDurchmesser / 2 };
        int[] yPunkte = { kroneY, kroneY, kroneY - borstenDurchmesser / 2 };
        g.fillPolygon(xPunkte, yPunkte, 3);

        // Stiel zeichnen
        /*
         * g.setColor(besenStielFarbe);
         * int stielLaenge = besenGroesse;
         * int stielBreite = besenGroesse / 6;
         * int[] xPunkte = { posX, posX + stielLaenge, posX + stielLaenge - stielBreite,
         * posX - stielBreite };
         * int[] yPunkte = { posY - stielBreite / 2, posY - stielBreite / 2, posY +
         * stielBreite / 2,
         * posY + stielBreite / 2 };
         * g.fillPolygon(xPunkte, yPunkte, 4);
         * // Borsten zeichnen
         * g.setColor(besenBorstenFarbe);
         * int borstenDurchmesser = besenGroesse / 2;
         * g.fillOval(posX - borstenDurchmesser / 2, posY, borstenDurchmesser,
         * borstenDurchmesser);
         */

    }
}
