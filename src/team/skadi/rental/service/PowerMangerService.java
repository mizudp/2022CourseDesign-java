package team.skadi.rental.service;

import team.skadi.rental.bean.Power;
import team.skadi.rental.dao.impl.PowerDaoImp;

public class PowerMangerService {

	private PowerDaoImp pdi;
	private static PowerMangerService instance = new PowerMangerService();

	public Power borrow(String id,double left,int status){
		if (id == null) {

		}else {

		}


		return null;
	}
	
	// 单例模式
	private PowerMangerService() {
		pdi = new PowerDaoImp();
	}

	public static PowerMangerService getInstance() {
		return instance;
	}
}
