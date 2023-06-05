package team.skadi.rental.service;

import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.UserDao;
import team.skadi.rental.dao.impl.UserDaoImp;

public class UserService {

	private UserDaoImp udi;
	private static UserService instance = new UserService();

	public User logIn(String userName, String userPassword, String userPhoneNumber, double balance, String userEmail) {
		User user = new User();
		udi.createNewUser();
		int serialnum = udi.getSerialnum();
		String id = String.format("%06d", serialnum);
		user.setId(id);
		udi.addNewUser(id, serialnum);
		user.setName(userName);
		user.setPassword(userPassword);
		user.setPhoneNumber(userPhoneNumber);
		user.setBalance(balance);
		user.setEmail(userEmail);
		udi.updateUser(user);
		LogsService.addLogs(user, "注册完成");
		return user;
	}

	public User signIn(String id, String userPassword) {
		User user = udi.findUserById(id);
		if (user != null && user.getPassword().equals(userPassword)) {
			return user;
		} else {
			return null;
		}
	}

	public void modify(User user, String phoneNumber, String email) {
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

	// 单例模式
	private UserService() {
		udi = new UserDaoImp();
	}

	public static UserService getInstance() {
		return instance;
	}
}
