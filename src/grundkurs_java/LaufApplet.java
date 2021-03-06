package grundkurs_java;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class LaufApplet extends JApplet {
	// 18.3

	private Container c;
	private JComboBox fontBox;
	private JCheckBox letterCheck;
	private ColorRunButton runButton1;
	private ColorRunButton runButton2;

	public void init() {
		c = getContentPane();

		runButton1 = new ColorRunButton();
		runButton1.setText("3");
		runButton1.setActionCommand("runButton1");
		runButton1.setPreferredSize(new Dimension(80, 80));
		runButton1.setBackground(Color.WHITE);
		runButton1.addActionListener(new RunButtonListener());
		runButton2 = new ColorRunButton();
		runButton2.setText("H");
		runButton2.setActionCommand("runButton2");
		runButton2.setPreferredSize(new Dimension(80, 80));
		runButton2.setBackground(Color.WHITE);
		runButton2.addActionListener(new RunButtonListener());

		fontBox = new JComboBox();
		fontBox.addItem("schwarze Schrift");
		fontBox.addItem("graue Schrift");
		fontBox.addActionListener(new FontListener());

		letterCheck = new JCheckBox("Buchstaben");
		letterCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
		letterCheck.addActionListener(new LetterListener());

		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		// c.setLayout(new BorderLayout());
		JPanel buttonPane = new JPanel();
		buttonPane.add(runButton1);
		buttonPane.add(runButton2);
		c.add(buttonPane);
		// c.add(buttonPane, BorderLayout.SOUTH);
		// c.add(runButton1);
		// c.add(runButton2);
		c.add(fontBox);
		c.add(letterCheck);
		setSize(170, 130);
	}

	class RunButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "runButton1") {
				runButton1.change();
			} else if (e.getActionCommand() == "runButton2") {
				runButton2.change();
			}
		}
	}

	class FontListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (fontBox.getSelectedIndex() == 0) {
				runButton1.setForeground(Color.BLACK);
				runButton2.setForeground(Color.BLACK);
			} else if (fontBox.getSelectedIndex() == 1) {
				runButton1.setForeground(Color.GRAY);
				runButton2.setForeground(Color.GRAY);
			}
		}

	}

	class LetterListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (letterCheck.isSelected())
				ColorRunButton.useLetters = true;
			else
				ColorRunButton.useLetters = false;
		}

	}
}

class ColorRunButton extends JButton implements Runnable {

	public static boolean useLetters = true;
	private boolean running;

	public void start() {
		running = true;
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}

	public void run() {
		while (running) {
			char ordinal;
			if (useLetters) {
				ordinal = (char) randInt(65, 90);
			} else {
				ordinal = (char) randInt(48, 57);
			}
			setText(String.valueOf(ordinal));
		}
	}

	public void change() {
		if (running)
			stop();
		else
			start();
	}

	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

}