package grundkurs_java;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

public class Chap14 {

	public static void main(String[] args) {
		// FrameMitTextUndToolTip.main(args);
		// FrameMitSchwarzemLabel.main(args);
		// FrameMitMonospacedText.main(args);
		// FrameMitFlowLayout.main(args);
		// FrameMitBorderLayout.main(args);
		// FrameMitGridLayout.main(args);
		// FrameMitBild.main(args);
		// FrameMitButtons.main(args);
		// FrameMitToggleButtons.main(args);
		// FrameMitCheckBoxes.main(args);
		// FrameMitRadioButtons.main(args);
		// FrameMitComboBoxes.main(args);
		// FrameMitListe.main(args);
		// FrameMitTextFeldern.main(args);
		// FrameMitTextArea.main(args);
		// FrameMitScrollText.main(args);
		// FrameMitPanels.main(args);
		// TopLevelContainer.main(args);
		// FrameMitMenuBar.main(args);
		// VierButtonFrame.main(args);
		// NotenEingabeTest.main(args);
		// NotenEingabeNeu.main(args);
		SpruchDesTages.main(args);
	}
}

class SpruchDesTages extends JFrame {

	String[] sprueche = {
			"I'm sorry, i missed the chequered flag. When did the argument start?",
			"I've given this some serious thought, and decided that my favorite word is boobytrap. Because when you spell it backwards it's partyboob." };
	Container c;
	// Container dieses Frames
	JLabel info;
	// Label
	JTextArea ta;

	// TextArea
	public SpruchDesTages() {
		c = getContentPane();
		// Konstruktor
		// Container bestimmen
		// Erzeuge Label und TextArea
		info = new JLabel("Spruch des Tages:");
		ta = new JTextArea(sprueche[(int) (sprueche.length * Math.random())]);
		// Setze die Schriftart
		Font schrift = new Font("SansSerif", Font.BOLD + Font.ITALIC, 16);
		ta.setFont(schrift);
		ta.setLineWrap(true);
		// Automatischer Zeilenumbruch
		ta.setWrapStyleWord(true);
		// wortweise
		JScrollPane sp = new JScrollPane(ta);
		// Scrollpane erzeugen
		// Fuege die Komponenten hinzu
		c.add(info, BorderLayout.NORTH);
		c.add(sp);
	}

