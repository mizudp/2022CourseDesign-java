package team.skadi.rental.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 获得当前时间格式为 yyyy-MM-dd hh:mm:ss
	 * 
	 * @return string
	 */
	public static String getDateNow() {
		return sdf.format(new Date());
	}

	/**
	 * @param millisec 1970年1月1日起的毫秒数
	 * @return 以yyyy-MM-dd hh:mm:ss显示的时间
	 */
	public static String format(long millisec) {
		return sdf.format(new Date(millisec));
	}
}
