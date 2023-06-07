package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Manger;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.impl.MangerDaoImp;

public class MangerService {

	private MangerDaoImp mdi;
	private static final MangerService instance = new MangerService();

	/**
	 * 管理人员登录
	 * 
	 * @param id       管理员的工号
	 * @param password 密码
	 * @return 管理员*1, null: 工号或密码错误
	 */
	public Manger login(String id, String password) {
		return null;
	}

	/**
	 * 管理人员登出
	 * 
	 * @param manger 要登出的管理员
	 */
	public void signOut(Manger manger) {

	}

	/**
	 * 移除指定用户
	 * 
	 * @param user 要移除的用户
	 */
	public void removeUser(User user) {

	}

	/**
	 * 添加指定状态的充电宝
	 * 
	 * @param power 需要添加的充电宝
	 */
	public void addPower(Power power) {

	}

	/**
	 * 修改指定的充电宝
	 * 
	 * @param power 需要修改的充电宝
	 */
	public void modifyPower(Power power) {

	}

	/**
	 * 移除指定的充电宝
	 * 
	 * @param power 要移除的充电宝
	 */
	public void removePower(Power power) {

	}

	/**
	 * 获取用户列表
	 * 
	 * @return 用户列表
	 */
	public List<User> getUserList() {
		return null;
	}

	public static MangerService getInstance() {
		return instance;
	}

	// 单例模式
	private MangerService() {
		mdi = new MangerDaoImp();
	}
}
