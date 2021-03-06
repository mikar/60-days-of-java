package grundkurs_java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Chap16 {

	public static void main(String[] args) {
		// Zeichnung.main(args);
		// PunkteVerbinden.main(args);
		// NewButtonFrame1.main(args);
		// NewButtonFrame2.main(args);
		// Punkt.main(args);
		DrehFrame.main(args);
	}
}

class SchwerDreieck extends Dreieck {
	// 16.6

	public SchwerDreieck(Punkt p, Punkt q, Punkt r) {
		super(p, q, r);
	}

	public void zeichnen(Graphics g, int xNull, int yNull) {
		double xs = (p.getX() + q.getX() + r.getX()) / 3;
		double ys = (p.getY() + q.getY() + r.getY()) / 3;
		super.zeichnen(g, xNull - (int) xs, yNull - (int) ys);
	};
}

class DrehFrame extends JFrame {
	// 16.5
	Container c;
	DrehPanel d;

	public DrehFrame() {
		c = getContentPane();
		d = new DrehPanel();
		c.add(d);
	}

	public static void main(String[] args) {
		DrehFrame fenster = new DrehFrame();
		fenster.setTitle("DrehFrame");
		fenster.setSize(350, 300);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class DrehPanel extends JPanel {
	// 16.4
	static final double SCHRITTWEITE = Math.PI / 60;
	private JButton linksButton, rechtsButton, streckeButton, dreieckButton;
	private GeoObjekt drehObject;

	public DrehPanel() {
		drehObject = erzeugeStrecke();

		setLayout(new FlowLayout());
		setOpaque(false);

		add(linksButton = new JButton("Links"));
		add(rechtsButton = new JButton("rechts"));
		add(streckeButton = new JButton("Strecke"));
		add(dreieckButton = new JButton("Dreieck"));

		linksButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drehObject.drehen(-SCHRITTWEITE);
				repaint();
			}
		}); // Ende der anonymen Klassendefinition

		rechtsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drehObject.drehen(SCHRITTWEITE);
				repaint();
			}
		});

		streckeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drehObject = erzeugeStrecke();
				repaint();
			}
		});

		dreieckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drehObject = erzeugeDreieck();
				repaint();
			}
		});
	}

	public GeoObjekt erzeugeStrecke() {
		return new Strecke(new Punkt(0, 0), new Punkt(100, 0));
	}

	public GeoObjekt erzeugeDreieck() {
		return new Dreieck(new Punkt(0, 0), new Punkt(100, 0), new Punkt(50,
				-66));

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drehObject.zeichnen(g, this.getWidth() / 2, this.getHeight() / 2);
	}
}

class Dreieck implements GeoObjekt {
	// 16.3
	protected Punkt p, q, r;
	private Strecke pq, qr, pr;

	public Dreieck(Punkt p, Punkt q, Punkt r) {
		this.p = p;
		this.q = q;
		this.r = r;
		pq = new Strecke(p, q);
		qr = new Strecke(q, r);
		pr = new Strecke(p, r);
	}

	public void drehen(double phi) {
		pq.drehen(phi);
		qr.drehen(phi);
		pr.drehen(phi);
	};

	public void zeichnen(Graphics g, int xNull, int yNull) {
		pq.zeichnen(g, xNull, yNull);
		qr.zeichnen(g, xNull, yNull);
		pr.zeichnen(g, xNull, yNull);
	};

}

class Strecke implements GeoObjekt {
	// 16.2
	private Punkt p, q;

	public Strecke(Punkt p, Punkt q) {
		this.p = p;
		this.q = q;
	}

	public void drehen(double phi) {
		p.drehen(phi);
		q.drehen(phi);
	}

	public void zeichnen(Graphics g, int xNull, int yNull) {
		g.drawLine(xNull + (int) this.p.getX(), yNull + (int) this.p.getY(),
				xNull + (int) this.q.getX(), yNull + (int) this.q.getY());
	}
}

interface GeoObjekt {
	// 16.2
	public void drehen(double phi);

	// dreht das Objekt um den Winkel phi
	public void zeichnen(Graphics g, int xNull, int yNull);
	// zeichnet das Objekt auf der Zeichenebene
	// xNull und yNull sind die Koordinaten des Ursprungs
	// (Nullpunkts) des verwendeten Koordinatensystems
}

class Punkt {
	// 16.1
	private double x, y;

