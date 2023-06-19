package team.skadi.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import team.skadi.rental.bean.Log;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.LogDao;
import team.skadi.rental.utils.DBUtil;

public class LogDaoImp implements LogDao {

	@Override
	public void addLog(User user, Power power, String content) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "INSERT logs(userId,powerId,startDate,context) VALUES(?,?,?,?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getId());
			statement.setString(2, power.getId());
			statement.setLong(3, System.currentTimeMillis());
			statement.setString(4, content);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, statement, rs);
		}
	}

	@Override
	public void finishLog(Log log) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "UPDATE logs SET endDate=?,context=? WHERE userId=? AND powerId=? AND endDate IS NULL;";
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, log.getEndDate());
			statement.setString(2, log.getContext());
			statement.setString(3, log.getUserId());
			statement.setString(4, log.getPowerId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, statement, rs);
		}
	}

	@Override
	public Log getLog(User user, Power power) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "SELECT * from logs WHERE userId=? AND powerId=? AND endDate IS NULL;";
		Log log = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getId());
			statement.setString(2, power.getId());
			rs = statement.executeQuery();
			while (rs.next()) {
				log = new Log();
				log.setUserId(rs.getString("userID"));
				log.setPowerId(rs.getString("powerId"));
				log.setStartDate(rs.getLong("startDate"));
				log.setEndDate(rs.getLong("endDate"));
				log.setContext(rs.getString("context"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, statement, rs);
		}
		return log;
	}

	@Override
	public List<Log> queryLogs(User user) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "SELECT * from logs WHERE userId=?;";
		List<Log> logs = new ArrayList<>();
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getId());
			rs = statement.executeQuery();
			while (rs.next()) {
				Log log = new Log();
				log.setUserId(rs.getString("userID"));
				log.setPowerId(rs.getString("powerId"));
				log.setStartDate(rs.getLong("startDate"));
				log.setEndDate(rs.getLong("endDate"));
				log.setContext(rs.getString("context"));
				logs.add(log);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, statement, rs);
		}
		return logs;
	}

}
