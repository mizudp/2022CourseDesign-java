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
}
