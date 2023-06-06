package team.skadi.rental;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import team.skadi.rental.ui.MainFrame;

public class Main {
	
	public static final Font DEFAILT_FONT = new Font("微软雅黑", Font.PLAIN, 18);
	public static final Font TITLE_FONT = new Font("微软雅黑", Font.PLAIN, 30);
	
	public static void main(String[] args) {
//		设置全局字体
		FontUIResource fontResource = new FontUIResource(DEFAILT_FONT);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontResource);
			}
		}
		SwingUtilities.invokeLater(()->{
			new MainFrame();
		});
	}
}
