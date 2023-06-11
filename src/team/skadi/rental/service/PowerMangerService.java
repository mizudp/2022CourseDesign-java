package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Power;
import team.skadi.rental.dao.impl.PowerDaoImp;

public class PowerMangerService {

	private PowerDaoImp pdi;
	private static final PowerMangerService instance = new PowerMangerService();

	/**
	 * 移除指定的充电宝
	 * 
	 * @param power 要移除的充电宝
	 */
	protected void remove(Power power) {
		power.reset();
		pdi.updatePower(power);
	}

	/**
	 * 添加指定的充电宝
	 * 
	 * @param left 电量
	 * @return 添加的充电宝
	 */
	protected Power add(double left) {
		pdi.createNewPower();
		int serialnum = pdi.getSerialnum();
		String powerId = String.format("%06d", serialnum);
		pdi.addNewPower(powerId, serialnum);
		return new Power(powerId, serialnum, left, Power.AVAILABLE);
	}

	/**
	 * 修改指定的充电宝
	 * 
	 * @param power 要修改的充电宝
	 */
	protected void modifyPower(Power power) {
		pdi.updatePower(power);
	}

	/**
	 * 根据状态获取充电宝
	 * 
	 * @param status 充电宝状态
	 * @return 满足条件的充电宝
	 */
	public List<Power> getPowersByStatus(int status) {
		return pdi.findPowersByStatus(status);
	}

	/**
	 * 获取大于指定电量的充电宝
	 * 
	 * @param left 剩余电量
	 * @return 满足条件的充电宝
	 */
	public List<Power> getPowersByPowerLeft(double left) {
		return pdi.findPowersByPowerLeft(left);
	}

	// 单例模式
	private PowerMangerService() {
		pdi = new PowerDaoImp();
	}

	public static PowerMangerService getInstance() {
		return instance;
	}
}
