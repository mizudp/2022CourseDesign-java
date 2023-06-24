package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.Main;
import team.skadi.rental.bean.Power;
import team.skadi.rental.dao.impl.PowerDaoImp;

public class PowerService {

	private PowerDaoImp pdi;
	private static final PowerService instance = new PowerService();

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
	 * @return 添加的充电宝
	 */
	protected Power add(Power power) {
		int serialnum = pdi.getSerialnum();
		if (serialnum == 0) {
			pdi.createNewPower();
			serialnum = pdi.getSerialnum();
		}
		String powerId = String.format("%06d", serialnum);
		pdi.addNewPower(powerId, serialnum);
		power.setId(powerId);
		power.setSerialnum(serialnum);
		power.setLeft(power.getLeft());
		pdi.updatePower(power);
		return power;
	}

	/**
	 * 修改指定的充电宝
	 * 
	 * @param power 要修改的充电宝
	 */
	protected void modify(Power power) {
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
	 * @param left 剩余电量[0,100]
	 * @return 满足条件的充电宝
	 */
	public List<Power> getPowersByPowerLeft(int left) {
		return pdi.findPowersByPowerLeft(left);
	}

	/**
	 * 获取所有的充电宝
	 * 
	 * @return 充电宝
	 */
	public List<Power> getAllPowers() {
		return pdi.getAllPowers();
	}

	/**
	 * 根据id获取指定的充电宝
	 * 
	 * @param id id
	 * @return 对应id的充电宝
	 */
	public Power getPowerById(String id) {
		return pdi.findPowerById(id);
	}

	/**
	 * 给指定充电宝充电
	 * 
	 * @param power  要充电的充电宝
	 * @param amount 充电量
	 */
	public void chage(Power power, int amount) {
		if (power.getLeft() == 100) {
			return;
		}
		power.setLeft(power.getLeft() + (power.getLeft() + amount > 100 ? 100 - power.getLeft() : amount));
		power.removeStatus(Power.NO_POWER);
		power.addStatus(Power.AVAILABLE);
		pdi.updatePower(power);
	}

	/**
	 * 随机充电
	 */
	public void chageAll() {
		List<Power> allPowers = getAllPowers();
		for (Power power : allPowers) {
			if (!power.hasStatus(Power.BORROWED | Power.BROKEN)) {
				chage(power, Main.getRandom(1, 10));
			}
		}
	}

	// 单例模式
	private PowerService() {
		pdi = new PowerDaoImp();
	}

	public static PowerService getInstance() {
		return instance;
	}
}
