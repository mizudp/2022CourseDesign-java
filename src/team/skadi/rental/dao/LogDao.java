package team.skadi.rental.dao;

import java.util.List;

import team.skadi.rental.bean.Log;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;

public interface LogDao {
	/**
	 * 添加一条用户使用记录，记录开始世间
	 * 
	 * @param user    用户
	 * @param power   要租借充电宝
	 * @param content 日志内容
	 */
	void addLog(User user, Power power, String content);

	/**
	 * 归还充电宝时，记录结束时间
	 * 
	 * @param user  用户
	 * @param power 需要归还的充电宝
	 */
	void finishLog(Log log);

	/**
	 * 获得指定用户和指定充电宝并且没有结束时间的记录
	 * 
	 * @param user 用户
	 * @return 日志*1。null；没有
	 */
	Log getLog(User user);

	/**
	 * 获取所有日志
	 * 
	 * @return 所有日志
	 */
	List<Log> getAllLogs();


	/**
	 * 通过移动用户和移动电源查找日志
	 * 
	 * @param user  用户
	 * @param power 移动电源
	 * @return 有关于这个用户和移动电源的日志
	 */
	List<Log> queryLogs(User user, Power power);

}
