package team.skadi.rental.service;

import team.skadi.rental.dao.impl.MangerDaoImp;

public class MangerService {

	private MangerDaoImp mdi;
	private static MangerService instance = new MangerService();

	// TODO: 业务逻辑在这这里写（写后记得删除本行）

	public static MangerService getInstance() {
		return instance;
	}

	// 单例模式
	private MangerService() {
		mdi = new MangerDaoImp();
	}
}
