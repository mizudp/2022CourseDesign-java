package team.skadi.rental.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumberOnly extends KeyAdapter {
	@Override
	public void keyTyped(KeyEvent e) {
		char keyChar = e.getKeyChar();
		if ((keyChar < '0' || keyChar > '9') && keyChar != '.') {
			e.consume();
		}
	}
}
