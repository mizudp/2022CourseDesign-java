package team.skadi.rental.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

	/**
	 * 获取时间间隔，结果四舍五入
	 * 
	 * @param start 起始时间
	 * @param end   结束时间
	 * @return 时间间隔，单位：小时
	 */
	public static int getTimeSpan(long start, long end) {
		double time = end - start;
		time /= 3600000;
		return (int) Math.round(time);
	}
}
