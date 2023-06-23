package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Manager;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.impl.ManagerDaoImp;

public class ManagerService {

	private ManagerDaoImp mdi;
	private static final ManagerService instance = new ManagerService();

	/**
	 * 管理人员登录
	 * 
	 * @param id       管理员的工号
	 * @param password 密码
	 * @return 管理员*1, null: 工号或密码错误
	 */
	public Manager login(String id, String password) {
		Manager manger = mdi.findMangerById(id);
		if (manger != null && manger.getPassword().equals(password)) {
			return manger;
		} else {
			return null;
		}
	}

	/**
	 * 管理人员登出
	 * 
	 * @param manger 要登出的管理员
	 */
	public void signOut(Manager manger) {
		manger = null;
	}

	/**
	 * 移除指定用户
	 * 
	 * @param user 要移除的用户
	 */
	public void removeUser(User user) {
		UserService.getInstance().removeUser(user);
	}

	/**
	 * 添加指定状态的充电宝
	 * 
	 * @param left 剩余电量[0,100]
	 * @return 指定电量充电宝
	 */
	public Power addPower(int left) {
		return PowerService.getInstance().add(left);
	}

	/**
	 * 修改指定的充电宝
	 * 
	 * @param power 需要修改的充电宝
	 */
	public void modifyPower(Power power) {
		PowerService.getInstance().modify(power);
	}

	/**
	 * 移除指定的充电宝
	 * 
	 * @param power 要移除的充电宝
	 */
	public void removePower(Power power) {
		PowerService.getInstance().remove(power);
	}

	/**
	 * 获取用户列表
	 * 
	 * @return 用户列表
	 */
	public List<User> getAllUsers() {
		return UserService.getInstance().getAllUser();
	}

	/**
	 * @see UserService#getUsersByCredit(int)
	 * @param credit
	 * @return
	 */
	public List<User> getUsersByCredit(int credit) {
		return UserService.getInstance().getUsersByCredit(credit);
	}

	/**
	 * 通过id查询用户
	 * 
	 * @param id 用户id
	 * @return 指定id的用户
	 */
	public User findUserById(String id) {
		return UserService.getInstance().findUserById(id);
	}

	/**
	 * 根据用户余额
	 * 
	 * @param balance 余额
	 * @return 小于指定金额的用户列表
	 */
	public List<User> getUsersByBalance(double balance) {
		return UserService.getInstance().getUsersByBalance(balance);
	}

	public static ManagerService getInstance() {
		return instance;
	}

	// 单例模式
	private ManagerService() {
		mdi = new ManagerDaoImp();
	}
}
