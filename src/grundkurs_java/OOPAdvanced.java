package grundkurs_java;

import java.util.Scanner;
import java.util.regex.Pattern;

import Prog1Tools.Plotter;

public class OOPAdvanced {

	public static void main(String[] args) {
		// Vater v = new Vater();
		// Vater s = new Sohn(); // but not Sohn s = new Vater();
		// v.zeigeVar(); // 1
		// ((Vater) s).zeigeVar(); // 2 (dynamic binding)
		// System.out.println("VATER: " + ((Vater) s).var); // 1 (no dyn
		// binding)
		// // note: polymorphism works for methods only.
		// Kind k = new Kind();
		// Vater vaeterchen = new Vater();
		// Sohn soehnchen = new Sohn();
		// System.out.println(vaeterchen instanceof Sohn);
		// System.out.println(vaeterchen instanceof Vater);
		// // System.out.println(vaeterchen instanceof Waehrung); //
		// incompatible
		// System.out.println(vaeterchen instanceof Object);
		// System.out.println(soehnchen instanceof Sohn);
		// System.out.println(soehnchen instanceof Vater);
		// // System.out.println(soehnchen instanceof Waehrung);
		// System.out.println(soehnchen instanceof Object); // a
		//
		// double d = 3.14;
		// Double D = new Double(3.14);
		// System.out.println(d + " " + D);
		// Lire l = new Lire(1000);
		// System.out.println(l);
		// // Note: You can prevent instantiation by making the constructor
		// // private (apart from defining it abstract, obviously).
		// // KreisPlot.main(args);
		// Aufzaehlung.main(args);
		// TestABC.main(args);
		// DSpiel.main(args);
		Sandwich.main(args);
		AutoTest.main(args);
		ElchTest.main(args);
		RunStrecke.main(args);
	}
}

// 9.17
class RunStrecke {
	public static void main(String[] args) {
		PunktOld a = new PunktOld(1, 1);
		PunktOld b = new PunktOld(3, 3);
		StreckeOld s = new StreckeOld(a, b);
		System.out.println(s);
		double winkel = 0.14;
		s.turn(winkel);
		System.out.println(s.getLaenge());
	}
}

class StreckeOld {
	private PunktOld p;
	private PunktOld q;

	public StreckeOld(PunktOld p, PunktOld q) {
		this.p = p;
		this.q = q;
	}

	public String toString() {
		return p + "_" + q;
	}

	public double getLaenge() {
		return p.distance(p, q);
	}

	public void turn(double phi) {
		q.turn(phi);
		p.turn(phi);
	}
}

/** Klasse fuer PunktOlde (x,y) in der Ebene */
class PunktOld {
	private double x;
	private double y;

	public PunktOld(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void turn(double phi) {
		// dreht das Point-Objekt um den Winkel phi
		double xAlt = x;
		x = xAlt * Math.cos(phi) - y * Math.sin(phi);
		y = xAlt * Math.sin(phi) + y * Math.cos(phi);
	}

	public static double distance(PunktOld p, PunktOld q) {
		// liefert den Abstand zwischen p und q
		double xdiff = p.getX() - q.getX();
		double ydiff = p.getY() - q.getY();
		return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
	}

	public String toString() {
		// liefert die String-Darstellung des Point-Objekts
		return "(" + x + "," + y + ")";
	}
}

// 9.15 a)
class Fahrzeuga {
	void fahre() {
		System.out.println("Das Fahrzeug faehrt");
	}
}

class Auto extends Fahrzeuga {
	void fahre() {
		System.out.println("Das Auto faehrt");
	}
}

class AutoTest {
	public static void main(String[] args) {
		Fahrzeuga f;
		Auto a = new Auto();
		f = a;
		f.fahre();
	}
}

// 9.15 b)
class AKlasse {
	public int wert = 0;

	public int wert() {
		return this.wert;
	}
}

class CKlasse extends AKlasse {
	public int wert = 1;