	public static void main(String[] args) {
		SpruchDesTages fenster = new SpruchDesTages();
		fenster.setTitle("Spruch des Tages");
		fenster.setSize(200, 160);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class TextFelderAuslesen extends JFrame {
	// 14.4
	public static void main(String[] args) {
		FrameMitTextFeldern fenster = new FrameMitTextFeldern();
		fenster.setTitle("Frame mit Textfeldern");
		fenster.setSize(220, 100);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println("Geben Sie im Frame in beide Textfelder etwas ein.");
		System.out.println();
		System.out
				.println("Druecken Sie danach hier im Konsolenfenster die Eingabetaste!");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Im Feld 'Name' steht: " + fenster.tf.getText());
		System.out.println("Im Feld 'Passwort' steht: " + fenster.pf.getText()); // deprecated
		System.out.println("Im Feld 'Passwort' steht: "
				+ new String(fenster.pf.getPassword()));
	}
}

class NotenEingabeNeu extends JFrame {
	// 14.3
	Container c;

	public NotenEingabeNeu() {
		JRadioButton A, B, C, D, E;
		ButtonGroup bg = new ButtonGroup();
		c = getContentPane();
		c.setLayout(new GridLayout(6, 1));
		c.add(A = new JRadioButton("sehr gut"));
		c.add(B = new JRadioButton("gut"));
		c.add(C = new JRadioButton("befriedigend"));
		c.add(D = new JRadioButton("ausreichend", true));
		c.add(E = new JRadioButton("ungenuegend"));
		bg.add(A);
		bg.add(B);
		bg.add(C);
		bg.add(D);
		bg.add(E);
		c.add(new JCheckBox("Wiederholungspruefung"));
	}
}

class NotenEingabeTest {
	public static void main(String[] args) {
		NotenEingabeNeu a = new NotenEingabeNeu();
		a.setSize(150, 200);
		a.show();
	}
}

class NotenEingabe extends JFrame {
	Container c;

	public NotenEingabe() {
		c = getContentPane();
		c.setLayout(new GridLayout(5, 1));
		c.add(new JCheckBox("sehr gut"));
		c.add(new JCheckBox("gut"));
		c.add(new JCheckBox("befriedigend"));
		c.add(new JCheckBox("ausreichend", true));
		c.add(new JCheckBox("ungenuegend"));
	}
}

class VierButtonFrame extends JFrame {
	// 14.1 + 14.2
	Container c;
	JLabel beschriftung;

	public VierButtonFrame(int i) {
		c = getContentPane();
		if (i == 1)
			c.setLayout(new FlowLayout());
		else if (i == 2)
			c.setLayout(new BorderLayout());
		else if (i == 3)
			c.setLayout(new GridLayout());
		else
			c.setLayout(new GridLayout(0, 1));

		JButton[] A = { new JButton("A"), new JButton("B"), new JButton("C"),
				new JButton("D") };

		for (int j = 0; j < 4; j++) {
			int bg = 1 + (int) (254 * Math.random());
			A[j].setBackground(new Color(bg, bg, bg));
			A[j].setToolTipText("Background color: " + bg);
			c.add(A[j]);
		}

	}

	public static void main(String[] args) {
		VierButtonFrame[] fenster = new VierButtonFrame[4];
		for (int i = 0; i < 4; i++) {
			fenster[i] = new VierButtonFrame(i + 1);
			fenster[i].setTitle("Fenster " + (i + 1));
			fenster[i].setSize(200, 200);
			fenster[i].setLocation(i * 200, 0);
			fenster[i].setVisible(true);
			fenster[i].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
}

class FrameMitMenuBar extends JFrame {
	Container c;
	// Container dieses Frames
	JMenuBar menuBar;
	// Menueleiste
	JMenu menu;
	// Menue
	JMenuItem menuItem;
	// Menue-Eintrag
	JToolBar toolBar;
	// Werkzeugleiste
	JButton button;
	// Knoepfe der Werkzeugleiste
	JLabel textLabel;

	// Label, das im Frame erscheinen soll
	public FrameMitMenuBar() { // Konstruktor
		// Bestimme die Referenz auf den eigenen Container
		c = getContentPane();
		// Erzeuge die Menueleiste.
		menuBar = new JMenuBar();
		// Erzeuge ein Menue
		menu = new JMenu("Bilder");
		menu.setMnemonic(KeyEvent.VK_B);
		// Erzeuge die Menue-Eintraege und fuege sie dem Menue hinzu
		menuItem = new JMenuItem("Hund");
		menuItem.setMnemonic(java.awt.event.KeyEvent.VK_H);
		menu.add(menuItem);
		menuItem = new JMenuItem("Katze");
		menuItem.setMnemonic(java.awt.event.KeyEvent.VK_K);
		menu.add(menuItem);
		menuItem = new JMenuItem("Maus");
		menuItem.setMnemonic(java.awt.event.KeyEvent.VK_M);
		menu.add(menuItem);
		// Fuege das Menue der Menueleiste hinzu
		menuBar.add(menu);
		// Fuegt das Menue dem Frame hinzu
		setJMenuBar(menuBar);
		// Erzeuge die Werkzeugleiste
		toolBar = new JToolBar("Rahmenfarbe");
		// Erzeuge die Knoepfe
		button = new JButton(new ImageIcon("images/rot.gif"));
		button.setToolTipText("roter Rahmen");
		toolBar.add(button);
		button = new JButton(new ImageIcon("images/gruen.gif"));
		button.setToolTipText("gruener Rahmen");
		toolBar.add(button);
		button = new JButton(new ImageIcon("images/blau.gif"));
		button.setToolTipText("blauer Rahmen");
		toolBar.add(button);
		// Erzeuge das Labelobjekt
		textLabel = new JLabel("Hier erscheint mal ein Bild mit Rahmen.",
				JLabel.CENTER);
		// Fuege Label und Toolbar dem Container hinzu
		c.add(textLabel, BorderLayout.CENTER);
		c.add(toolBar, BorderLayout.NORTH);
	}

	public static void main(String[] args) {
		FrameMitMenuBar fenster = new FrameMitMenuBar();
		fenster.setTitle("Frame mit Menueleiste und Toolbar");
		fenster.setSize(350, 170);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class TopLevelContainer {
	public static void main(String[] args) {
		// Hauptfenster erzeugen und beschriften
		JFrame f = new JFrame();
		f.getContentPane().add(new JLabel("Frame", JLabel.CENTER));
		f.setTitle("Frame");
		f.setSize(300, 150);
		f.setLocation(100, 100);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Unterfenster (Window) erzeugen und beschriften
		JWindow w = new JWindow(f);
		w.getContentPane().add(new JLabel("Window", JLabel.CENTER));
		w.setSize(150, 150);
		w.setLocation(410, 100);
		w.setVisible(true);
		// Modales Unterfenster (Dialog) erzeugen und beschriften
		JDialog d = new JDialog(f, true);
		d.getContentPane().add(new JLabel("Dialog", JLabel.CENTER));
		d.setTitle("Dialog");
		d.setSize(150, 100);
		d.setLocation(300, 180);
		d.setVisible(true);
		d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}

class FrameMitPanels extends JFrame {
	Container c;
	// Container dieses Frames
	JPanel jp1, jp2, jp3; // Panels

	public FrameMitPanels() {
		c = getContentPane();
		// Panels erzeugen
		jp1 = new JPanel();
		// Konstruktor
		// Container bestimmen14.5 Einige Grundkomponenten
		jp2 = new JPanel();
		jp3 = new JPanel(new GridLayout(2, 3));
		// Vier Tasten in Panel 1 einfuegen
		for (int i = 1; i <= 4; i++)
			jp1.add(new JButton("Taste " + i));
		// Bildobjekt erzeugen
		Icon bild = new ImageIcon("kitten.jpg");
		// Bild drei mal in Panel 2 einfuegen
		for (int i = 1; i <= 3; i++)
			jp2.add(new JLabel(bild));
		// Sechs Haekchen-Kaestchen in Panel 3 einfuegen
		for (int i = 1; i <= 6; i++)
			jp3.add(new JCheckBox("Auswahl-Box " + i));
		// Panels in den Container einfuegen
		c.add(jp1, BorderLayout.NORTH);
		c.add(jp2, BorderLayout.CENTER);
		c.add(jp3, BorderLayout.SOUTH);
	}

	public static void main(String[] args) {
		FrameMitPanels fenster = new FrameMitPanels();
		fenster.setTitle("Label mit Panels");
		fenster.setSize(350, 200);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitScrollText extends JFrame {
	Container c;
	// Container dieses Frames
	JLabel info;
	// Label
	JTextArea ta;
	// TextArea
	JScrollPane sp;

	// ScrollPane
	public FrameMitScrollText() {
		c = getContentPane();
		// Konstruktor
		// Container bestimmen
		// Erzeuge Label und TextArea
		info = new JLabel("Hier kann Text bearbeitet werden");
		ta = new JTextArea("Einiges an Text steht auch schon hier rum.");
		// Setze die Schriftart
		Font schrift = new Font("SansSerif", Font.BOLD + Font.ITALIC, 16);
		ta.setFont(schrift);
		ta.setLineWrap(true);
		// Automatischer Zeilenumbruch
		ta.setWrapStyleWord(true);
		// wortweise
		sp = new JScrollPane(ta);
		// Scrollpane erzeugen
		// Fuege die Komponenten hinzu
		c.add(info, BorderLayout.NORTH);
		c.add(sp);
	}

	public static void main(String[] args) {
		FrameMitScrollText fenster = new FrameMitScrollText();
		fenster.setTitle("Frame mit ScrollTextArea");
		fenster.setSize(250, 160);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitTextArea extends JFrame {
	Container c;
	// Container dieses Frames
	JLabel info;
	// Label
	JTextArea ta;

	// TextArea
	public FrameMitTextArea() {
		c = getContentPane();
		// Konstruktor
		// Container bestimmen
		// Erzeuge Label und TextArea
		info = new JLabel("Hier kann Text bearbeitet werden");
		ta = new JTextArea("Einiges an Text steht auch schon hier rum.");
		// Setze die Schriftart
		Font schrift = new Font("SansSerif", Font.BOLD + Font.ITALIC, 16);
		ta.setFont(schrift);
		// Automatischen Umbruch aktivieren
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		// Fuege die Komponenten hinzu
		c.add(info, BorderLayout.NORTH);
		c.add(ta);
	}

	public static void main(String[] args) {
		FrameMitTextArea fenster = new FrameMitTextArea();
		fenster.setTitle("Frame mit TextArea");
		fenster.setSize(200, 160);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitTextFeldern extends JFrame {
	Container c;
	// Container dieses Frames
	JLabel name, passwd;
	// Labels
	JTextField tf;
	// Textfeld
	JPasswordField pf;

	// Passwortfeld
	public FrameMitTextFeldern() {
		// Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new GridLayout(2, 2)); // Layout setzen14.5 Einige
											// Grundkomponenten
		// Erzeuge die Labels und Textfelder
		name = new JLabel("Name:", JLabel.RIGHT);
		passwd = new JLabel("Passwort:", JLabel.RIGHT);
		tf = new JTextField();
		pf = new JPasswordField();
		// Setze die Schriftart
		Font schrift = new Font("SansSerif", Font.BOLD, 18);
		name.setFont(schrift);
		passwd.setFont(schrift);
		tf.setFont(schrift);
		pf.setFont(schrift);
		// Fuege die Komponenten hinzu
		c.add(name);
		c.add(tf);
		c.add(passwd);
		c.add(pf);
	}

	public static void main(String[] args) {
		FrameMitTextFeldern fenster = new FrameMitTextFeldern();
		fenster.setTitle("Frame mit Textfeldern");
		fenster.setSize(220, 100);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitListe extends JFrame {
	Container c;
	// Container dieses Frames
	// Liste und Combo-Box, die im Frame erscheinen sollen
	JList vornamen;
	JComboBox nachnamen;

	public FrameMitListe() {
		// Konstruktor446
		c = getContentPane();
		c.setLayout(new FlowLayout());
		// Container bestimmen
		// Layout setzen
		// Eintraege fuer Vornamen-Combo-Box festlegen
		String[] namen = new String[] { "Bilbo", "Frodo", "Samwise",
				"Meriadoc", "Peregrin" };
		vornamen = new JList(namen);
		// Liste mit Eintraegen
		nachnamen = new JComboBox();
		// Leere Combo-Box
		nachnamen.addItem("Baggins");
		// Eintraege hinzufuegen
		nachnamen.addItem("Brandybuck");
		nachnamen.addItem("Gamgee");
		nachnamen.addItem("Took");
		// Liste und Combo-Box dem Frame hinzufuegen
		c.add(vornamen);
		c.add(nachnamen);
	}

	public static void main(String[] args) {
		FrameMitListe fenster = new FrameMitListe();
		fenster.setTitle("Frame mit Liste");
		fenster.setSize(240, 160);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitComboBoxes extends JFrame {
	Container c;
	// Container dieses Frames
	// Combo-Boxes, die im Frame erscheinen sollen
	JComboBox vornamen, nachnamen;

	public FrameMitComboBoxes() {
		// Konstruktor14.5 Einige Grundkomponenten
		c = getContentPane();
		c.setLayout(new FlowLayout());
		// Container bestimmen
		// Layout setzen
		// Eintraege fuer Vornamen-Combo-Box festlegen
		String[] namen = new String[] { "Bilbo", "Frodo", "Samwise",
				"Meriadoc", "Peregrin" };
		vornamen = new JComboBox(namen); // Combo-Box mit Eintraegen
		nachnamen = new JComboBox();
		// Leere Combo-Box
		nachnamen.addItem("Baggins");
		// Eintraege hinzufuegen
		nachnamen.addItem("Brandybuck");
		nachnamen.addItem("Gamgee");
		nachnamen.addItem("Took");
		// Den dritten Nachnamen (Index 2) selektieren
		nachnamen.setSelectedIndex(2);
		// Combo-Boxes dem Frame hinzufuegen
		c.add(vornamen);
		c.add(nachnamen);
	}

	public static void main(String[] args) {
		FrameMitComboBoxes fenster = new FrameMitComboBoxes();
		fenster.setTitle("Frame mit ComboBoxes");
		fenster.setSize(240, 160);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitRadioButtons extends JFrame {
	Container c;
	// Container dieses Frames
	// Feld fuer Radio-Buttons, die im Frame erscheinen sollen
	JRadioButton rb[] = new JRadioButton[4];

	public FrameMitRadioButtons() { // Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new FlowLayout());
		// Layout setzen
		// Gruppe erzeugen
		ButtonGroup bg = new ButtonGroup();
		// Erzeuge die Button-Objekte und fuege
		// sie dem Frame und der Gruppe hinzu
		for (int i = 0; i < 4; i++) {
			rb[i] = new JRadioButton("Box " + (i + 1)); // erzeugen
			bg.add(rb[i]); // der Gruppe hinzufuegen
			c.add(rb[i]); // dem Frame hinzufuegen
		}
	}

	public static void main(String[] args) {
		FrameMitRadioButtons fenster = new FrameMitRadioButtons();
		fenster.setTitle("Frame mit RadioButtons");
		fenster.setSize(330, 60);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitCheckBoxes extends JFrame {
	Container c;
	// Container dieses Frames
	// Feld fuer Check-Boxes, die im Frame erscheinen sollen14.5 Einige
	// Grundkomponenten
	JCheckBox cb[] = new JCheckBox[4];

	public FrameMitCheckBoxes() { // Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new FlowLayout());
		// Layout setzen
		// Erzeuge die Button-Objekte
		for (int i = 0; i < 4; i++)
			cb[i] = new JCheckBox("Box " + (i + 1));
		cb[0].setSelected(true);
		cb[2].setSelected(true);
		// Fuege die Buttons dem Frame hinzu
		for (int i = 0; i < 4; i++) {
			c.add(cb[i]);
		}
	}

	public static void main(String[] args) {
		FrameMitCheckBoxes fenster = new FrameMitCheckBoxes();
		fenster.setTitle("Frame mit CheckBoxes");
		fenster.setSize(280, 60);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitToggleButtons extends JFrame {
	Container c;
	// Container dieses Frames
	// Feld fuer Toggle-Buttons, die im Frame erscheinen sollen
	JToggleButton b[] = new JToggleButton[4];

	public FrameMitToggleButtons() {
		c = getContentPane();
		c.setLayout(new FlowLayout());
		// Konstruktor
		// Container bestimmen
		// Layout setzen
		// Erzeuge die Button-Objekte
		for (int i = 0; i < 4; i++) {
			b[i] = new JToggleButton("Schalter " + (i + 1));
			b[i].setFont(new Font("SansSerif", Font.ITALIC, 24));
		}
		b[0].setSelected(true);
		b[2].setSelected(true);
		// Fuege die Buttons dem Frame hinzu
		for (int i = 0; i < 4; i++) {
			c.add(b[i]);
		}
	}

	public static void main(String[] args) {
		FrameMitToggleButtons fenster = new FrameMitToggleButtons();
		fenster.setTitle("Frame mit Buttons");
		fenster.setSize(330, 130);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitButtons extends JFrame {
	Container c;
	// Container dieses Frames
	// Feld fuer Buttons, die im Frame erscheinen sollen
	JButton b[] = new JButton[4];

	public FrameMitButtons() {
		// Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new FlowLayout());
		// Layout setzen
		// Erzeuge die Button-Objekte
		for (int i = 0; i < 4; i++) {
			b[i] = new JButton("Taste " + (i + 1));
			b[i].setFont(new Font("SansSerif", Font.ITALIC, 24));
		}
		// Fuege die Buttons dem Frame hinzu
		for (int i = 0; i < 4; i++) {
			c.add(b[i]);
		}
	}

	public static void main(String[] args) {
		FrameMitButtons fenster = new FrameMitButtons();
		fenster.setTitle("Frame mit Buttons");
		fenster.setSize(250, 130);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitBild extends JFrame {
	Container c;
	// Container dieses Frames
	JLabel lab;

	// Label das im Frame erscheinen soll
	public FrameMitBild() { // Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new FlowLayout());
		// Layout setzen
		// Bildobjekt erzeugen
		Icon bild = new ImageIcon("kitten.jpg");
		// Label mit Text und Bild beschriften
		lab = new JLabel("Spotty", bild, JLabel.CENTER);
		// Text unter das Bild setzen
		lab.setHorizontalTextPosition(JLabel.CENTER);
		lab.setVerticalTextPosition(JLabel.BOTTOM);
		// Fuege das Label dem Frame hinzu
		c.add(lab);
	}

	public static void main(String[] args) {
		FrameMitBild fenster = new FrameMitBild();
		fenster.setTitle("Label mit Bild und Text");
		fenster.setSize(250, 185);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitGridLayout extends JFrame {
	Container c;
	// Container dieses Frames
	// Feld fuer Labels, die im Frame erscheinen sollen
	FarbigesLabel fl[] = new FarbigesLabel[6];

	public FrameMitGridLayout() {
		// Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new GridLayout(2, 3, 10, 40)); // Layout setzen
		/* Erzeuge die Labelobjekte mit Text und Farbe */
		for (int i = 0; i < 6; i++) {
			int rgbFg = 255 - i * 50;
			int rgbBg = i * 50;
			fl[i] = new FarbigesLabel("Nummer " + (i + 1), new Color(rgbFg,
					rgbFg, rgbFg), new Color(rgbBg, rgbBg, rgbBg));
			fl[i].setFont(new Font("Serif", Font.ITALIC, 10 + i * 3));
		}
		// Fuege die Labels dem Frame hinzu
		for (int i = 0; i < 6; i++) {
			c.add(fl[i]);
		}
	}

	public static void main(String[] args) {
		FrameMitGridLayout fenster = new FrameMitGridLayout();
		fenster.setTitle("Frame mit Grid-Layout");
		fenster.setSize(300, 150);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitBorderLayout extends JFrame {
	Container c;
	// Container dieses Frames
	// Labelfeld fuer Label, die im Frame erscheinen sollen430
	FarbigesLabel fl[] = new FarbigesLabel[5];

	public FrameMitBorderLayout() {
		// Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new BorderLayout());
		// Layout setzen
		/* Erzeuge die Labelobjekte mit Text und Farbe */
		fl[0] = new FarbigesLabel("Norden", Color.BLACK, Color.WHITE);
		fl[1] = new FarbigesLabel("Sueden", Color.WHITE, Color.LIGHT_GRAY);
		fl[2] = new FarbigesLabel("Osten", Color.WHITE, Color.GRAY);
		fl[3] = new FarbigesLabel("Westen", Color.WHITE, Color.DARK_GRAY);
		fl[4] = new FarbigesLabel("Zentrum", Color.WHITE, Color.BLACK);
		for (int i = 0; i < 5; i++) {
			// Setze die Schriftart der Labelbeschriftung
			fl[i].setFont(new Font("SansSerif", Font.BOLD, 14));
			// Setze die horizontale Position des Labeltextes auf dem Label
			fl[i].setHorizontalAlignment(JLabel.CENTER);
		}
		// Fuege die Labels dem Frame hinzu
		c.add(fl[0], BorderLayout.NORTH);
		c.add(fl[1], BorderLayout.SOUTH);
		c.add(fl[2], BorderLayout.EAST);
		c.add(fl[3], BorderLayout.WEST);
		c.add(fl[4], BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		FrameMitBorderLayout fenster = new FrameMitBorderLayout();
		fenster.setTitle("Frame mit Border-Layout");
		fenster.setSize(300, 150);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitFlowLayout extends JFrame {
	Container c;
	// Container dieses Frames
	// Feld fuer Labels, die im Frame erscheinen sollen
	FarbigesLabel fl[] = new FarbigesLabel[4];

	public FrameMitFlowLayout() {
		// Konstruktor
		c = getContentPane();
		// Container bestimmen
		c.setLayout(new FlowLayout());
		// Layout setzen
		// Erzeuge die Labelobjekte mit Uebergabe der Labeltexte
		for (int i = 0; i < 4; i++) {
			int rgbFg = 255 - i * 80;
			// Farbwert fuer Vordergrund
			int rgbBg = i * 80;
			// Farbwert fuer Hintergrund
			fl[i] = new FarbigesLabel("Nummer " + (i + 1), new Color(rgbFg,
					rgbFg, rgbFg), new Color(rgbBg, rgbBg, rgbBg));
			fl[i].setFont(new Font("Serif", Font.ITALIC, 28));
		}
		// Fuege die Labels dem Frame hinzu
		for (int i = 0; i < 4; i++) {
			c.add(fl[i]);
		}
	}

	public static void main(String[] args) {
		FrameMitFlowLayout fenster = new FrameMitFlowLayout();
		fenster.setTitle("Frame mit Flow-Layout");
		fenster.setSize(300, 150);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitMonospacedText extends JFrame {
	Container c;
	// Container dieses Frames
	JLabel textLabel; // Label das im Frame erscheinen soll

	public FrameMitMonospacedText() {
		c = getContentPane();
		c.setLayout(new FlowLayout());
		// Konstruktor
		// Container bestimmen
		// Layout setzen
		// Erzeuge das Labelobjekt mit Uebergabe des Labeltextes
		textLabel = new JLabel("Monospaced Text");
		// Setze die Schriftart fuer die Labelschriftart
		textLabel.setFont(new Font("Monospaced", Font.BOLD + Font.ITALIC, 30));
		// Fuege das Label dem Frame hinzu
		c.add(textLabel);
	}

	public static void main(String[] args) {
		FrameMitMonospacedText fenster = new FrameMitMonospacedText();
		fenster.setTitle("Frame mit monospaced Text");
		fenster.setSize(300, 80);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FrameMitSchwarzemLabel extends JFrame {
	Container c;
	// Container dieses Frames
	FarbigesLabel schwarzesLabel; // Label, das im Frame erscheinen soll

	public FrameMitSchwarzemLabel() {
		c = getContentPane();
		c.setLayout(new FlowLayout());
		// Konstruktor
		// Container bestimmen
		// Layout setzen
		// Erzeuge das Labelobjekt mit Uebergabe des Labeltextes
		schwarzesLabel = new FarbigesLabel("schwarzes Label", new Color(255,
				255, 255), Color.BLACK);
		// Fuege das Label dem Frame hinzu
		c.add(schwarzesLabel);
	}

	public static void main(String[] args) {
		FrameMitSchwarzemLabel fenster = new FrameMitSchwarzemLabel();
		fenster.setTitle("Frame mit schwarzem Label");
		fenster.setSize(300, 60);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class FarbigesLabel extends JLabel {
	public FarbigesLabel(String text, Color fG, Color bG) { // Konstruktor
		// Uebergabe des Labeltextes an den Super-Konstruktor
		super(text);
		// Setze den Hintergrund des Labels auf undurchsichtig
		setOpaque(true);
		// Setze die Farbe der Beschriftung des Labels
		setForeground(fG);
		// Setze die Hintergrundfarbe des Labels
		setBackground(bG);
	}
}

class FrameMitTextUndToolTip extends JFrame {
	Container c;
	// Container dieses Frames
	JLabel beschriftung; // Label das im Frame erscheinen soll

	public FrameMitTextUndToolTip() { // Konstruktor
		// Bestimme die Referenz auf den eigenen Container
		c = getContentPane();
		// Setze das Layout
		c.setLayout(new FlowLayout());
		// Erzeuge das Labelobjekt mit Uebergabe des Labeltextes
		beschriftung = new JLabel("Label-Text im Frame");
		// Fuege das Label dem Frame hinzu
		c.add(beschriftung);
		// Fuege dem Label einen Tooltip hinzu
		beschriftung.setToolTipText("Des isch nur en Tescht!");
	}

	public static void main(String[] args) {
		FrameMitTextUndToolTip fenster = new FrameMitTextUndToolTip();
		fenster.setTitle("Frame mit Text im Label mit Tooltip");
		fenster.setSize(400, 150);
		fenster.setVisible(true);
		fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}