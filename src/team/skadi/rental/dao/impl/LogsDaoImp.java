package team.skadi.rental.dao.impl;

import java.util.List;

import team.skadi.rental.bean.Logs;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.LogsDao;

public class LogsDaoImp implements LogsDao {

	@Override
	public void addLog(User user, Power power, String content) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTime(User user, Power power) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Logs> queryLogs(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
