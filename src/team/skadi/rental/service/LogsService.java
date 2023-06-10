package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Logs;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.impl.LogsDaoImp;

public class LogsService {
	
	private static LogsDaoImp ldi = new LogsDaoImp();

	/**
	 * 添加借充电宝日志
	 * 
	 * @param user  用户
	 * @param power 充电宝
	 */
	public static void addBorrowLog(User user, Power power) {
		ldi.addLog(user, power, "借充电宝(型号：" + power.getId() + ")");
	}

	/**
	 * 添加归还充电宝日志
	 * 
	 * @param log 日志
	 */
	public static void addReturnLog(Logs log) {
		log.setEndDate(System.currentTimeMillis());
		int time = getTime(log);
		log.setContext(log.getContext() + "时长：" + time + "。花费：" + (time * 1.5f) + "元。");
		ldi.finishLog(log);
	}

	/**
	 * 查询指定用户的所有的日志
	 * 
	 * @param user 用户
	 * @return
	 */
	public static List<Logs> queryLogs(User user) {
		return ldi.queryLogs(user);
	}

	/**
	 * 获取没有归还充电宝的日志
	 * 
	 * @param user  用户
	 * @param power 充电宝
	 * @return 日志。null：未借
	 */
	public static Logs getLog(User user, Power power) {
		return ldi.getLog(user, power);
	}

	/**
	 * 获得log中的时间间隔
	 * 
	 * @param log 需要获取时间间隔的log对象
	 * @return 时间间隔。单位（小时）
	 */
	public static int getTime(Logs log) {
		double length = log.getEndDate() - log.getStartDate();
		length /= 3600000;
		return (int) Math.round(length);
	}
}
