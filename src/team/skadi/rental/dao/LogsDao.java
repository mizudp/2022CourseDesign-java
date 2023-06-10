package team.skadi.rental.dao;

import java.util.List;

import team.skadi.rental.bean.Logs;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;

public interface LogsDao {
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
	void finishLog(Logs log);

	/**
	 * 获得指定用户和指定充电宝并且没有结束时间的记录
	 * 
	 * @param user  用户
	 * @param power 充电宝
	 * @return 开始时间(start Time)
	 */
	Logs getLog(User user, Power power);

	/**
	 * 根据指定的用户id获取所有记录
	 * 
	 * @param user 用户
	 * @return 用户记录列表
	 */
	List<Logs> queryLogs(User user);

}
