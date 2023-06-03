package team.skadi.rental.service;

import team.skadi.rental.dao.impl.UserDaoImp;

public class UserService {

	private UserDaoImp udi;
	private static UserService instance = new UserService();

	// TODO: 业务逻辑在这这里写（写后记得删除本行）

	// 单例模式
	private UserService() {
		udi = new UserDaoImp();
	}

	public static UserService getInstance() {
		return instance;
	}
}
