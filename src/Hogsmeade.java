import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Basis-Panel stellt Grundfunktionen fuer den Aufbau interaktiver Anwendungen
 * zur Verfuegung.
 * 
 * Alle Mausereignisse koennen in einzelnen Methoden verarbeitet werden.
 * Die Besen werden animiert durch einen Timer.
 * 
 * @author Joerg Berdux
 * @version 1.1
 */
public class Hogsmeade extends JPanel implements MouseListener {

	/** Sammlung der Häuser, die in der Szene gezeichnet werden. */
	public Haus[] haeuser;

	/** Die Straße im Vordergrund (oder Mittelgrund) der Szene. */
	public Strasse strasse_1;

	/** Die Sonne; steuert außerdem Tag-/Nacht-Zustand. */
	public Sonne sonne_1;

	/** Sammlung der Bäume in der Szene. */
	public Baum[] baeume;

	/** Sammlung der stehenden Besen in der Szene. */
	public BesenStehend[] besenStehend;

	/** Sammlung der fliegenden Besen in der Szene. */
	public BesenFliegendmitAni[] besenFliegend;

	/** Timer für die Animation der fliegenden Besen */
	private Timer animationTimer;

	private static final long serialVersionUID = 1L;

	/**
	 * Initialisierung des Panels und setzen des MouseListerns
	 * fuer die Verwendung von Maus-Ereignissen sowie Starten der Animation.
	 */
	public Hogsmeade() {

		/*
		 * registriert Panel als MouseListener, so dass die jeweilige spezialisierte
		 * Methode aufgerufen wird, wenn ein Mausereignis innerhalb des Panels
		 * ausgeloest wird
		 */
		this.addMouseListener(this);

		// Initialisiere Häuser, Bäume, Sonne und Straße mit sinnvollen Startwerten

		// BesenStehend: (größe, posX, posY) - in Array speichern
		besenStehend = new BesenStehend[3];
		besenStehend[0] = new BesenStehend(50, 80, 470);
		besenStehend[1] = new BesenStehend(50, 330, 470);
		besenStehend[2] = new BesenStehend(50, 720, 470);

		haeuser = new Haus[5];
		haeuser[0] = new Haus(35, 575, 150, 240, new Color(123, 3, 35));
		haeuser[1] = new Haus(240, 575, 170, 215, new Color(70, 130, 180));
		haeuser[2] = new Haus(545, 575, 135, 185, new Color(60, 180, 115));
		haeuser[3] = new Haus(700, 575, 150, 165, new Color(240, 230, 140));
		haeuser[4] = new Haus(850, 575, 170, 175, new Color(219, 112, 147));

		// Straße: (posX, posY, höhe, breite)
		strasse_1 = new Strasse(0, 570, 100, 1110);
		// Sonne: (posX, posY, hoehe, breite) - dient auch als Schalter für Tag/Nacht
		sonne_1 = new Sonne(850, 80, 200, 200);

		// Bäume: (größe, posX, posY) - in Array speichern
		baeume = new Baum[3];
		baeume[0] = new Baum(100, 180, 370);
		baeume[1] = new Baum(80, 1000, 410);
		baeume[2] = new Baum(110, 516, 350);

		// BesenFliegend: (größe, posX, posY) - in Array speichern
		besenFliegend = new BesenFliegendmitAni[2];
		besenFliegend[0] = new BesenFliegendmitAni(50, 300, 200);
		besenFliegend[1] = new BesenFliegendmitAni(50, 600, 150);

		// Geschwindigkeiten für die Besen setzen
		besenFliegend[0].setGeschwindigkeit(3, 0); // Schneller, horizontal
		besenFliegend[1].setGeschwindigkeit(2, 0); // Langsamer, horizontal

		// Bildschirmgröße setzen
		besenFliegend[0].setBildschirmGroesse(1110, 670);
		besenFliegend[1].setBildschirmGroesse(1110, 670);

		// Optional: Wellenbewegung aktivieren
		besenFliegend[0].setStartY(200);
		besenFliegend[1].setStartY(150);

		// Timer für Animation starten (30 FPS = ca. 33ms pro Frame)
		animationTimer = new Timer(33, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				animiereBesen();
				repaint();
			}
		});
		animationTimer.start();
	}

	/**
	 * Animiert alle fliegenden Besen.
	 * <p>
	 * Diese Methode wird vom Timer regelmäßig aufgerufen und
	 * bewegt alle Besen entsprechend ihrer Geschwindigkeit.
	 * </p>
	 */
	private void animiereBesen() {
		if (besenFliegend != null) {
			for (BesenFliegendmitAni bf : besenFliegend) {
				if (bf != null) {
					// Option 1: Gerade Bewegung
					bf.bewegen();

					// Option 2: Wellenbewegung (auskommentiert)
					// bf.bewegenWelle(30, 0.05);
				}
			}
		}
	}

	/**
	 * Zeichnen der Strasse.
	 * 
	 * Umsetzung der Methode
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * 
	 * @param g Graphik-Kontext, auf dem die Landschaft gezeichnet wird
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// Himmel zeichnen (Tag/Nacht): hängt vom Zustand der Sonne ab
		if (sonne_1.istNacht()) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(new Color(50, 100, 200));
		}
		g.fillRect(0, 0, getWidth(), getHeight());

		// Objekte in der Szene zeichnen (Häuser, Straße, Sonne, Bäume)
		for (Haus h : haeuser) {
			h.zeichnen(g);
		}

		// Zeichnet die Strasse ein
		strasse_1.draw(g);
		// Zeichnet die Sonne ein
		sonne_1.draw(g);

		// Zeichnet die fliegenden Besen ein
		if (besenFliegend != null) {
			for (BesenFliegendmitAni bf : besenFliegend) {
				if (bf != null)
					bf.draw(g);
			}
		}

		// Zeichnet die stehenden Besen ein
		if (besenStehend != null) {
			for (BesenStehend bs : besenStehend) {
				if (bs != null)
					bs.draw(g);
			}
		}

		// Zeichnet die Bäume aus dem Array ein
		if (baeume != null) {
			for (Baum b : baeume) {
				if (b != null)
					b.draw(g);
			}
		}
	}

	/**
	 * Aufloesung der x, y-Position, an der Mausbutton betaetigt wurde.
	 * 
	 * Umsetzung der Methode
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * 
	 * @param e Maus-Ereignis, das ausgeloest wurde
	 */
	public void mouseClicked(MouseEvent e) {
		int x, y;

		x = e.getX(); // x-Koordinate, an der Mausereignis stattgefunden hat
		y = e.getY(); // y-Koordinate, an der Mausereignis stattgefunden hat

		// Mausereignis verarbeiten: Klick auf Haus schaltet Licht, Klick auf Sonne
		// schaltet Tag/Nacht
		for (Haus h : haeuser) {
			if (h.lichtUmschalter(x, y))
				break; // wenn getroffen, fertig
		}

		// Klick auf Sonne: nur zählen, wenn innerhalb des Kreisradius.
		// Zustand wird vorher über Getter abgefragt und dann indirekt umgeschaltet.
		if (sonne_1.containsPoint(x, y)) {
			sonne_1.toggle();
			repaint();
			return; // bereits neu gezeichnet
		}

		// Nach möglichen Änderungen (z. B. Fenster an/aus) Szene neu zeichnen
		repaint();
	}

	/**
	 * Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		// diese Methode bleibt einfach leer
	}

	/**
	 * Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// diese Methode bleibt einfach leer
	}

	/**
	 * Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		// diese Methode bleibt einfach leer
	}

	/**
	 * Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		// diese Methode bleibt einfach leer
	}
}