package team.skadi.rental.dao;

import java.util.List;

import team.skadi.rental.bean.User;

public interface UserDao {
	/**
	 * 根据用户id获取指定的用户
	 * 
	 * @param userId 用户id
	 * @return 用户*1， null 没有该id的用户
	 */
	User findUserById(String userId);

	/**
	 * 更新用户信息
	 * 
	 * @param user 需要更新的用户对象
	 */
	void updateUser(User user);

	/**
	 * 获得所有的用户
	 * 
	 * @return 用户列表
	 */
	List<User> getAllUser();

	/**
	 * 通过账户余额来获得用户列表
	 * 
	 * @param balance 账户余额
	 * @return 小于指定余额的用户列表
	 */
	List<User> getUsersByBalance(double balance);

	/**
	 * 通过账户信用分来查询用户
	 * 
	 * @param credit 信用分
	 * @return 用户列表
	 */
	List<User> getUsersByCredit(int credit);

	/**
	 * 创建新用户（空账户）
	 */
	void createNewUser();

	/**
	 * 用于注册，添加新账户，正式完成注册
	 * 
	 * @param userId    用户id
	 * @param serialnum 空账户的序列号
	 */
	void addNewUser(String userId, int serialnum);

	/**
	 * 获取空账号的序列号
	 * 
	 * @return 序列号
	 */
	int getSerialnum();

}