	public int wert() {
		return this.wert;
	}
}

class ElchTest {
	public static void main(String argv[]) {
		AKlasse a = new AKlasse();
		System.out.println("Wert von a ist: " + a.wert);
		System.out.println("Wert von a ist: " + a.wert());
		CKlasse b = new CKlasse();
		System.out.println("Wert von b ist: " + b.wert);
		System.out.println("Wert von b ist: " + b.wert());

		AKlasse c = b;
		System.out.println("Wert von c ist: " + c.wert);

		System.out.println("Wert von c ist: " + c.wert());

	}
}

// 9.14
class Mahlzeit {
	Mahlzeit() {
		System.out.println("Mahlzeit()");
	}
}

class Brot {
	Brot() {
		System.out.println("Brot()");
	}
}

class Wurst {
	Wurst() {
		System.out.println("Wurst()");
	}
}

class Salat {
	Salat() {
		System.out.println("Salat()");
	}
}

class Mittagessen extends Mahlzeit {
	Mittagessen() {
		System.out.println("Mittagessen()");
	}
}

class Vesper extends Mittagessen {
	Vesper() {
		System.out.println("Vesper()");
	}
}

class Sandwich extends Vesper {
	Brot b = new Brot();
	Wurst w = new Wurst();
	Salat s = new Salat();

	Sandwich() {
		System.out.println("Sandwich()");
	}

	public static void main(String[] args) {
		new Sandwich();
	}
}

// 9.13
class DSpiel {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		char beuteXpos;
		int beuteYpos;
		String beuteFarbe;
		char jägerXpos;
		int jägerYpos;
		String jägerFarbe;

		System.out.println("Please set the treasure X position (A-Z): ");
		beuteXpos = input.next("^[a-zA-Z]$").charAt(0);
		System.out.println("Now the Y position (1-8): ");
		beuteYpos = input.nextInt();
		System.out.println("And finally the colour (black/white): ");
		beuteFarbe = input.next("black|white");
		System.out.println("Treasure is at " + beuteXpos + beuteYpos + " ("
				+ beuteFarbe + ").");
		DameFigur beute = new DameFigur(beuteXpos, beuteYpos, beuteFarbe);
		Bildschirm.loeschen();

		System.out.println("Please set the hunter X position (A-Z): ");
		jägerXpos = input.next().charAt(0);
		System.out.println("Now the Y position (1-8): ");
		jägerYpos = input.nextInt();
		System.out
				.println("And finally the colour of the hunter (black/white): ");
		jägerFarbe = input.next("black|white");
		System.out.println("Hunter is at " + jägerXpos + jägerYpos + " ("
				+ jägerFarbe + ").");
		DameFigur jäger = new DameFigur(jägerXpos, jägerYpos, jägerFarbe);

		int zug = 0;
		boolean victory = false;
		char richtung;
		int anzahl;
		if (jäger.trifft(beute))
			victory = true;
		while (!jäger.trifft(beute) && zug < 11) {
			System.out
					.println("Wollen sie waagrecht (-), senkrecht (|) oder diagonal (/, \\) ziehen?");
			richtung = input.next().charAt(0);
			System.out.println("Wieviele Felder wollen Sie ziehen?");
			anzahl = input.nextInt();
			jäger.ziehe(richtung, anzahl);
			zug++;
			if (jäger.trifft(beute)) {
				System.out.println("Hit! " + jäger.getXpos() + jäger.getYpos());
				victory = true;
				break;
			} else {
				System.out
						.println("Miss: " + jäger.getXpos() + jäger.getYpos());
			}
		}
		if (victory)
			System.out.println("Congratulations, you've found the treasure in "
					+ zug + " moves.");
		else
			System.out
					.println("Sorry, you didn't find the treasure in 10 moves.");
	}
}

class DameFigur extends SpielFigur {
	// 9.12
	private final String name = "Dame";

	public DameFigur(char x, int y, String f) {
		super(x, y, f);
	}

