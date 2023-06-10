package team.skadi.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import team.skadi.rental.bean.Logs;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.dao.LogsDao;
import team.skadi.rental.utils.DBUtil;
import team.skadi.rental.utils.DateUtil;

public class LogsDaoImp implements LogsDao {

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
			statement.setString(3, DateUtil.getDateNow());
			statement.setString(4, content);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, statement, rs);
		}
	}

	@Override
	public void finishLog(Logs log) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "UPDATE logs SET endDate=? WHERE userId=? AND powerId=? AND endDate IS NULL;";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, DateUtil.getDateNow());
			statement.setString(2, log.getUserId());
			statement.setString(3, log.getPowerId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, statement, rs);
		}
	}

	@Override
	public Logs getLog(User user, Power power) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "SELECT * from logs WHERE userId=? AND powerId=? AND endDate IS NULL;";
		Logs log = new Logs();
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getId());
			statement.setString(2, power.getId());
			rs = statement.executeQuery();
			while (rs.next()) {
				log.setUserId(rs.getString("userID"));
				log.setPowerId(rs.getString("powerId"));
				log.setStartDate(rs.getString("startDate"));
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
	public List<Logs> queryLogs(User user) {
		Connection connection = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "SELECT * from logs WHERE userId=?;";
		List<Logs> logs = new ArrayList<>();
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getId());
			rs = statement.executeQuery();
			while (rs.next()) {
				Logs logs2 = new Logs();
				logs2.setUserId(rs.getString("userID"));
				logs2.setPowerId(rs.getString("powerId"));
				logs2.setStartDate(rs.getString("startDate"));
				logs2.setContext(rs.getString("context"));
				logs.add(logs2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, statement, rs);
		}
		return logs;
	}

}
