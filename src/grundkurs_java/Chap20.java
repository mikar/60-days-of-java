package grundkurs_java;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.*;
import java.util.*;
import java.text.*;

import javax.swing.*;

public class Chap20 {

	public static void main(String[] args) {
		// DNSAnfrage.main(new String[] { "www.github.com" });
		// DateTimeServer.main(new String[] { "2222" });
		// DateTimeClient.main(new String[] { "localhost", "2222" });
		// DateTimeMultiServer.main(new String[] { "3333" });
		// MyClient.main(new String[] { "3333" });
		// LiesURL.main(new String[] { "http://github.com" });
		// CdServer.main(new String[] { "3334" });
		// CurrencyServer.main(new String[] { "3334" });
		TalkServer.main(new String[] { "3332" });
	}
}

class TalkServer {

	public static void main(String[] args) {
		Socket c1;
		Socket c2;
		// Argumentanzahl überprüfen
		if (args.length == 1) {
			// Port-Nummer bestimmen
			int port = Integer.parseInt(args[0]);
			// try-catch-Block beginnen
			try {
				// Einen Socket für den Server erzeugen
				ServerSocket server = new ServerSocket(port);
				System.out.println("Der Server laeuft");
				// Endlosschleife
				while (true) {
					// Für je zwei Clients, die eine Verbindung aufbauen,
					// zwei Talk-Dienst Threads starten
					c1 = server.accept();
					c2 = server.accept();
					new TalkDienst(c1, c2).start();
					new TalkDienst(c2, c1).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// Hinweis für korrekten Aufruf auf die Konsole ausgeben
			System.out.println("Aufruf: java TalkServer <PortNummer>");
		}
	}
}

class TalkDienst extends Thread {

	Socket c1, c2; // Sockets für die beiden Clients
	BufferedReader in; // Eingabe-Ströme zu den Clients
	PrintWriter out; // Ausgabe-Ströme zu den Clients

	/**
	 * Eingabe- und Ausgabestroeme zu den Clients erzeugen
	 *
	 */
	TalkDienst(Socket sin, Socket sout) {
		// Die Client-Sockets in den Instanzvariablen speichern
		c1 = sin;
		c2 = sout;
		// try-catch-Block beginnen
		try {
			// Den Eingabe-Strom vom anderen Client erzeugen
			in = new BufferedReader(new InputStreamReader(c1.getInputStream()));

			// Den Ausgabe-Strom zum anderen Client erzeugen
			out = new PrintWriter(c2.getOutputStream(), true);
			out.println("*** " + "Chatpartner gefunden, es kann losgehen!"
					+ " ***");
		} catch (IOException e) {
		}
	}

	// run-Methode ueberschreiben: Abarbeitung des eigentlichen Protokolls

	public void run() {
		String line;

		try {
			while (true) {
				line = in.readLine();
				if (line != null)
					out.println(line);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Fehler:" + e);
		}
	}
}

class EuroServer {
	// 20.2 offiziell
	static boolean serverAktiv = true;

	public static void main(String[] args) {
		// Argumentanzahl überprüfen
		if (args.length == 1) {
			// Port-Nummer bestimmen
			int port = Integer.parseInt(args[0]);
			// Try-Catch-Block beginnen
			try {
				// Server-Steuerung aktivieren
				new SteuerDienst().start();
				// Einen Socket für den Server erzeugen
				ServerSocket server = new ServerSocket(port);
				System.out.println("Der Server laeuft.");
				// "Endlos"-Schleife
				while (serverAktiv) {
					// Für jeden Client, der eine Verbindung aufbaut,
					// einen EuroThread starten
					new EuroThread(server.accept()).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Der Server ist beendet.");
		} else {
			// Hinweis für korrekten Aufruf auf die Konsole ausgeben
			System.out.println("Aufruf: java EuroServer <PortNummer>");
		}
	}
}

class EuroThread extends Thread {
	// 20.2 offiziell
	Socket c; // Socket für den Clients
	BufferedReader in; // Eingabe-Strom zum Client
	PrintWriter out; // Ausgabe-Strom zum Client

	// Einen Konstruktor für den EuroThread deklarieren
	EuroThread(Socket sock) {
		System.out.println("Neuer Client wird bearbeitet.");
		// Den Client-Socket in der Instanzvariablen speichern
		c = sock;
		// Try-Catch-Block beginnen
		try {
			// Den Eingabe-Strom zum Client erzeugen
			in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			// Den Ausgabe-Strom zum Client erzeugen
			out = new PrintWriter(c.getOutputStream(), true);
		} catch (IOException e) {
		}
	}

	public void run() {
		try {

			String zeile;
			double wert;
			boolean toEuroDesired, nochmal;

			nochmal = true;

			// Protokoll für die Unterhaltung

			while (nochmal) {
				out.println("Welche Waehrung wollen Sie eingeben (DM oder EUR)?");
				zeile = in.readLine();
				if (zeile == null)
					break;
				toEuroDesired = zeile.toUpperCase().startsWith("DM");

				out.println("Welchen Wert wollen Sie umrechnen?");
				zeile = in.readLine();
				if (zeile == null)
					break;
				wert = Double.parseDouble(zeile);

				if (toEuroDesired) {
					wert = EuroConverter.convertToEuro(wert, EuroConverter.DEM);
					out.println("Wert in EUR: " + wert);
				} else {
					wert = EuroConverter.convertFromEuro(wert,
							EuroConverter.DEM);
					out.println("Wert in DM: " + wert);
				}

				out.println();
				out.println("Darf's noch eine Umrechnung sein?");
				zeile = in.readLine();
				if (zeile == null)
					break;
				nochmal = zeile.startsWith("j") || zeile.startsWith("J");
			}
		} catch (IOException ign) {
		}
	}
}

class SteuerDienst extends Thread {
	// 20.2 offiziell
	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	String eingabe;

	public void run() {
		try {
			// Solange Benutzer-Eingaben anfordern, bis SHUTDOWN eingegeben wird
			do {
				System.out
						.println("Server beenden durch Eingabe von SHUTDOWN!");
			} while (!(eingabe = in.readLine()).toLowerCase().startsWith(
					"shutdown"));
			// EuroServer deaktivieren
			EuroServer.serverAktiv = false;
			System.out.println("Der Server wird nun nach Abarbeitung des");
			System.out.println("naechsten Clients automatisch beendet.");
		} catch (IOException e) {
		}
	}
}

class CurrencyServer {
	// 20.2
	public static void main(String[] args) {
		try {
			int port = Integer.parseInt(args[0]);
			// Port-Nummer
			ServerSocket server = new ServerSocket(port); // Server-Socket
			System.out.println("CurrencyServer wartet auf Port " + port); // Statusmeldung
			while (true) {
				Socket s = server.accept(); // Client-Verbindung akzeptieren
				new CurrencyServerDienst(s).start();
			}
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java CdServer <Port>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class CurrencyServerDienst extends Thread {
	// 20.2
	static SimpleDateFormat time = new SimpleDateFormat(
			"'Es ist gerade 'H'.'mm' Uhr.'"), date = new SimpleDateFormat(
			"'Heute ist 'EEEE', der 'dd.MM.yy");
	// Formate fuer den Zeitpunkt20.2 Client/Server-Programmierung

	static int anzahl = 0;
	// Anzahl der Clients insgesamt
	int nr = 0;
	// Nummer des Clients
	Socket s;
	// Socket in Verbindung mit dem Client
	BufferedReader vomClient;
	// Eingabe-Strom vom Client
	PrintWriter zumClient;

	// Ausgabe-Strom zum Client
	public CurrencyServerDienst(Socket s) { // Konstruktor
		try {
			this.s = s;
			nr = ++anzahl;
			vomClient = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			zumClient = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("IO-Error bei Client " + nr);
			e.printStackTrace();
		}
	}

	public void run() { // Methode, die das Protokoll abwickelt
		System.out.println("Protokoll fuer Client " + nr + " gestartet");
		try {
			while (true) {
				zumClient
						.println("Der Client laueft und kann mit 'quit' beendet werden.");
				zumClient
						.println("Welche Waehrung wollen Sie eingeben (DM oder EUR)?");
				String wunsch = vomClient.readLine(); // vom Client empfangen
				if (wunsch == null || wunsch.equalsIgnoreCase("quit"))
					break;
				Date jetzt = new Date();
				if (wunsch.equalsIgnoreCase("date"))
					zumClient.println(date.format(jetzt));
				else if (wunsch.equalsIgnoreCase("time"))
					zumClient.println(time.format(jetzt));
				else if (wunsch.equalsIgnoreCase("dm"))
					zumClient.println("Wert in Euro: " + toEuro());
				else if (wunsch.equalsIgnoreCase("eur"))
					zumClient.println("Wert in DM: " + toDM());
				else
					zumClient.println(wunsch + "ist als Kommando unzulaessig!");
			}
			s.close();
			// Socket (und damit auch Stroeme) schliessen
		} catch (IOException e) {
			System.out.println("IO-Error bei Client " + nr);
		}
		System.out.println("Protokoll fuer Client " + nr + " beendet");
	}

	private double toEuro() {
		zumClient.println("Bitte DM Betrag eingeben: ");
		String betrag = readBetrag();
		return EuroConverter.convertToEuro(Double.parseDouble(betrag),
				EuroConverter.DEM);
	}

	private double toDM() {
		zumClient.println("Bitte EUR Betrag eingeben: ");
		String betrag = readBetrag();
		return EuroConverter.convertFromEuro(Double.parseDouble(betrag),
				EuroConverter.DEM);
	}

	private String readBetrag() {
		String betrag = null;
		try {
			betrag = vomClient.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return betrag;
	}

}

class CDServer {
	// 20.1 Musterlösung
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Aufruf:  java CDServer <portnr>");
			System.exit(1);
		}
		try {
			int port = Integer.parseInt(args[0]);
			ServerSocket server = new ServerSocket(port);
			System.out.println("CDServer wartet auf Port " + port);
			boolean neu = true;
			while (neu) {
				Socket sock = server.accept();
				new CDVerbindung(sock).start();
			}
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class CDVerbindung extends Thread {
	// 20.1 Musterlösung
	Socket sock;
	PrintWriter sockOut;
	BufferedReader sockIn;
	static final String LIST = "list", TRACKS = "tracks";

	CDVerbindung(Socket sock) {
		this.sock = sock;
		try {
			sockIn = new BufferedReader(new InputStreamReader(
					sock.getInputStream()));
			sockOut = new PrintWriter(sock.getOutputStream(), true);
		} catch (IOException e) {
			protokolliere("Ausnahme beim Verbindungsaufbau: " + e);
		}
	}

	void protokolliere(String action) {
		System.out.println("[" + sock.getInetAddress() + ":" + sock.getPort()
				+ ": " + action + "]");
	}

	public void run() {
		try {
			protokolliere("neue Verbindung");
			String clientAnfrage;
			while ((clientAnfrage = sockIn.readLine()) != null) {
				if (clientAnfrage.equalsIgnoreCase(LIST)) {
					protokolliere("sende Verzeichnis der CDs");
					String[] verzeichnis = new File("cdArchiv").list();
					for (int i = 0; i < verzeichnis.length; i++)
						sockOut.println(verzeichnis[i]);
				} else if (clientAnfrage.startsWith(TRACKS)) {
					String cd = clientAnfrage.substring(TRACKS.length() + 1)
							.trim();
					protokolliere("sende Tracks der CD " + cd);
					BufferedReader fileIn;
					try {
						fileIn = new BufferedReader(new FileReader("cdArchiv/"
								+ cd));
					} catch (FileNotFoundException e) {
						continue;
					}
					while ((cd = fileIn.readLine()) != null)
						sockOut.println(cd);
					fileIn.close();
				}
			}
			sock.close();
			protokolliere("Verbindung unterbrochen");
		} catch (IOException e) {
		}
	}
}

class CdServer {
	// 20.1

	public static void main(String[] args) {
		try {
			int port = Integer.parseInt(args[0]);
			// Port-Nummer
			ServerSocket server = new ServerSocket(port); // Server-Socket
			System.out.println("CdServer wartet auf Port " + port); // Statusmeldung
			File cdArchiv = new File("cdArchiv");
			if (!cdArchiv.exists()) {
				try {
					cdArchiv.mkdir();
				} catch (SecurityException e) {
					System.out
							.println("Could neither find nor create cdArchive directory.");
					System.exit(1);
				}
			}
			while (true) {
				Socket s = server.accept(); // Client-Verbindung akzeptieren
				// Dienst starten
				new CdServerDienst(s, cdArchiv).start();
			}
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java CdServer <Port>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class CdServerDienst extends Thread {
	// 20.1
	static SimpleDateFormat time = new SimpleDateFormat(
			"'Es ist gerade 'H'.'mm' Uhr.'"), date = new SimpleDateFormat(
			"'Heute ist 'EEEE', der 'dd.MM.yy");
	// Formate fuer den Zeitpunkt20.2 Client/Server-Programmierung

	static int anzahl = 0;
	// Anzahl der Clients insgesamt
	int nr = 0;
	// Nummer des Clients
	Socket s;
	// Socket in Verbindung mit dem Client
	BufferedReader vomClient;
	// Eingabe-Strom vom Client
	PrintWriter zumClient;

	File cdArchiv;

	// Ausgabe-Strom zum Client
	public CdServerDienst(Socket s, File cdArchiv) { // Konstruktor
		try {
			this.s = s;
			this.cdArchiv = cdArchiv;
			nr = ++anzahl;
			vomClient = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			zumClient = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("IO-Error bei Client " + nr);
			e.printStackTrace();
		}
	}

	public void run() { // Methode, die das Protokoll abwickelt
		System.out.println("Protokoll fuer Client " + nr + " gestartet");
		try {
			while (true) {
				zumClient
						.println("Mögliche Befehle: list, tracks <album>, date, time, quit");
				String wunsch = vomClient.readLine(); // vom Client empfangen
				if (wunsch == null || wunsch.equalsIgnoreCase("quit"))
					break;
				Date jetzt = new Date();
				if (wunsch.equalsIgnoreCase("date"))
					zumClient.println(date.format(jetzt));
				else if (wunsch.equalsIgnoreCase("time"))
					zumClient.println(time.format(jetzt));
				else if (wunsch.equalsIgnoreCase("list"))
					for (String s : cdArchiv.list())
						zumClient.println(s);
				else if (wunsch.toLowerCase().startsWith("tracks"))
					zumClient.println(findTracks(wunsch));
				else
					zumClient.println(wunsch + "ist als Kommando unzulaessig!");
			}
			s.close();
			// Socket (und damit auch Stroeme) schliessen
		} catch (IOException e) {
			System.out.println("IO-Error bei Client " + nr);
		}
		System.out.println("Protokoll fuer Client " + nr + " beendet");
	}

	private List<String> findTracks(String wunsch) throws IOException {
		File[] matches = cdArchiv.listFiles(new FilesearchFilter(wunsch));
		System.out.println(matches.length + " matches");
		if (matches.length == 0)
			return null;
		return Files
				.readAllLines(matches[0].toPath(), Charset.defaultCharset());
	}

	class FilesearchFilter implements FilenameFilter {

		private String wunsch;

		public FilesearchFilter(String wunsch) {
			this.wunsch = wunsch;
		}

		public boolean accept(File dir, String name) {
			String[] wunschSplit = wunsch.split("\\s");
			for (String w : wunschSplit)
				System.out.println(w);
			return name.startsWith(wunsch.split("\\s")[1]);
		}
	}
}

class DateTimeApplet extends JApplet {
	public void init() {
		try {
			Socket socket = new Socket(this.getCodeBase().getHost(), 7777);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			in.readLine();
			out.println("date");
			String s = in.readLine();
			getContentPane().add(new JLabel(s, JLabel.CENTER));
		} catch (IOException e) {
			String s = "Verbindung zum DateTimeServer fehlgeschlagen!";
			getContentPane().add(new JLabel(s, JLabel.CENTER));
		}
	}
}

class LiesURL {
	public static void main(String[] args) {
		try {
			URL u = new URL(args[0]);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					u.openStream()));
			String zeile;
			while ((zeile = in.readLine()) != null)
				System.out.println(zeile);
			in.close();
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java LiesURL <URL>");
		} catch (MalformedURLException me) {
			System.out.println(args[0] + " ist keine zulaessige URL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyClient {
	// liest alle vom Server geschickten Daten
	static void zeigeWasKommt(BufferedReader sin) throws IOException {
		String str = null;
		try {
			while ((str = sin.readLine()) != null)
				System.out.println(str);
		} catch (SocketTimeoutException sto) {
		}
	}

	static void zeigePrompt() {
		System.out.print("> ");
		System.out.flush();
	}

	public static void main(String[] args) {
		try {
			System.out.println("Client laeuft. Beenden mit QUIT");
			Socket c = new Socket(args[0], Integer.parseInt(args[1]));
			c.setSoTimeout(500); // setze Timeout auf eine halbe Sekunde
			BufferedReader vomServer = new BufferedReader(
					new InputStreamReader(c.getInputStream()));
			PrintWriter zumServer = new PrintWriter(c.getOutputStream(), true);
			BufferedReader vonTastatur = new BufferedReader(
					new InputStreamReader(System.in));
			String zeile;
			do {
				zeigeWasKommt(vomServer);
				zeigePrompt();
				zeile = vonTastatur.readLine();
				zumServer.println(zeile);
			} while (!zeile.equalsIgnoreCase("quit"));
			c.close();
			// Socket (und damit auch Stroeme) schliessen
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java MyClient <Port-Nummer>");
		} catch (UnknownHostException ux) {
			System.out.println("Kein DNS-Eintrag fuer " + args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class DateTimeMultiServer {
	public static void main(String[] args) {
		try {
			int port = Integer.parseInt(args[0]);
			// Port-Nummer
			ServerSocket server = new ServerSocket(port); // Server-Socket
			System.out.println("DateTimeServer laeuft"); // Statusmeldung
			while (true) {
				Socket s = server.accept(); // Client-Verbindung akzeptieren
				new DateTimeDienst(s).start();
				// Dienst starten
			}
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java DateTimeServer <Port>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class DateTimeDienst extends Thread {
	static SimpleDateFormat time = new SimpleDateFormat(
			"'Es ist gerade 'H'.'mm' Uhr.'"), date = new SimpleDateFormat(
			"'Heute ist 'EEEE', der 'dd.MM.yy");
	// Formate fuer den Zeitpunkt20.2 Client/Server-Programmierung

	static int anzahl = 0;
	// Anzahl der Clients insgesamt
	int nr = 0;
	// Nummer des Clients
	Socket s;
	// Socket in Verbindung mit dem Client
	BufferedReader vomClient;
	// Eingabe-Strom vom Client
	PrintWriter zumClient;

	// Ausgabe-Strom zum Client
	public DateTimeDienst(Socket s) { // Konstruktor
		try {
			this.s = s;
			nr = ++anzahl;
			vomClient = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			zumClient = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("IO-Error bei Client " + nr);
			e.printStackTrace();
		}
	}

	public void run() { // Methode, die das Protokoll abwickelt
		System.out.println("Protokoll fuer Client " + nr + " gestartet");
		try {
			while (true) {
				zumClient.println("Geben Sie DATE, TIME oder QUIT ein");
				String wunsch = vomClient.readLine(); // vom Client empfangen
				if (wunsch == null || wunsch.equalsIgnoreCase("quit"))
					break;
				// Schleife abbrechen
				Date jetzt = new Date();
				// Zeitpunkt bestimmen
				// vom Client empfangenes Kommando ausfuehren
				if (wunsch.equalsIgnoreCase("date"))
					zumClient.println(date.format(jetzt));
				else if (wunsch.equalsIgnoreCase("time"))
					zumClient.println(time.format(jetzt));
				else
					zumClient.println(wunsch + "ist als Kommando unzulaessig!");
			}
			s.close();
			// Socket (und damit auch Stroeme) schliessen
		} catch (IOException e) {
			System.out.println("IO-Error bei Client " + nr);
		}
		System.out.println("Protokoll fuer Client " + nr + " beendet");
	}
}

class DateTimeClient {
	public static void main(String[] args) {
		String hostName = ""; // Rechner-Name bzw. -Adresse
		int port;
		// Port-Nummer
		Socket c = null;
		// Socket fuer die Verbindung zum Server
		try {
			hostName = args[0];
			port = Integer.parseInt(args[1]);
			c = new Socket(hostName, port);
			BufferedReader vomServer = new BufferedReader(
					new InputStreamReader(c.getInputStream()));
			PrintWriter zumServer = new PrintWriter(c.getOutputStream(), true);
			BufferedReader vonTastatur = new BufferedReader(
					new InputStreamReader(System.in));
			// Protokoll abwickeln20.2 Client/Server-Programmierung
			System.out.println("Server " + hostName + ":" + port + " sagt:");
			String text = vomServer.readLine(); // vom Server empfangen
			System.out.println(text);
			// auf die Konsole schreiben
			text = vonTastatur.readLine();
			// von Tastatur lesen
			zumServer.println(text);
			// zum Server schicken
			text = vomServer.readLine();
			// vom Server empfangen
			System.out.println(text);
			// auf die Konsole schreiben
			// Socket (und damit auch Stroeme) schliessen
			c.close();
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf:");
			System.out.println("java DateTimeClient <HostName> <PortNr>");
		} catch (UnknownHostException ue) {
			System.out.println("Kein DNS-Eintrag fuer " + hostName);
		} catch (IOException e) {
			System.out.println("IO-Error");
		}
	}
}

class DateTimeServer {
	public static void main(String[] args) {
		try {
			int port = Integer.parseInt(args[0]);
			// Port-Nummer
			ServerSocket server = new ServerSocket(port); // Server-Socket
			System.out.println("DateTimeServer laeuft");
			// Statusmeldung
			Socket s = server.accept();
			// Client-Verbindung akzeptieren
			new DateTimeProtokoll(s).transact();
			// Protokoll abwickeln
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java DateTimeServer <Port-Nr>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class DateTimeProtokoll {
	static SimpleDateFormat
	// Formate fuer den Zeitpunkt
			time = new SimpleDateFormat("'Es ist gerade 'H'.'mm' Uhr.'"),
			date = new SimpleDateFormat("'Heute ist 'EEEE', der 'dd.MM.yy");
	Socket s;
	BufferedReader vomClient;
	PrintWriter zumClient;

	// Socket in Verbindung mit dem Client
	// Eingabe-Strom vom Client
	// Ausgabe-Strom zum Client
	public DateTimeProtokoll(Socket s) { // Konstruktor
		try {
			this.s = s;
			vomClient = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			zumClient = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("IO-Error");
			e.printStackTrace();
		}
	}

	public void transact() {
		// Methode, die das Protokoll abwickelt
		System.out.println("Protokoll gestartet");
		try {
			zumClient.println("Geben Sie DATE oder TIME ein");
			String wunsch = vomClient.readLine();
			// v. Client empfangen
			Date jetzt = new Date();
			// Zeitpunkt bestimmen
			// vom Client empfangenes Kommando ausfuehren
			if (wunsch.equalsIgnoreCase("date"))
				zumClient.println(date.format(jetzt));
			else if (wunsch.equalsIgnoreCase("time"))
				zumClient.println(time.format(jetzt));
			else
				zumClient.println(wunsch + " ist als Kommando unzulaessig!");
			s.close();
			// Socket (und damit auch Stroeme) schliessen
		} catch (IOException e) {
			System.out.println("IO-Error");
		}
		System.out.println("Protokoll beendet");
	}
}

class DNSAnfrage {
	public static void main(String[] args) {
		try {
			InetAddress ip = InetAddress.getByName(args[0]);
			System.out.println("Angefragter Name: " + args[0]);
			System.out.println("IP-Adresse: " + ip.getHostAddress());
			System.out.println("Host-Name: " + ip.getHostName());
		} catch (ArrayIndexOutOfBoundsException aex) {
			System.out.println("Aufruf: java DNSAnfrage <hostname>");
		} catch (UnknownHostException uex) {
			System.out.println("Kein DNS-Eintrag fuer " + args[0]);
		}
	}
}