	public void ziehe(char richtung, int anzahl) {
		switch (richtung) {
		case '-':
			ziehe(anzahl, 0);
			break;
		case '|':
			ziehe(0, anzahl);
			break;
		case '/':
			ziehe(anzahl, anzahl);
			break;
		case '\\':
			ziehe(anzahl, -anzahl);
			break;
		default:
			System.out.println("Warnung: unzulaessigs Zeichen!");
		}
	}

	public boolean trifft(SpielFigur andereFigur) {
		return andereFigur.getXpos() + andereFigur.getYpos() == getXpos()
				+ getYpos();
	}

	public String toString() {
		return getFarbe() + "e Dame auf Feld " + getXpos() + getYpos();
	}
}

class SpielFigur {
	// 9.12
	private char xPos;
	private int yPos;
	private String farbe;

	public SpielFigur(char x, int y, String f) {
		xPos = x;
		yPos = y;
		farbe = f;
		korrigierePosition();
	}

	private void korrigierePosition() {
		if (xPos < 'A')
			xPos = 'A';
		else if (xPos > 'H')
			xPos = 'H';
		if (yPos < 1)
			yPos = 1;
		else if (yPos > 8)
			yPos = 8;
	}

	public char getXpos() {
		return xPos;
	}

	public int getYpos() {
		return yPos;
	}

	public String getFarbe() {
		return farbe;
	}

	public void ziehe(int xF, int yF) {
		xPos = (char) (xPos + xF);
		yPos = yPos + yF;
		korrigierePosition();
	}

	public String toString() {
		return farbe + "e Figur auf Feld " + xPos + yPos;
	}
}

class Bildschirm {
	// 9.12
	public static void loeschen() {
		for (int i = 0; i < 100; i++)
			System.out.println();
	}
}

class MetallPlatte {
	// 9.11
	public double laenge;
	public double breite;

	public MetallPlatte(double laenge, double breite) {
		this.laenge = laenge;
		this.breite = breite;
	}

	public double flaeche() {
		return laenge * breite;
	}

	public boolean schwererAls(MetallPlatte p) {
		return (this.flaeche() > p.flaeche());
	}
}

class GelochtePlatte extends MetallPlatte {
	// 9.11
	private int anzahlLoecher;
	public int maxAnzahlLoecher;
	private double lochLaenge;
	private double lochBreite;
	private MetallPlatte[] loch;

	public GelochtePlatte(double laenge, double breite, int maxAnzahlLoecher) {
		super(laenge, breite);
		this.maxAnzahlLoecher = maxAnzahlLoecher;
		loch = new MetallPlatte[maxAnzahlLoecher];
		lochLaenge = laenge * (1.0 / maxAnzahlLoecher);
		lochBreite = breite * (1.0 / maxAnzahlLoecher);
	}

	public void neuesLochStanzen() {
		System.out.println("Anzahl Löcher: " + anzahlLoecher);
		System.out.println("Maximale Anzahl Löcher: " + maxAnzahlLoecher);
		if (anzahlLoecher >= maxAnzahlLoecher) {
			System.out.println("Maximale Anzahl von Löchern bereits erreicht.");
		} else {
			System.out.println("Stanze neues Loch.");
			loch[anzahlLoecher++] = new MetallPlatte(lochLaenge, lochBreite);
			System.out.println("Anzahl Löcher neu: " + anzahlLoecher);
			System.out.println();
		}
	}

	public int getAnzahlLoecher() {
		return anzahlLoecher;
	}

	public double getLochLaenge() {
		return lochLaenge;
	}

	public double getLochBreite() {
		return lochBreite;
	}

	public double flaeche() {
		double f = super.flaeche();
		for (int i = 0; i < anzahlLoecher; i++)
			f = f - loch[i].flaeche();
		return f;
	}
}

// 9.10
class A {
	A(int i) {
		System.out.println("A aufgerufen " + i);
	}
}

class B {
	B(int i) {
		System.out.println("B aufgerufen " + i);
	}
}

class C extends A {
	B b;

	C(int i) {
		super(i);
		b = new B(i);
		System.out.println("C aufgerufen " + i);
	}
}

