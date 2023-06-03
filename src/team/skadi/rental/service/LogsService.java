package team.skadi.rental.service;

import java.util.List;

import team.skadi.rental.bean.Logs;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.impl.LogsDaoImp;

public class LogsService {
	private static LogsDaoImp ldi = new LogsDaoImp();

	public static void addLogs(User user, String logContext) {
		// TODO: 补充逻辑
	}

	public static List<Logs> queryLogs(User user) {
		// TODO: 补充逻辑
		return null;
	}
}
