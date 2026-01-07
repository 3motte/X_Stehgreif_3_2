import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/** 
 * Basis-Panel stellt Grundfunktionen fuer den Aufbau interaktiver Anwendungen zur
 * Verfuegung.
 *  
 * Alle Mausereignisse koennen in einzelnen Methoden verarbeitet werden. 
 *  
 * @author Joerg Berdux
 * @version 1.0
 */
public class Hogsmeade extends JPanel implements MouseListener {
    
	/** Sammlung der Häuser, die in der Szene gezeichnet werden. */
	public Haus[] haeuser;
	/** Die Straße im Vordergrund (oder Mittelgrund) der Szene. */
	public Strasse strasse_1;
	/** Die Sonne; steuert außerdem Tag-/Nacht-Zustand. */
	public Sonne sonne_1;
	/** Drei exemplarische Bäume an unterschiedlichen Positionen. */
	public Baum baum_1;
	public Baum baum_2;
	public Baum baum_3;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Initialisierung des Panels und setzen des MouseListerns
	 * fuer die Verwendung von Maus-Ereignissen
	 */
	public Hogsmeade(){
		
		/* registriert Panel als MouseListener, so dass die jeweilige spezialisierte 
		 * Methode aufgerufen wird, wenn ein Mausereignis innerhalb des Panels ausgeloest 
		 * wird
		 */
		this.addMouseListener(this);
		
		// Initialisiere Häuser, Bäume, Sonne und Straße mit sinnvollen Startwerten
		
		haeuser = new Haus[5];
		haeuser[0] = new Haus(35,  575, 150,  240, new Color(123, 3, 35));     
		haeuser[1] = new Haus(240, 575, 170, 215, new Color(70, 130, 180));    
		haeuser[2] = new Haus(545, 575, 135,  185, new Color(60, 180, 115));   
		haeuser[3] = new Haus(700, 575, 150, 165, new Color(240, 230, 140));  
		haeuser[4] = new Haus(850, 575, 170,  175, new Color(219, 112, 147)); 
		
		// Straße: (posX, posY, höhe, breite)
		strasse_1 = new Strasse(0, 570, 100, 1110);
		// Sonne: (posX, posY, hoehe, breite) - dient auch als Schalter für Tag/Nacht
		sonne_1 = new Sonne(850, 80, 200, 200);

		// Bäume: (größe, posX, posY)
		baum_1 = new Baum(100, 180, 370);
		baum_2 = new Baum(80, 1000, 410);
		baum_3 = new Baum(110, 516, 350);
		
		
		
	}
	
	/** 
	 * Zeichnen der Strasse.
	 * 
	 * Umsetzung der Methode
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 * 
	 * @param g Graphik-Kontext, auf dem die Landschaft gezeichnet wird
	 */
	public void paint(Graphics g){
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
				
				
		//Zeichnet die Strasse ein
		strasse_1.draw(g);
		//Zeichnet die Sonne ein
		sonne_1.draw(g);
		
		//Zeichnet die Bäume ein	
		baum_1.draw(g);
		baum_2.draw(g);
		baum_3.draw(g);
	}
	
	
	/** 
	 * Aufloesung der x, y-Position, an der Mausbutton betaetigt wurde.
	 * 
	 * Umsetzung der Methode
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * 
	 * @param e Maus-Ereignis, das ausgeloest wurde 
	 */
	public void mouseClicked(MouseEvent e){
		int x, y;
		
		x = e.getX(); // x-Koordinate, an der Mausereignis stattgefunden hat
		y = e.getY(); // y-Koordinate, an der Mausereignis stattgefunden hat
		
		// Mausereignis verarbeiten: Klick auf Haus schaltet Licht, Klick auf Sonne schaltet Tag/Nacht
		for (Haus h : haeuser) {
		    if (h.lichtUmschalter(x, y)) break;  // wenn getroffen, fertig
		}
		
		// Klick auf Sonne toggelt Tag/Nacht; bei Änderung neu zeichnen
		if (sonne_1.lichtUmschalterSonne(x, y)) {
			repaint();
			return; // bereits neu gezeichnet
		}

		// Nach möglichen Änderungen (z. B. Fenster an/aus) Szene neu zeichnen
		repaint();
	}
	
	/** Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e){
		// diese Methode bleibt einfach leer
	}
	
	/** Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e){
		// diese Methode bleibt einfach leer
	}
	
	/** Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e){
		// diese Methode bleibt einfach leer
	}

	/** Faengt Mouse-Event ab, ohne ihn weiter zu verarbeiten
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e){
		// diese Methode bleibt einfach leer
	}
}