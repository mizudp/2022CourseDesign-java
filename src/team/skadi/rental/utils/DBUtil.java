package team.skadi.rental.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBUtil {

	public final static String DEVICE;
	public final static String DB_URL;
	public final static String DB_USER_NAME;
	public final static String DB_PASSWORD;

	static {
		Properties properties = new Properties();
		try {
			File file = new File("DB.cfg");
			if (file.exists()) {
				properties.load(new FileInputStream(file));
			} else {
				JOptionPane.showMessageDialog(null, "未检测到数据库配置文件，即将进行数据库配置文件初始化。");
				String driver = JOptionPane.showInputDialog("请输入驱动类(device):", "com.mysql.jdbc.Driver");
				properties.setProperty("DRIVER", driver);
				String url = JOptionPane.showInputDialog("请输入数据库链接(url):",
						"jdbc:mysql://localhost:3306/powerbank?useSSL=false");
				properties.setProperty("DB_URL", url);
				String userName = JOptionPane.showInputDialog("请输入数据库用户(user):", "root");
				properties.setProperty("DB_USER_NAME", userName);
				String password = JOptionPane.showInputDialog("请输入" + userName + "的密码(password):", "123456");
				properties.setProperty("DB_PASSWORD", password);
				properties.store(new FileOutputStream(file), "init data base config");
				JOptionPane.showMessageDialog(null, "初始化成功！如果需要更改，请到当前项目根目录对「DB.cfg」文件进行修改！");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		DEVICE = properties.getProperty("DRIVER");
		DB_URL = properties.getProperty("DB_URL");
		DB_USER_NAME = properties.getProperty("DB_USER_NAME");
		DB_PASSWORD = properties.getProperty("DB_PASSWORD");
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DEVICE);
			connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void closeAll(Connection connection, PreparedStatement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
