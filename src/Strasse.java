import java.awt.Color;
import java.awt.Graphics;

/**
 * Einfache Darstellung einer Straße als gefülltes Rechteck.
 *
 * Die Straße verwendet die oberen linken Koordinaten (`posX`, `posY`) und
 * die Größenangaben `hoehe` und `breite` zum Zeichnen.
 */
public class Strasse {

    // Position und Größe der Straße
    private int hoehe;
    private int breite;
    private int posX;
    private int posY;

    // Farbe der Straßenfläche
    private Color strassenFarbe;

    /**
     * Konstruktor.
     *
     * @param posX  x-Koordinate der linken oberen Ecke
     * @param posY  y-Koordinate der linken oberen Ecke
     * @param hoehe Höhe der Straße (vertikale Ausdehnung)
     * @param breite Breite der Straße (horizontale Ausdehnung)
     */
    public Strasse(int posX, int posY, int hoehe, int breite) {
        this.posX = posX;
        this.posY = posY;
        this.hoehe = hoehe;
        this.breite = breite;
        this.strassenFarbe = new Color(128, 128, 128);
    }

    /**
     * Liefert die aktuelle Farbe der Straße. Kann verwendet werden, um
     * bei Bedarf das Aussehen zur Laufzeit abzufragen.
     *
     * @return die Farbe der Straße
     */
    public Color getFarbe() {
        return strassenFarbe;
    }

    /**
     * Zeichnet die Straßenfläche als gefülltes Rechteck.
     *
     * @param g Graphics-Kontext
     */
    public void draw(Graphics g) {
        g.setColor(strassenFarbe);
        g.fillRect(posX, posY, breite, hoehe);
    }
}