package team.skadi.rental.service;

import team.skadi.rental.dao.impl.PowerDaoImp;

public class PowerMangerService {

	private PowerDaoImp pdi;
	private static PowerMangerService instance = new PowerMangerService();

	//TODO: 业务逻辑在这这里写（写后记得删除本行）
	
	// 单例模式
	private PowerMangerService() {
		pdi = new PowerDaoImp();
	}

	public static PowerMangerService getInstance() {
		return instance;
	}
}
