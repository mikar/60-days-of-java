package grundkurs_java;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class Chap19 {

	public static void main(String[] args) {
		// Create.main(args);
		// WriteToFile.main(args);
		// BufferedWriteToFile.main(args);
		// ZahlenSumme.main(args);
		// try {
		// PrintWriting.main(args);
		// } catch (IOException e) {
		// }
		// InTools.main(args);
		// ObjectWrite.main(args);
		// ObjectRead.main(args);
		// try {
		// CopyFile.usingFileStreams(new File("MeineDaten.dat"), new File(
		// "MeineDaten.test"));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// Vokalumwandlung.main(new String[] { "example.txt", "a" });
		HexaStream.main(args);
		try {
			BinOut.main(new String[] { "bin/grundkurs_java/Chap19.class" });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class BinOut {
	// 19.4 official solution
	public static void main(String[] args) throws IOException {
		PrintWriter out = new PrintWriter(System.out, true);
		if (args.length == 0) {
			out.println("Aufruf:  java BinOut <Dateiname>");
			return;
		}

		final int BREITE = 16; // Anzahl Bytes je Ausgabe-Zeile
		FileInputStream in = new FileInputStream(args[0]); // Eingabedatei
															// (Bytes)
		StringBuffer buf = new StringBuffer(); // zum Sammeln der darstellb.
												// Zeichen
		int i, c = 0;

		while ((i = in.read()) != -1) { // lies Bytes bis Dateiende erreicht ist
			c++; // erhöhe Zähler für gelesene Bytes
			if (i <= 15) // falls der gelesene Byte-Wert < 15
				out.print('0'); // gib eine Null aus
			out.print(Integer.toHexString(i)); // gib den Bytewert hexadezimal
												// aus
			out.print(" "); // gib 1 Leerzeichen aus
			if (i >= 32 && i < 127) // falls das Byte i als char darstellbar
				buf.append((char) i); // hänge das entspr. Zeichen an buf an
			else
				// andernfalls
				buf.append('.'); // hänge einen . an buf an
			if (c == BREITE) { // wenn 16 Bytes bearbeitet sind
				out.println("\t" + buf); // gib den Inhalt von buf aus
				c = 0; // setze Zähler auf Null zurück
				buf = new StringBuffer(); // erzeuge neues StringBuffer-Objekt
			}
		}
		// restliche Bytes, die keine volle Zeile mehr ergeben, bearbeiten
		int l = buf.length(); // bestimme die Länge des StringBuffers
		if (l > 0) { // falls noch Zeichen im Buffer
			for (int k = 0; k < BREITE - l; k++) {
				out.print("   "); // fülle Hex-Ausgabe mit Leerzeichen
				buf.append(' '); // hänge Leerzeichen an buf an
			}
			out.println("\t" + buf); // gib den Inhalt von Buffer aus
		}
	}
}

class HexaStream {
	// 19.4
	public static void main(String[] args) {
		String hexString, hexOutput = "", charOutput = "";
		int hexInteger, count = 0;
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(
					"bin/grundkurs_java/Chap19.class"));
			while (true) {
				count++;
				hexInteger = fis.read();
				if (hexInteger == -1) {
					System.out.println(hexOutput + "\t" + charOutput);
					break;
				}
				hexString = Integer.toHexString(hexInteger);
				if (hexInteger < 16)
					hexString = "0" + hexString;

				hexOutput += padRight(hexString, 3);
				if (hexInteger > 31 && hexInteger < 127) {
					charOutput += (char) hexInteger;
				} else {
					charOutput += ".";
				}
				if (count % 16 == 0) {
					System.out.println(hexOutput + "\t" + charOutput);
					hexOutput = "";
					charOutput = "";
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
}

class Vokalumwandlung {
	// 19.3
	public static void main(String[] args) {
		FileInputStream fis = null;
		BufferedReader br = null;
		LineNumberReader lnr = null;
		String dest;
		String[] nameTokens = args[0].split("\\.(?=[^\\.]+$)");
		dest = nameTokens[0] + "_ausgabe" + nameTokens[1];

		// List<String> output = new ArrayList<String>();
		String[] output = null;

		try {
			fis = new FileInputStream(args[0]);
			br = new BufferedReader(new InputStreamReader(fis));
			lnr = new LineNumberReader(new FileReader(new File(args[0])));

			String replaceLC = args[1].toLowerCase();
			String replaceUC = args[1].toUpperCase();
			String newLine;
			String line;

			lnr.skip(Long.MAX_VALUE);
			output = new String[lnr.getLineNumber()];
			for (int i = 0; i < lnr.getLineNumber(); i++) {
				line = br.readLine();
				newLine = line.replaceAll("Ae|Oe|Ue", replaceUC + replaceLC);
				newLine = newLine.replaceAll("ae|oe|ue", replaceLC + replaceLC);
				newLine = newLine.replaceAll("[aeiou]", replaceLC);
				newLine = newLine.replaceAll("[AEIOU]", replaceUC);
				// output.add(newLine);
				output[i] = newLine;
				System.out.println(newLine);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			try {
				lnr.close();
				br.close();
				fis.close();
			} catch (IOException e) {
			}
		}
		for (int i = 0; i < output.length; i++) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(dest,
					true))) {
				String s;
				s = output[i];
				bw.write(s);
				bw.flush();
				bw.newLine();
			} catch (IOException ex) {
			}
		}
	}
}

class InOutTools {
	// 19.2
	// Gepufferter Eingabestrom ueber den Standardeingabstrom System.in
	public static BufferedReader in = new BufferedReader(new InputStreamReader(
			System.in));

	// Methode zum Einlesen von double-Werten
	public static double readDouble() {
		double erg = 0;
		try {
			erg = Double.parseDouble(in.readLine());
		} catch (Exception e) {
			System.out.println(e);
		}
		return erg;
	}

	// Methode zum Einlesen von double-Werten mit Prompt
	public static double readDouble(String prompt) {
		System.out.print(prompt);
		System.out.flush();
		double erg = 0;
		try {
			erg = Double.parseDouble(in.readLine());
		} catch (Exception e) {
			System.out.println(e);
		}
		return erg;
	}

	// main-Methode
	public static void main(String[] args) {
		System.out.print("double-Wert eingeben: d = ");
		double d = readDouble();
		System.out.println("d = " + d + " wurde eingelesen");

		double e = readDouble("double-Wert eingeben: e = ");
		System.out.println("e = " + e + " wurde eingelesen");
	}
}

class CopyFile {
	// 19.1
	private static void usingApacheCommonsIO(File source, File dest)
			throws IOException {
		FileUtils.copyFile(source, dest);
	}

	private static void usingFileChannels(File source, File dest)
			throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}

	public static void usingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}

	public static void usingFiles(File source, File dest) {
		try {
			Files.copy(source.toPath(), dest.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// official solution
		try {
			File inputFile = new File(args[0]); // Eingabedatei
			File outputFile = new File(args[1]); // Ausgabedatei
			FileReader in = new FileReader(inputFile); // Eingabestrom
			FileWriter out = new FileWriter(outputFile); // Ausgabestrom
			int c;
			while ((c = in.read()) != -1)
				// Einlesen mit Test auf Strom-Ende
				out.write(c); // Ausgeben in Datei
			in.close();
			out.close();
			System.out.println(args[0] + " kopiert nach " + args[1]);
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf:  java Kopiere <Quelle> <Ziel>");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

class ObjectRead {
	public static void main(String[] summand) {
		try {
			// Dateiname fuer die Speicherung festlegen
			String dateiname = "MeineDaten.dat";
			// Datenstrom zum Lesen aus der Datei erzeugen
			FileInputStream datEin = new FileInputStream(dateiname);
			// Objektstrom darueberlegen
			ObjectInputStream oEin = new ObjectInputStream(datEin);
			// Datensaetze aus der Datei lesen und deren Datensatzfelder
			// zur Kontrolle auf den Bildschirm ausgeben
			int anzahl = oEin.readInt();
			System.out.println("Die Datei " + dateiname + " enthaelt " + anzahl
					+ " Datensaetze");
			for (int i = 1; i <= anzahl; i++) {
				Datensatz gelesen = (Datensatz) oEin.readObject();
				System.out.println(gelesen);
			}
			// Dateistrom schliessen
			oEin.close();
		} catch (Exception e) {
			System.out.println("Fehler beim Lesen: " + e);
		}
	}
}

class ObjectWrite {
	public static void main(String[] summand) {
		try {
			// Dateiname fuer die Speicherung festlegen
			String dateiname = "MeineDaten.dat";
			// Datenstrom zum Schreiben in die Datei erzeugen
			FileOutputStream datAus = new FileOutputStream(dateiname);
			// Objektstrom darueber legen
			ObjectOutputStream oAus = new ObjectOutputStream(datAus);
			// Testdatensaetze erzeugen
			int anzahl = 2; // Anzahl der Datensaetze
			Datensatz a = new Datensatz(99, 56, "Coca Cola");
			Datensatz b = new Datensatz(111, 1234.79, "Fahrrad");
			// Datensaetze in die Datei schreiben
			oAus.writeInt(anzahl);
			// Anzahl der Datensaetze
			oAus.writeObject(a);
			// Datensatz 1
			oAus.writeObject(b);
			// Datensatz 2
			// Dateistrom schliessen
			oAus.close();
			System.out.println(anzahl + " Datensaetze in die Datei "
					+ dateiname + " geschrieben");
			System.out.println(a);
			System.out.println(b);
		} catch (Exception e) {
			System.out.println("Fehler beim Schreiben: " + e);
		}
	}
}

class Datensatz implements Serializable {
	public int nr;
	// Nummer des Datensatzes
	public double wert; // Wert des Datensatzes
	public String kom; // Kommentar

	public Datensatz(int nr, double wert, String kom) { // Konstruktor
		this.nr = nr;
		this.wert = wert;
		this.kom = kom;
	}

	public String toString() {
		// Erzeugung einer String-Darstellung
		return "Nr. " + nr + ": " + wert + " (" + kom + ")";
	}
}

class DataWriteAndRead {
	// Speichert elementare Werte in einer Datei und liest sie wieder ein
	public static void main(String[] args) {
		try {
			File datei = new File("binaer.dat");
			FileOutputStream out = new FileOutputStream(datei);
			DataOutputStream dout = new DataOutputStream(out);
			dout.writeInt(1);
			dout.writeDouble(2.3);
			dout.writeChar('a');
			dout.writeBoolean(true);
			dout.close();
			FileInputStream in = new FileInputStream(datei);
			DataInputStream din = new DataInputStream(in);
			System.out.println("int: " + din.readInt());
			System.out.println("double: " + din.readDouble());
			System.out.println("char: " + din.readChar());
			System.out.println("boolean: " + din.readBoolean());
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

class Eingaben {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int i;
		double d;
		boolean b;
		System.out.print("i = ");
		i = in.nextInt();
		System.out.print("d = ");
		d = in.nextDouble();
		System.out.print("b = ");
		b = in.nextBoolean();
		System.out.println("i = " + i);
		System.out.println("d = " + d);
		System.out.println("b = " + b);
	}
}

class InTools {
	// Gepufferter Eingabestrom ueber den Standardeingabestrom System.in
	public static BufferedReader in = new BufferedReader(new InputStreamReader(
			System.in));

	// Methode zum Einlesen von double-Werten
	public static double readDouble() {
		double erg = 0;
		try {
			erg = Double.parseDouble(in.readLine());
		} catch (Exception e) {
			System.out.println(e);
		}
		return erg;
	}

	// main-Methode
	public static void main(String[] args) {
		System.out.print("double-Wert eingeben: d = ");
		double d = readDouble();
		System.out.println("d = " + d + " wurde eingelesen");
	}
}

class PrintWriting {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter p = new PrintWriter(System.out);
		PrintWriter pf = new PrintWriter(System.out, true);
		pf.print(1);
		pf.print('a');
		in.readLine();
		// Enter-Taste druecken
		pf.println();
		in.readLine();
		// Enter-Taste druecken
		pf.println("pf ist fertig!");
		p.print(3.2);
		p.print(true);
		p.println();
		p.println("p ist fertig!");
		in.readLine();
		// Enter-Taste druecken
		p.flush();
	}
}

class ZahlenSumme {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer st = new StreamTokenizer(br);
		System.out.println("Addiere alle Zahlen in einer Zeichenfolge");
		System.out.println("(Eingabe mit STOP abschliessen)");
		System.out.println();
		StringBuffer woerter = new StringBuffer(); // zum Woerter sammeln
		double sum = 0.0;
		// zum Zahlen summieren
		int tokenType;
		// Typ des Tokens
		boolean stop = false;
		// Flag fuer Schleife
		try {
			do {
				switch (tokenType = st.nextToken()) { // naechstes Token
				case StreamTokenizer.TT_NUMBER:
					// ist Zahl
					sum += st.nval;
					// summiere Wert
					break;
				case StreamTokenizer.TT_WORD:
					// ist Wort
					if (!(stop = st.sval.equals("STOP")))
						// falls nicht STOP
						woerter.append(st.sval);
					// Wort anhaengen
					break;
				}
			} while (!stop);
			System.out.println();
			System.out.println("Summe aller Zahlen: " + sum);
			System.out.println("Text: " + woerter.toString());
		} catch (IOException e) {
			System.out.println(e);
		}
		;
	}
}

class BufferedWriteToFile {
	// Liest alle Zeichen aus br und schreibt sie in bw
	public static void br2bw(BufferedReader br, BufferedWriter bw)
			throws IOException {
		String z;
		// Zeile
		while ((z = br.readLine()) != null) { // lesen, Stromende pruefen,
			bw.write(z);
			// ausgeben und
			bw.newLine();
			// Zeilenwechsel ausgeben
		}
		br.close();
		bw.close();
	}

	// Liest Zeilen von der Tastatur und speichert sie in einer Datei
	public static void main(String[] args) {
		try {
			File datei = new File(args[0]);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			BufferedWriter out = new BufferedWriter(new FileWriter(datei));
			System.out.println("Geben Sie jetzt den Text ein.");
			System.out.println("(Ende/Speichern mit Ctrl-Z bzw. Strg-Z)");
			System.out.println();
			br2bw(in, out);
			in = new BufferedReader(new FileReader(datei));
			out = new BufferedWriter(new OutputStreamWriter(System.out));
			System.out.println();
			System.out.println("Der in " + args[0] + " gespeicherte Text:");
			System.out.println();
			br2bw(in, out);
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java BufferedWriteToFile <Datei>");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

class Create {
	public static void main(String[] args) {
		try {
			File f = new File(args[0]);
			// Verzeichnis
			File g = new File(args[0] + "/" + args[1]);
			// Datei
			File h = new File(args[0] + "/" + args[1] + ".txt"); // Datei
			if (f.exists()) {
				System.out.println("Verzeichnis oder Datei " + args[0]
						+ " existiert bereits");
				return;
			}
			f.mkdir();
			// Verzeichnis anlegen
			g.createNewFile();
			// Datei anlegen
			h.createNewFile();
			// Datei anlegen
			String[] dateien = f.list(); // Verzeichniseintraege aufzaehlen
			System.out.println("Dateien im Verzeichnis " + args[0] + ":");
			for (int i = 0; i < dateien.length; i++)
				System.out.println(dateien[i]);
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java Create <Verzeichnis> <Datei>");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class WriteToFile {
	// Liest alle Zeichen aus r und schreibt sie in w
	public static void r2w(Reader r, Writer w) throws IOException {
		int c;
		// Zeichen
		while ((c = r.read()) != -1)
			// lesen und auf Strom-Ende testen
			w.write(c);
		// ausgeben
		r.close();
		w.close();
	}

	// Liest Zeichen von der Tastatur und speichert sie in einer Datei
	public static void main(String[] args) {
		try {
			File datei = new File(args[0]);
			Reader in = new InputStreamReader(System.in);
			Writer out = new FileWriter(datei);
			System.out.println("Geben Sie jetzt den Text ein.");
			System.out.println("(Ende/Speichern mit Ctrl-Z bzw. Strg-Z)");
			System.out.println();
			r2w(in, out);
			in = new FileReader(datei);
			out = new OutputStreamWriter(System.out);
			System.out.println();
			System.out.println("Der in " + args[0] + " gespeicherte Text:");
			System.out.println();
			r2w(in, out);
		} catch (ArrayIndexOutOfBoundsException ae) {
			System.out.println("Aufruf: java WriteToFile <Datei>");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}