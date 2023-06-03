package team.skadi.rental.dao;

import team.skadi.rental.bean.Manger;

public interface MangerDao {
	/**
	 * 根据指定的id来获取数据库中的管理员
	 * 
	 * @param id
	 * @return
	 */
	Manger findMangerById(String id);
}