class TestABC {
	public static void main(String[] args) {
		// B testB = new B();
		C c = new C(1);
	}
}

interface Folge {
	/**
	 * Liefert genau dann true, wenn weitere Elemente verfuegbar sind
	 */
	boolean elementeVerfuegbar();

	/** Liefert das naechste Element zurueck */
	Object naechstesElement();
}

/**
 * Diese Klasse erzeugt eine Reihe von Zufallszahlen, die man in Form einer
 * Folge durchlaufen kann.
 **/
class Aufzaehlung {
	/** Feld von Zufallszahlen */
	private double[] zahlen;

	/**
	 * Konstruktor. Erzeugt ein Objekt mit n Zufallszahlen
	 */
	public Aufzaehlung(int n) {
		zahlen = new double[n];
		for (int i = 0; i < n; i++)
			zahlen[i] = Math.random();
	}

	/** Gibt die Anzahl der gespeicherten Zahlen aus */
	public int length() {
		return zahlen.length; //
	}

	/** Gibt die i-te Zufallszahl zurueck */
	public double getZahl(int i) {
		return zahlen[i];
	}

	/** Erzeuge eine Folge, die den Inhalt repraesentiert. */
	public Folge folge() { //
		return new Folge() {
			/** Ein Zaehler zeigt an, bei welchem Element wir sind */
			private int zaehler = 0;

			/** Zeigt an, ob es noch mehr Elemente gibt */
			public boolean elementeVerfuegbar() {
				return zaehler < zahlen.length;
			}

			/** Gibt das naechste Element zurueck und erhoeht den Zaehler. */
			public Object naechstesElement() {
				// Wandle die double-Zahl in ein Objekt um
				Double res = new Double(zahlen[zaehler]);
				// Erhoehe den Zaehler
				zaehler++;
				// Gib das Ergebnis zuruec
				return res;
			}
		}; // end of anonymous class

	}

	/**
	 * Main-Methode. Erzeugt ein Zahlenfeld der Laenge 10 und gibt die Zahlen
	 * aus.
	 */
	public static void main(String[] args) {
		Aufzaehlung zahlen = new Aufzaehlung(10);
		for (Folge f = zahlen.folge(); f.elementeVerfuegbar();) {
			System.out.println(f.naechstesElement());
		}
	}
}

interface Plottable {
	public double inf();

	public double sup();

	public double x(double t);

	public double y(double t);
}

class KreisPlot implements Plottable {
	// 9.7
	// ( x(t) ) <- t
	// ( y(t) ),

	public double inf() {
		return 0;
	}

	public double sup() {
		return 2 + Math.PI;
	}

	public double x(double t) {
		return Math.sin(t);
	}

	public double y(double t) {
		return Math.cos(t);
	}

	// public static void main(String[] args) {
	// Plotter p = new Plotter(new KreisPlot(), "Kreisplot");
	// p.adjustGrid(0.2, 0.2);
	// p.showGrid(true);
	// p.setNumOfPoints(9);
	// p.setVisible(true);
	// System.out.print("zum Beenden bitte das ");
	// System.out.println("Grafikfenster schliessen.");
	// }
}

interface Wertgegenstand {
	// an interface is not a class but it can be "inherited" from with
	// the keyword "implements"
	public Waehrung wert();
}

class Goldbarren implements Wertgegenstand {
	public static double preisProGrammInDollar = 60;
	private double gewicht;

	// constructor
	public Goldbarren(double gewichtInGramm) {
		gewicht = gewichtInGramm;
	}

	// actual interface implementation
	public Waehrung wert() {
		return new USDollar(gewicht * preisProGrammInDollar);
	}

	public static Waehrung gesamtwert(Wertgegenstand[] objekte) {
		double summe = 0;
		for (Wertgegenstand w : objekte)
			summe += w.wert().dollarBetrag();
		return new USDollar(summe);
	}
}

class Kruegerrand extends Waehrung implements Wertgegenstand {
	private static double kurs;
	private double wert;

