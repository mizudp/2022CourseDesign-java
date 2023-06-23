package team.skadi.rental;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import team.skadi.rental.ui.MainFrame;

public class Main {

	public static final Font DEFAILT_FONT = new Font("微软雅黑", Font.PLAIN, 18);
	public static final Font MIDDLE_FONT = new Font("微软雅黑", Font.PLAIN, 28);
	public static final Font TITLE_FONT = new Font("微软雅黑", Font.PLAIN, 42);

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
		SwingUtilities.invokeLater(() -> {
			new MainFrame();
		});
	}

	/**
	 * 获取花费金额（元）
	 * 
	 * @param timeSpan 时间间隔（小时）
	 * @return 花费金额
	 */
	public static double getCost(int timeSpan) {
		return timeSpan * 1.5;
	}
}
