package team.skadi.rental.ui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ImageButton extends JButton implements MouseListener {

	private ImageIcon image;

	public ImageButton(String text, String res, int width, int height) {
		this(text, res);
		image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
	}

	public ImageButton(String text, String res) {
		image = new ImageIcon(res);
		setIcon(image);
		setText(text);
		setBorderPainted(false);
		setBackground(new Color(204, 227, 255));
		setContentAreaFilled(false);
		setFocusPainted(false);
		addMouseListener(this);
	}

	public void setTextPosition(int horizontal, int vertical) {
		setHorizontalTextPosition(horizontal);
		setVerticalTextPosition(vertical);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		setBackground(color);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setContentAreaFilled(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setContentAreaFilled(false);
	}
}
