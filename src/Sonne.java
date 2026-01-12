import java.awt.Color;
import java.awt.Graphics;

/**
 * Repräsentiert die Sonne (oder bei ausgeschaltetem Licht den Mond)
 * in der Szene. Zusätzlich steuert diese Klasse den Tag-/Nacht-Zustand.
 */
public class Sonne {

	// Position und Größe der Sonne (oder des Mondes)
	private int hoehe;
	private int breite;
	private int posX;
	private int posY;

	/**
	 * Interner Zustand: true = Licht aus (Nacht), false = Licht an (Tag).
	 * Der Name ist bewusst invers formuliert: "sonneLichtAnAus" bedeutet,
	 * dass die Sonne ausgeblendet ist, wenn der Wert true ist.
	 */
	private boolean sonneLichtAnAus;

	// Standardfarbe der Sonne (Tag)
	private Color sonnenFarbe;

	/**
	 * Konstruktor.
	 *
	 * @param posX  x-Koordinate der oberen linken Ecke der Sonne
	 * @param posY  y-Koordinate der oberen linken Ecke der Sonne
	 * @param hoehe Höhe der Sonne
	 * @param breite Breite der Sonne
	 */
	public Sonne(int posX, int posY, int hoehe, int breite) {
		this.hoehe = hoehe;
		this.breite = breite;
		this.posX = posX;
		this.posY = posY;
		this.sonnenFarbe = new Color(243, 159, 24);
	}

	/**
	 * Gibt zurück, ob gerade Nacht ist.
	 *
	 * @return true, wenn Nacht (Sonne ausgeblendet)
	 */
	public boolean istNacht() {
		return sonneLichtAnAus;
	}

	/**
	 * Gibt zurück, ob gerade Tag ist.
	 *
	 * @return true, wenn Tag (Sonne sichtbar)
	 */
	public boolean istTag() {
		return !sonneLichtAnAus;
	}

	/**
	 * Schaltet den Tag/Nacht-Zustand um, wenn die übergebenen Koordinaten
	 * innerhalb des Sonnenbereichs liegen.
	 *
	 * Hinweis: Die ursprüngliche Implementierung prüfte ein umgebendes
	 * Rechteck. Diese Klasse bietet nun eine kreisförmige Trefferprüfung
	 * (mittels `containsPoint`) und eine explizite `toggle()`-Methode, so
	 * dass Aufrufer zunächst den aktuellen Zustand über die Getter
	 * (`istTag`/`istNacht`) abfragen können und anschließend indirekt
	 * das Umschalten steuern.
	 *
	 * @param posX x-Koordinate (z. B. Maus-Klick)
	 * @param posY y-Koordinate (z. B. Maus-Klick)
	 * @return true, wenn der Klick innerhalb des Sonnenbereichs lag
	 */
	public boolean lichtUmschalterSonne(int posX, int posY) {
		return containsPoint(posX, posY);
	}

	/**
	 * Prüft, ob die gegebene Punktkoordinate innerhalb des kreisförmigen
	 * Sonnenbereichs liegt.
	 *
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 * @return true, wenn Punkt im Kreis liegt
	 */
	public boolean containsPoint(int x, int y) {
		int centerX = this.posX + this.breite / 2;
		int centerY = this.posY + this.hoehe / 2;
		int radius = Math.min(this.breite, this.hoehe) / 2;
		int dx = x - centerX;
		int dy = y - centerY;
		return dx * dx + dy * dy <= radius * radius;
	}

	/**
	 * Schaltet den internen Tag/Nacht-Zustand um (toggle).
	 */
	public void toggle() {
		this.sonneLichtAnAus = !this.sonneLichtAnAus;
	}

	/**
	 * Zeichnet die Sonne (oder den Mond) auf dem Graphics-Kontext. Die Farbe
	 * wechselt je nach Tag-/Nacht-Zustand.
	 *
	 * @param g Graphics-Kontext
	 */
	public void draw(Graphics g) {
		// Farbe wählen: bei Nacht zeichnen wir einen hellen Kreis (Mond),
		// bei Tag die orangefarbene Sonne.
		if (sonneLichtAnAus) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(sonnenFarbe);
		}
		g.fillOval(posX, posY, breite, hoehe);
	}
}

