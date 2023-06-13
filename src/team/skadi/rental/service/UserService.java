package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Log;
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
		user.setSerialnum(serialnum);
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
	 * @param user 修改后的用户
	 */
	public void modify(User user) {
		udi.updateUser(user);
	}

	/**
	 * 借充电宝
	 * 
	 * @param userLogin 已登录的用户
	 * @param power     充电宝
	 * @return false: 已经借了充电宝，true: 成功借入
	 */
	public boolean borrow(User userLogin, Power power) {
		Log log = LogService.getLog(userLogin, power);
		if (log == null) {
			LogService.addBorrowLog(userLogin, power);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 归还充电宝
	 * 
	 * @param userLogin 已经登录的用户
	 * @param power     充电宝
	 * @return 使用时长
	 */
	public int giveBack(User userLogin, Power power) {
		Log log = LogService.getLog(userLogin, power);
		if (log != null) {
			LogService.addReturnLog(log);
		}
		return LogService.getTime(log);
	}

	/**
	 * 用户使用充电宝
	 * 
	 * @param userLogin 已登录用户
	 * @param power     用户要使用的充电宝
	 * @param amount    使用了多少电
	 * @return true 使用成功。false 没电，无法使用
	 */
	public boolean use(User userLogin, Power power, int amount) {
		if (power.getStatus() == Power.NO_POWER || power.getLeft() < amount || amount > 100 || amount < 0) {
			return false;
		}
		power.setLeft(power.getLeft() - amount);
		PowerService.getInstance().modify(power);
		if (power.getLeft() == 0) {
			power.setStatus(Power.NO_POWER);
			PowerService.getInstance().modify(power);
		}
		return true;
	}

	/**
	 * 移除指定的用户
	 * 
	 * @param user 要移除的用户
	 */
	protected void removeUser(User user) {
		user.del();
		udi.updateUser(user);
	}

	/**
	 * 获得所有用户
	 * 
	 * @return 用户列表
	 */
	protected List<User> getAllUser() {
		return udi.getAllUser();
	}

	// 单例模式
	private UserService() {
		udi = new UserDaoImp();
	}

	public static UserService getInstance() {
		return instance;
	}
}
