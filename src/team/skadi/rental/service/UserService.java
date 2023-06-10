package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.impl.UserDaoImp;

public class UserService {

	private UserDaoImp udi;
	private static final UserService instance = new UserService();

	/**
	 * 用户注册
	 * 
	 * @param userName        用户名
	 * @param userPassword    密码
	 * @param userPhoneNumber 电话号码
	 * @param userEmail       用户邮箱
	 * @return 完成注册后的用户实例*1
	 */
	public User signIn(String userName, String userPassword, String userPhoneNumber, String userEmail) {
		User user = new User();
		udi.createNewUser();
		int serialnum = udi.getSerialnum();
		String id = String.format("%06d", serialnum);
		user.setId(id);
		udi.addNewUser(id, serialnum);
		user.setName(userName);
		user.setPassword(userPassword);
		user.setPhoneNumber(userPhoneNumber);
		user.setBalance(0);
		user.setEmail(userEmail);
		user.setCredit(100);
		udi.updateUser(user);
		return user;
	}

	/**
	 * 用户登录
	 * 
	 * @param id           用户账户
	 * @param userPassword 用户密码
	 * @return
	 */
	public User login(String id, String userPassword) {
		User user = udi.findUserById(id);
		if (user != null && user.getPassword().equals(userPassword)) {
			return user;
		} else {
			return null;
		}
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user        需要修改的用户
	 * @param name        修改后的用户名
	 * @param phoneNumber 修改后的电话号码
	 * @param email       修改后的邮箱
	 */
	public void modify(User user, String name, String phoneNumber, String email) {
		user.setName(name);
		user.setPhoneNumber(phoneNumber);
		user.setEmail(email);
		udi.updateUser(user);
	}

	/**
	 * 借充电宝
	 * 
	 * @param userLogin 已登录的用户
	 * @param power     充电宝
	 */
	public void borrow(User userLogin, Power power) {

	}

	/**
	 * 归还充电宝
	 * 
	 * @param userLogin 已经登录的用户
	 * @param power     充电宝
	 */
	public void giveBack(User userLogin, Power power) {

	}

	/**
	 * 移除指定的用户
	 * 
	 * @param user 要移除的用户
	 */
	protected void removeUser(User user) {

	}

	/**
	 * 获得所有用户
	 * 
	 * @return 用户列表
	 */
	protected List<User> getAllUser() {
		return null;
	}

	// 单例模式
	private UserService() {
		udi = new UserDaoImp();
	}

	public static UserService getInstance() {
		return instance;
	}
}
