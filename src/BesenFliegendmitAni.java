import java.awt.Color;
import java.awt.Graphics;

/**
 * Repräsentation eines fliegenden Besens im Himmel.
 * <p>
 * Der Besen verwendet eine Position (posX,posY) als Referenzpunkt und eine
 * Größe (besenGroesse) als Basismaß für die Dimensionen des Besens. Die
 * Zeichenoperationen gehen davon aus, dass `posY` die Mitte des Besens
 * vertikal darstellt.
 * </p>
 * <p>
 * Hinweise zum Layout:
 * - Der Besen wird als Polygon für den Stiel und als gefülltes Oval für die
 * Borste gezeichnet.
 * - Die Größe des Besens beeinflusst sowohl die Länge des Stiels als auch
 * die Größe der Borste.
 * - Der Besen kann sich mit einer festgelegten Geschwindigkeit bewegen.
 * </p>
 */
public class BesenFliegendmitAni {
    /** x-Koordinate des Besens */
    private int posX;

    /** y-Koordinate der Mitte des Besens */
    private int posY;

    /** Basisgröße des Besens */
    private int besenGroesse;

    /** Farbe des Besenstiels */
    private Color besenStielFarbe;

    /** Farbe der Besenborsten */
    private Color besenBorstenFarbe;

    /** Geschwindigkeit in x-Richtung (Pixel pro Frame) */
    private int geschwindigkeitX;

    /** Geschwindigkeit in y-Richtung (Pixel pro Frame) */
    private int geschwindigkeitY;

    /** Maximale Bildschirmbreite für Wrap-Around */
    private int maxBreite;

    /** Maximale Bildschirmhöhe für Wrap-Around */
    private int maxHoehe;

    /** Start-Y-Position für Wellenbewegung */
    private int startY;

    /** Zähler für Wellenbewegung */
    private int bewegungsZaehler = 0;

    /**
     * Erzeugt einen neuen fliegenden Besen mit Standardfarbe.
     * 
     * @param besenGroesse Basisgröße des Besens (bestimmt Stiellänge und
     *                     Borstengröße)
     * @param posX         x-Koordinate des Besens
     * @param posY         y-Koordinate der Mitte des Besens
     */
    public BesenFliegendmitAni(int besenGroesse, int posX, int posY) {
        this.besenGroesse = besenGroesse;
        this.posX = posX;
        this.posY = posY;
        this.besenStielFarbe = new Color(102, 0, 153); // Standardfarbe Lila für fancy Besen
        this.besenBorstenFarbe = new Color(229, 190, 1); // Standardfarbe gelb für die Borsten
        // Standardgeschwindigkeit: langsam nach rechts
        this.geschwindigkeitX = 2;
        this.geschwindigkeitY = 1;

        // Standardbildschirmgröße (kann später gesetzt werden)
        this.maxBreite = 1110;
        this.maxHoehe = 670;
    }

    /**
     * Setzt die Bewegungsgeschwindigkeit des Besens.
     * 
     * @param geschwindigkeitX Geschwindigkeit in x-Richtung (positiv = rechts,
     *                         negativ = links)
     * @param geschwindigkeitY Geschwindigkeit in y-Richtung (positiv = runter,
     *                         negativ = hoch)
     */
    public void setGeschwindigkeit(int geschwindigkeitX, int geschwindigkeitY) {
        this.geschwindigkeitX = geschwindigkeitX;
        this.geschwindigkeitY = geschwindigkeitY;
    }

    /**
     * Setzt die maximalen Bildschirmgrenzen für Wrap-Around.
     * 
     * @param maxBreite maximale Breite des Bildschirms
     * @param maxHoehe  maximale Höhe des Bildschirms
     */
    public void setBildschirmGroesse(int maxBreite, int maxHoehe) {
        this.maxBreite = maxBreite;
        this.maxHoehe = maxHoehe;
    }

    /**
     * Bewegt den Besen basierend auf seiner Geschwindigkeit.
     * <p>
     * Wenn der Besen den Bildschirmrand erreicht, erscheint er
     * auf der gegenüberliegenden Seite wieder (Wrap-Around).
     * </p>
     */
    public void bewegen() {
        // Position aktualisieren
        posX += geschwindigkeitX;
        posY += geschwindigkeitY;

        // Wrap-Around: Wenn Besen rechts rausfliegt, links wieder reinkommen
        if (posX > maxBreite + besenGroesse) {
            posX = -besenGroesse;
        }
        // Wenn Besen links rausfliegt, rechts wieder reinkommen
        if (posX < -besenGroesse) {
            posX = maxBreite + besenGroesse;
        }

        // Wrap-Around für Y-Achse (falls Besen hoch/runter fliegt)
        if (posY > maxHoehe) {
            posY = 0;
        }
        if (posY < 0) {
            posY = maxHoehe;
        }
    }

    /**
     * Bewegt den Besen in Wellenform (sinusförmige Bewegung).
     * <p>
     * Der Besen fliegt horizontal und bewegt sich gleichzeitig
     * vertikal in einer Wellenbewegung.
     * </p>
     * 
     * @param amplitude Höhe der Welle
     * @param frequenz  Häufigkeit der Welle
     */

    /**
     * Setzt die Start-Y-Position für Wellenbewegung.
     * 
     * @param startY ursprüngliche Y-Position
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * Bewegt den Besen in einer Wellenbewegung.
     * 
     * @param amplitude Höhe der Welle (z.B. 30 Pixel)
     * @param frequenz  Geschwindigkeit der Welle (z.B. 0.05)
     */
    public void bewegenWelle(double amplitude, double frequenz) {
        // Horizontal bewegen
        posX += geschwindigkeitX;

        // Vertikale Wellenbewegung
        bewegungsZaehler++;
        posY = startY + (int) (amplitude * Math.sin(bewegungsZaehler * frequenz));

        // Wrap-Around für X-Achse
        if (posX > maxBreite + besenGroesse) {
            posX = -besenGroesse;
        }
        if (posX < -besenGroesse) {
            posX = maxBreite + besenGroesse;
        }
    }

    /**
     * Gibt die aktuelle X-Position zurück.
     * 
     * @return x-Koordinate
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Gibt die aktuelle Y-Position zurück.
     * 
     * @return y-Koordinate
     */
    public int getPosY() {
        return posY;
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
        // Stamm berechnen und zeichnen (waagerecht)
        int stielLaenge = besenGroesse * 2; // Länge nach rechts
        int stielBreite = besenGroesse / 4; // Dicke

        int stielX = posX; // linke Kante des Stiels
        int stielY = posY - stielBreite / 2; // so, dass posY die Mitte ist

        g.setColor(besenStielFarbe);
        g.fillRect(stielX, stielY, stielLaenge, stielBreite);

        // Krone/Borsten rechts am Stiel
        int borstenDurchmesser = (int) (besenGroesse * 1.5);

        // Start der Borsten am rechten Ende des Stiels
        int kroneX = stielX + stielLaenge - 160; // direkt am Stielende
        int kroneY = posY; // mittig auf der Stielhöhe

        g.setColor(besenBorstenFarbe);

        // Dreieck, das nach rechts zeigt
        int[] xPunkte = {
                kroneX, // links (am Stiel)
                kroneX, // links unten/oben
                kroneX + borstenDurchmesser // Spitze rechts
        };
        int[] yPunkte = {
                kroneY - borstenDurchmesser / 2, // oben
                kroneY + borstenDurchmesser / 2, // unten
                kroneY // Mitte (Spitze)
        };

        g.fillPolygon(xPunkte, yPunkte, 3);
    }
}