package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.Main;
import team.skadi.rental.bean.Log;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.impl.LogDaoImp;
import team.skadi.rental.utils.DateUtil;

public class LogService {

	private static LogDaoImp ldi = new LogDaoImp();

	/**
	 * 添加借充电宝日志
	 * 
	 * @param user  用户
	 * @param power 充电宝
	 */
	public static void addBorrowLog(User user, Power power) {
		power.setStatus(Power.BORROWED);
		PowerService.getInstance().modify(power);
		ldi.addLog(user, power, "借充电宝(型号：" + power.getId() + ")");
	}

	/**
	 * 添加归还充电宝日志
	 * 
	 * @param log 日志
	 * @return 时间间隔（小时）
	 */
	public static int addReturnLog(Log log) {
		log.setEndDate(System.currentTimeMillis());
		int time = getTime(log);
		if (time == 0) {
			log.setContext(log.getContext() + "时长：" + time + "。花费：" + Main.getCost(time + 1) + "元。(不满1小时归还扣费)");
		} else {
			log.setContext(log.getContext() + "时长：" + time + "。花费：" + Main.getCost(time) + "元。");
		}
		ldi.finishLog(log);
		return time;
	}

	/**
	 * 查询指定用户的所有的日志
	 * 
	 * @param user 用户
	 * @return
	 */
	public static List<Log> queryLogs(User user) {
		return ldi.queryLogs(user, null);
	}

	public static List<Log> queryLogs(Power power) {
		return ldi.queryLogs(null, power);
	}

	public static List<Log> queryLogs(User user, Power power) {
		return ldi.queryLogs(user, power);
	}

	/**
	 * 获取没有归还充电宝的日志
	 * 
	 * @param user 用户
	 * @return 日志。null：未借
	 */
	public static Log getLog(User user) {
		return ldi.getLog(user);
	}

	/**
	 * 获取所有日志
	 * 
	 * @return 所有日志
	 */
	public static List<Log> getAllLogs() {
		return ldi.getAllLogs();
	}

	/**
	 * 获得log中的时间间隔
	 * 
	 * @param log 需要获取时间间隔的log对象
	 * @return 时间间隔。单位（小时）
	 */
	public static int getTime(Log log) {
		return DateUtil.getTimeSpan(log.getStartDate(), log.getEndDate());
	}
}