	public Kruegerrand(double wert) {
		this.wert = wert;
	}

	public double dollarBetrag() {
		return wert * kurs;

	}

	public static void setKurs(double Kurs) {
		kurs = Kurs;
	}

	public Waehrung wert() {
		return this;
	}
}

class Papa {
	// prevent polymorphing/overloading/mutating this method
	public final void singe() {
		System.out.println("La la la la la ...");
	}
}

class Kind extends Papa {
	// public void singe() {
	// System.out.println("Do Re Mi Fa So ...");
	// }
}

class Vater {
	/** Eine oeffentliche Variable var */
	public int var;

	/** Konstruktor */
	public Vater() {
		var = 1;
	}

	/** Ausgabe des Variableninhalts */
	public void zeigeVar() {
		System.out.println("VATER: " + var);
	}
}

class Sohn extends Vater {
	/** Eine oeffentliche Variable var */
	public int var;

	/** Konstruktor */
	public Sohn() {
		var = 2;
	}

	/** Ausgabe des Variableninhalts */
	public void zeigeVar() {
		System.out.println("SOHN: " + var);
	}
}

// abstract = not instantiable. used often to inherit from.
// final = not inheritable (just not mutable, in general).
abstract class Waehrung {
	public abstract double dollarBetrag();
}

class USDollar extends Waehrung {
	private double betrag;

	public USDollar(double betrag) {
		this.betrag = betrag;
	}

	public double dollarBetrag() {
		return betrag;
	}
}

class Yen extends Waehrung {

	private static double kurs;

	private double betrag;

	public Yen(double betrag) {
		this.betrag = betrag;
	}

	public double dollarBetrag() {
		return betrag * kurs;
	}

	public static void setKurs(double Kurs) {
		kurs = Kurs;
	}
}

/** Die Waehrung Europas */
class Euro extends Waehrung {
	private static double kurs = 1;
	private double betrag;

	/** Konstruktor */
	public Euro(double betrag) {
		this.betrag = betrag;
	}

	public double dollarBetrag() {
		return betrag * kurs;
	}

	public double euroBetrag() {
		return betrag;
	}

	public String toString() {
		return euroBetrag() + "€";
	}

	public static void setEuroKurs(double Kurs) {
		kurs = Kurs;
	}
}

class DM extends Euro {

	public DM(double betrag) {
		super(betrag / 1.95583);
	}

	public DM(Euro euro) {
		super(euro.euroBetrag());
	}

	public double waehrungsBetrag() {
		return euroBetrag() * 1.95583;
	}
}

class Lire extends Euro {

	public Lire(double betrag) {
		super(betrag / 1936.27);
	}

	public Lire(Euro euro) {
		super(euro.euroBetrag());
	}

	public double waehrungsBetrag() {
		return euroBetrag() * 1936.27;
	}
}

class Franc extends Euro {

	public Franc(double betrag) {
		super(betrag / 6.55957);
	}

	public Franc(Euro euro) {
		super(euro.euroBetrag());
	}

	public double waehrungsBetrag() {
		return euroBetrag() * 6.55957;
	}

	// overwriting java.lang.Object methods
	public String toString() {
		return "$" + dollarBetrag();
	}

	// Note: When overloading "equals()" also overwrite "hashCode()"
	public boolean equals(Object obj) {
		if (obj instanceof Waehrung)
			return this.dollarBetrag() == ((Waehrung) obj).dollarBetrag();
		else
			return super.equals(obj);
	}

	public int hashCode() {
		return (int) (dollarBetrag() * 100);
	}
}

class CurrencyCalc {
	public DM betrag;

	public CurrencyCalc(double betrag) {
		this.betrag = new DM(betrag);
	}

	public double inEuro() {
		return betrag.euroBetrag();
	}

	public double inFranc() {
		Franc f = new Franc(betrag);
		return f.waehrungsBetrag();
	}

	public double inLire() {
		Lire L = new Lire(betrag);
		return L.waehrungsBetrag();
	}

}
