package grundkurs_java;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OOPAdvancedTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCurrency() {
		CurrencyCalc calc = new CurrencyCalc(123.4);
		assertEquals(63.09, calc.inEuro(), 0.1);
		assertEquals(413.8, calc.inFranc(), 0.1); // a
		assertEquals(122165.89274118916, calc.inLire(), 0.0000000001);
	}

	@Test
	public void testMetallPlatte() {
		GelochtePlatte gp = new GelochtePlatte(3, 5, 2);
		assertEquals("breite", 5, gp.breite, 0.1);
		assertEquals("länge", 3, gp.laenge, 0.1);
		assertEquals("flaeche ohne löcher", 15, gp.flaeche(), 0.1);
		assertEquals("anzahl löcher", gp.getAnzahlLoecher(), 0);
		assertEquals("lochbreite", 2.5, gp.getLochBreite(), 0.1);
		assertEquals("lochlänge", 1.5, gp.getLochLaenge(), 0.1);
		// gp.neuesLochStanzen();
		// assertEquals(1, gp.getAnzahlLoecher());
		// gp.neuesLochStanzen();
		// assertEquals(2, gp.getAnzahlLoecher());
		// gp.neuesLochStanzen();
		// assertEquals(2, gp.getAnzahlLoecher());
	}

	@Test
	public void testSpielFigur() {
		SpielFigur s = new SpielFigur('H', 8, "schwarz");
		DameFigur d = new DameFigur('H', 8, "weiß");
		assertTrue(d.trifft(s));
		assertEquals("weiße Dame auf Feld H8", d.toString());
	}
}
