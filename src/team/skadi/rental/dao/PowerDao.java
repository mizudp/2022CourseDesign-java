package team.skadi.rental.dao;

import java.util.List;

import team.skadi.rental.bean.Power;

public interface PowerDao {

	/**
	 * 根据充电宝的状态获取相应状态的充电宝列表
	 * 
	 * @param status 充电宝状态
	 * @return 充电宝列表
	 */
	List<Power> findPowersByStatus(int status);

	/**
	 * 查找大于大于gtLeft电量的充电宝
	 * 
	 * @param gtLeft 剩余电量
	 * @return 充电宝列表
	 */
	List<Power> findPowersByPowerLeft(int gtLeft);

	/**
	 * 根据指定的id查找充电宝
	 * 
	 * @param powerId 充电宝id
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