	public Punkt(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void drehen(double phi) {
		double xAlt = x;
		x = x * Math.cos(phi) - y * Math.sin(phi);
		y = xAlt * Math.sin(phi) + y * Math.cos(phi);
	}

	public static void main(String[] args) {

	}
}

class NewButtonFrame2 extends JFrame {
	Container c;
	JButton b;

	public NewButtonFrame2() {
		c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT));
		b = new JButton("Drueck mich!");
		b.addActionListener(new ButtonBearbeiter());
		c.add(b);
	}

	class ButtonBearbeiter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.add(b = new JButton("noch einer"));
			b.revalidate();
		}
	}

	public static void main(String[] args) {
		JFrame fenster = new NewButtonFrame2();
		fenster.setTitle("Buttons hinzufuegen");
		fenster.setSize(500, 300);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class NewButtonFrame1 extends JFrame {
	// new buttons only show after resizing (no revalidation)
	Container c;
	JButton b;

	public NewButtonFrame1() {
		c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.LEFT));
		b = new JButton("Drueck mich!");
		b.addActionListener(new ButtonBearbeiter());
		c.add(b);
	}

	class ButtonBearbeiter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			c.add(new JButton("noch einer"));
			c.repaint();
		}
	}

	public static void main(String[] args) {
		JFrame fenster = new NewButtonFrame1();
		fenster.setTitle("Buttons hinzufuegen");
		fenster.setSize(500, 300);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class PunkteVerbinden extends JFrame {
	Container c;
	// Container dieses Frames
	Zeichenbrett z;

	// Zeichenbrett zum Linien Malen16.1 Zeichnen in Swing-Komponenten
	public PunkteVerbinden() { // Konstruktor
		c = getContentPane();
		// Container bestimmen
		z = new Zeichenbrett();
		// Zeichenbrett erzeugen
		c.add(z);
		// und dem Frame hinzufuegen
	}

	public static void main(String[] args) {
		PunkteVerbinden fenster = new PunkteVerbinden();
		fenster.setTitle("Punkte verbinden");
		fenster.setSize(250, 200);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class Zeichenbrett extends JPanel {
	private int[] x, y; // Koordinaten der Maus-Klicks
	private int n;

	// Anzahl Klicks
	public Zeichenbrett() {
		// Konstruktor
		n = 0;
		x = new int[1000];
		y = new int[1000];
		addMouseListener(new ClickBearbeiter());
	}

	public void paintComponent(Graphics g) {
		g.drawPolyline(x, y, n);
	}

	// Innere Listener-Klasse fuer Maus-Ereignisse
	class ClickBearbeiter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			x[n] = e.getX(); // speichere x-Koordinate
			y[n] = e.getY(); // speichere y-Koordinate
			n++;
			// erhoehe Anzahl Klicks
			repaint();
			// Neuzeichnen der Komponente beim
			// Repaint-Manager anfordern
		}
	}
}

class Zeichnung extends JFrame {
	Container c;
	// Container dieses Frames
	ZeichenPanel z;

	// Zeichnung auf dem Zeichen-Panel
	public Zeichnung() {
		// Konstruktor
		c = getContentPane();
		z = new ZeichenPanel(); // Erzeuge neue Zeichnung
		c.add(z);
		// und fuege sie dem Frame hinzu
	}

	public static void main(String[] args) { // main-Methode
		Zeichnung fenster = new Zeichnung();
		fenster.setTitle("Zeichnung");
		fenster.setSize(200, 200);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class ZeichenPanel extends JPanel {
	public void paintComponent(Graphics g) {
		g.drawLine(10, 10, 30, 20);
		int[] x = { 30, 40, 60, 70 };
		int[] y = { 5, 5, 30, 5 };
		g.drawPolyline(x, y, 4);
		g.drawRect(10, 50, 20, 10);
		x = new int[] { 130, 140, 160, 170 };
		y = new int[] { 5, 25, 30, 35 };
		g.drawPolygon(x, y, 4);
		g.drawOval(110, 60, 30, 15);
		g.drawArc(70, 40, 30, 20, 0, 110);
		g.drawString("Wow!", 40, 90);
		g.fillRect(10, 130, 20, 10);
		x = new int[] { 130, 140, 160, 170 };
		y = new int[] { 105, 135, 130, 155 };
		g.fillPolygon(x, y, 4);
		g.fillOval(60, 130, 30, 30);
		g.fillArc(150, 70, 40, 30, 0, -45);
	}
}