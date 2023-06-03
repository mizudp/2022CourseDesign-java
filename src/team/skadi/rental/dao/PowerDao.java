package team.skadi.rental.dao;

import team.skadi.rental.bean.Power;

public interface PowerDao {
	/**
	 * 根据指定的id查找充电宝
	 * 
	 * @param powerId
	 * @return 充电宝*1， null 没有该id的充电宝
	 */
	Power findPowerById(String powerId);

	/**
	 * 新建一个空的充电宝记录
	 */
	void createNewPower();

	/**
	 * 用于向数据库添加新的充电宝
	 * 
	 * @param powerId
	 * @param serialnum
	 */
	void addNewPower(String powerId, int serialnum);

	/**
	 * 更改数据库中指定充电宝的所有信息
	 * 
	 * @param power
	 */
	void updatePower(Power power);

	/**
	 * 获取空充电宝的序列号，用于生成id
	 * 
	 * @return
	 */
	int getSerialnum();
}
