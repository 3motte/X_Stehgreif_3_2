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
	private int x; // linke x-Koordinate des Hauses
	private int y; // y-Koordinate der Bodenlinie (unten)
	private int breite; // Gesamtbreite des Hauses
	private int höhe; // Gesamtgröße (Dach + Wand)
	Color wandFarbe; // Farbe der Hauswände
	private boolean lichtAn = false; // interner Zustand: sind die Fenster beleuchtet?

	// Konstanten für Fensterberechnung
	private static final int MIN_FENSTER_BREITE = 20; // minimale Fensterbreite
	private static final int FENSTER_ABSTAND = 15; // Abstand zwischen den Fenstern

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
	 * der Wand- oder Dachfläche liegen.
	 *
	 * @param x Abfrage-x (z. B. Maus-Klick x)
	 * @param y Abfrage-y (z. B. Maus-Klick y)
	 * @return true wenn der Klick das Haus getroffen und damit der Zustand geändert
	 *         wurde
	 */
	public boolean lichtUmschalter(int x, int y) {
		int dachHöhe = this.höhe / 4;
		int wandHöhe = this.höhe - dachHöhe;
		int wandY = this.y - wandHöhe; // Oberkante der Wand (da y die Bodenlinie ist)

		if (containsPoint(x, y)) {
			lichtAn = !lichtAn;
			return true;
		}
		return false;
	}

	/**
	 * Prüft, ob ein Punkt das Haus trifft (Wand-Rechteck oder Dach-Dreieck).
	 *
	 * @param px x-Koordinate des Punkts
	 * @param py y-Koordinate des Punkts
	 * @return true, wenn Punkt Haus trifft
	 */
	private boolean containsPoint(int px, int py) {
		int dachHöhe = this.höhe / 4;
		int wandHöhe = this.höhe - dachHöhe;
		int wandY = this.y - wandHöhe; // Oberkante der Wand
		int dachY = wandY - dachHöhe; // Oberkante des Dachs (Dachspitze-Basis)

		// 1) Wand (rechteckig)
		if (px >= this.x && px <= this.x + this.breite && py >= wandY && py <= this.y) {
			return true;
		}

		// 2) Dach (rechteckig, passt zur Darstellung in zeichnen())
		if (py >= dachY && py < dachY + dachHöhe) {
			if (px >= this.x && px <= this.x + this.breite) return true;
		}

		return false;
	}

	/**
	 * Berechnet die optimale Anzahl von Fenstern basierend auf der Hausbreite.
	 * 
	 * @return Anzahl der Fenster
	 */
	private int berechneFenster() {
		// Formel: (Breite - 2*Rand) / (Fensterbreite + Abstand)
		int verfügbareBreite = this.breite - (2 * FENSTER_ABSTAND);
		int fensterMitAbstand = MIN_FENSTER_BREITE + FENSTER_ABSTAND;
		int anzahl = verfügbareBreite / fensterMitAbstand;

		// Mind. 1 Fenster, maximal so viele, wie passen
		return Math.max(1, Math.min(anzahl, 10));
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
		int dachHöhe = this.höhe / 4; // Dachhöhe
		int wandHöhe = this.höhe - dachHöhe; // Wandhöhe

		int wandY = this.y - wandHöhe; // Oberkante der Wand
		int dachY = wandY - dachHöhe; // Oberkante des Dachs

		// Dach zeichnen (dunkelgrau)
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.x, dachY, this.breite, dachHöhe);

		// Wand zeichnen
		g.setColor(this.wandFarbe);
		g.fillRect(this.x, wandY, this.breite, wandHöhe);

		// Fenster algorithmisch zeichnen
		zeichneFenster(g, wandY, wandHöhe);
	}

	/**
	 * Zeichnet die Fenster algrotithmisch basierend auf der Hausgröße.
	 * 
	 * @param g        Graphics-Kontext
	 * @param wandY    y-Koordinate der Oberkante der Wand
	 * @param wandHöhe Höhe der Wand
	 */
	private void zeichneFenster(Graphics g, int wandY, int wandHöhe) {
		// Fensterfarbe je nach Lichtzustand
		Color fensterFarbe = this.lichtAn ? Color.YELLOW : Color.BLACK;
		g.setColor(fensterFarbe);

		// Anzahl der Fenster berechnen
		int anzahlFenster = berechneFenster();

		// Fenstergrößen berechnen
		int fensterBreite = Math.max(MIN_FENSTER_BREITE, this.breite / (anzahlFenster * 2 + 1));
		int fensterHöhe = Math.max(20, wandHöhe / 4);

		// vertikale Position (mittig an der Wand)
		int fensterY = wandY + (wandHöhe - fensterHöhe) / 2;

		// Gesamtbreite aller Fenster und Abstände
		int gesamtFensterBreite = anzahlFenster * fensterBreite;
		int gesamtAbstandBreite = (anzahlFenster + 1) * FENSTER_ABSTAND;
		int gesamtBreite = gesamtFensterBreite + gesamtAbstandBreite;

		// Startposition (zentriert)
		int startX = this.x + (this.breite - gesamtBreite) / 2 + FENSTER_ABSTAND;

		// Fenster zeichnen
		for (int i = 0; i < anzahlFenster; i++) {
			int fensterX = startX + i * (fensterBreite + FENSTER_ABSTAND);
			g.fillRect(fensterX, fensterY, fensterBreite, fensterHöhe);
		}
	}
}