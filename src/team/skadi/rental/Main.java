package team.skadi.rental;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import team.skadi.rental.ui.MainFrame;

public final class Main {

	public static final Font DEFAILT_FONT = new Font("微软雅黑", Font.PLAIN, 18);
	public static final Font MIDDLE_FONT = new Font("微软雅黑", Font.PLAIN, 28);
	public static final Font TITLE_FONT = new Font("微软雅黑", Font.PLAIN, 42);

	public static final double CHARGING_STANDARD = 1.5;

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
	public static final double getCost(int timeSpan) {
		return timeSpan * CHARGING_STANDARD;
	}

	/**
	 * 获取随机数
	 * 
	 * @param min 最小值
	 * @param max 最大值
	 * @return 最小值到最大值之间的随机数 [min,max]
	 */
	public static final int getRandom(int min, int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}
}
