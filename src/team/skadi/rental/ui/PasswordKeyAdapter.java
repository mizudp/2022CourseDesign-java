package team.skadi.rental.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class PasswordKeyAdapter extends KeyAdapter {
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			onEnterPressed(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char keyChar = e.getKeyChar();
		if ((keyChar < '0' || keyChar > '9') && (keyChar < 'a' || keyChar > 'z') && (keyChar < 'A' || keyChar > 'Z')) {
			e.consume();
		}
	}
	
	protected void onEnterPressed(KeyEvent e) {
		
	}
}
