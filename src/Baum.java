
import java.awt.Color;
import java.awt.Graphics;

	/**
	 * Repräsentation eines einfachen Baums, bestehend aus Stamm und Krone.
	 * <p>
	 * Der Baum verwendet eine Position (posX,posY) als Referenzpunkt und eine
	 * Größe (baumGroesse) als Basismaß für Stamm- und Kronendimensionen. Die
	 * Zeichenoperationen gehen davon aus, dass `posY` die Oberkante des Stamms
	 * (also die Stelle, an der Stamm und Krone zusammentreffen) darstellt.
	 * </p>
	 * <p>
	 * Hinweise zum Layout:
	 * - Der Stamm ist proportional zur Gesamthöhe des Baums.
	 * - Die Krone wird als gefülltes Oval gezeichnet und zentriert über dem Stamm.
	 * </p>
	 */
	public class Baum {

		// Attribute / Eigenschaften des Baums
		private int posX;             // x-Koordinate des Baumzentrums
		private int posY;             // y-Koordinate: Oberkante des Stamms
		private int baumGroesse;      // Basisgröße, von der Stamm/krone abgeleitet werden
		private Color baumStammFarbe; // Farbe des Stamms
		private Color baumKroneFarbe; // Farbe der Krone

		/**
		 * Erzeugt einen neuen Baum mit Standardfarben für Stamm und Krone.
		 *
		 * @param baumGroesse Basisgröße des Baums (bestimmt Stammhöhe und Kronendurchmesser)
		 * @param posX        x-Koordinate des Baumzentrums
		 * @param posY        y-Koordinate der Oberkante des Stamms
		 */
		public Baum(int baumGroesse, int posX, int posY) {
			this.baumGroesse = baumGroesse;
			this.posX = posX;
			this.posY = posY;
			this.baumKroneFarbe = new Color(45, 87, 44);
			this.baumStammFarbe = new Color(80, 60, 60);
		}

		/**
		 * Zeichnet den Baum auf dem übergebenen Graphics-Kontext.
		 *
		 * Implementierung:
		 * - Stamm: gefülltes Rechteck, zentriert am `posX`, beginnt bei `posY`.
		 * - Krone: gefülltes Oval, zentriert über dem Stamm.
		 *
		 * @param g Graphics-Kontext, auf dem gezeichnet wird
		 */
		public void draw(Graphics g) {
			// Stamm berechnen und zeichnen
			int stammBreite = baumGroesse / 4;       // Stamm proportional zur Größe
			int stammHoehe = baumGroesse * 2;        // Höhe des Stamms
			int stammX = posX - stammBreite / 2;     // x so wählen, dass Stamm zentriert ist
			int stammY = posY;                       // y ist Oberkante des Stamms

			g.setColor(baumStammFarbe);
			g.fillRect(stammX, stammY, stammBreite, stammHoehe);

			// Krone berechnen und zeichnen
			int kroneDurchmesser = (int) (baumGroesse * 1.2); // etwas größer als Basis
			int kroneX = posX - kroneDurchmesser / 2;        // Krone zentrieren
			int kroneY = posY - kroneDurchmesser / 2;        // Krone ragt zur Hälfte über den Stamm

			g.setColor(baumKroneFarbe);
			g.fillOval(kroneX, kroneY, kroneDurchmesser, kroneDurchmesser);
		}
	}