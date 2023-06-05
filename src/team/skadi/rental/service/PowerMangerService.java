package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Power;
import team.skadi.rental.dao.impl.PowerDaoImp;

public class PowerMangerService {

	private PowerDaoImp pdi;
	private static PowerMangerService instance = new PowerMangerService();

	/**
	 * 移除指定的充电宝
	 * 
	 * @param power 要移除的充电宝
	 */
	public void remove(Power power) {

	}

	/**
	 * 添加指定的充电宝
	 * 
	 * @param power 要添加的充电宝
	 */
	public void add(Power power) {

	}

	/**
	 * 根据状态获取充电宝
	 * 
	 * @param status 充电宝状态
	 * @return 满足条件的充电宝
	 */
	public List<Power> getPowers(int status) {
		return null;
	}

	/**
	 * 获取大于指定电量的充电宝
	 * 
	 * @param left 剩余电量
	 * @return 满足条件的充电宝
	 */
	public List<Power> getPowers(double left) {
		return null;
	}

	// 单例模式
	private PowerMangerService() {
		pdi = new PowerDaoImp();
	}

	public static PowerMangerService getInstance() {
		return instance;
	}
}
