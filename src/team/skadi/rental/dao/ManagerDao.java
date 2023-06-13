package team.skadi.rental.dao;

import team.skadi.rental.bean.Manager;

public interface ManagerDao {
	/**
	 * 根据指定的id来获取数据库中的管理员
	 * 
	 * @param id
	 * @return
	 */
	Manager findMangerById(String id);
}
