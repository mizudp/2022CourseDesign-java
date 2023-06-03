package team.skadi.rental.dao;

import java.util.List;

import team.skadi.rental.bean.Logs;
import team.skadi.rental.bean.User;

public interface LogsDao {
	/**
	 * 添加一条用户使用记录
	 * 
	 * @param user    用户
	 * @param content 内容
	 */
	void addLog(User user, String content);

	/**
	 * 根据指定的用户id获取所有记录
	 * 
	 * @param userId 用户id
	 * @return 用户记录列表
	 */
	List<Logs> queryLogs(String userId);

}
