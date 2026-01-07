import java.awt.Color;
import java.awt.Graphics;

/**
 * Einfaches Haus mit Dach, Wänden und zwei Fenstern.
 * <p>
 * Das Haus verwendet als Referenzpunkt die Bodenlinie `y` (also ist `y` die
 * Position des Bodens). Die Höhe (`höhe`) umfasst Dach und Wand; die Dachhöhe
 * wird intern als ein Viertel der Gesamtgröße berechnet.
 * </p>
 */
public class Haus {
	private int x;             // linke x-Koordinate des Hauses
	private int y;             // y-Koordinate der Bodenlinie (unten)
	private int breite;        // Gesamtbreite des Hauses
	private int höhe;          // Gesamtgröße (Dach + Wand)
	Color wandFarbe;   // Farbe der Hauswände
	private boolean lichtAn = false; // interner Zustand: sind die Fenster beleuchtet?

	/**
	 * Konstruktor: legt Position, Größe und Wandfarbe fest.
	 *
	 * @param x         linke x-Koordinate
	 * @param y         Bodenlinie (y-Koordinate)
	 * @param breite    Breite des Hauses
	 * @param höhe      Gesamtgröße (Dach + Wand)
	 * @param wandFarbe Farbe der Hauswände
	 */
	public Haus(int x, int y, int breite, int höhe, Color wandFarbe) {
		this.x = x;
		this.y = y;
		this.breite = breite;
		this.höhe = höhe;
		this.wandFarbe = wandFarbe;
	}

	/**
	 * Schaltet das Licht (Fenster) um, falls die angegebenen Koordinaten innerhalb
	 * der Wandfläche liegen.
	 *
	 * @param x Abfrage-x (z. B. Maus-Klick x)
	 * @param y Abfrage-y (z. B. Maus-Klick y)
	 * @return true wenn der Klick das Haus getroffen und damit der Zustand geändert wurde
	 */
	public boolean lichtUmschalter(int x, int y) {
		int dachHöhe = this.höhe / 4;
		int wandHöhe = this.höhe - dachHöhe;
		int wandY = this.y - wandHöhe; // Oberkante der Wand (da y die Bodenlinie ist)

		if (x >= this.x && x <= this.x + this.breite &&
			y >= wandY && y <= wandY + wandHöhe) {
			lichtAn = !lichtAn; // Zustand invertieren
			return true;
		}
		return false;
	}

	/**
	 * Zeichnet das Haus (Dach, Wand und Fenster) auf dem übergebenen Graphics.
	 *
	 * Fensterfarbe hängt vom Zustand `lichtAn` ab: gelb = an, schwarz = aus.
	 *
	 * @param g Graphics-Kontext
	 */
	public void zeichnen(Graphics g) {
		// Höhen für Dach und Wand berechnen
		int dachHöhe = this.höhe / 4;      // Dachhöhe
		int wandHöhe = this.höhe - dachHöhe; // Wandhöhe

		int wandY = this.y - wandHöhe;    // Oberkante der Wand
		int dachY = wandY - dachHöhe;     // Oberkante des Dachs

		// Dach zeichnen (dunkelgrau)
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.x, dachY, this.breite, dachHöhe);

		// Wand zeichnen
		g.setColor(this.wandFarbe);
		g.fillRect(this.x, wandY, this.breite, wandHöhe);

		// Fensterfarbe je nach Lichtzustand
		Color fensterFarbe = this.lichtAn ? Color.YELLOW : Color.BLACK;
		g.setColor(fensterFarbe);

		// Berechnung Fenstergrößen und -positionen
		int fensterBreite = this.breite / 5;
		int fensterHöhe = wandHöhe / 5;
		int fensterY = wandY + wandHöhe / 3;
		int linkesFensterX = this.x + this.breite / 5;
		int rechtesFensterX = this.x + (3 * this.breite) / 5;

		// Fenster zeichnen
		g.fillRect(linkesFensterX, fensterY, fensterBreite, fensterHöhe);
		g.fillRect(rechtesFensterX, fensterY, fensterBreite, fensterHöhe);
	}
}