package team.skadi.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import team.skadi.rental.bean.User;
import team.skadi.rental.dao.UserDao;
import team.skadi.rental.utils.DBUtil;

public class UserDaoImp implements UserDao {

	@Override
	public User findUserById(String userId) {
		Connection connection = DBUtil.getConnection();
		if (userId == null) {
			return null;
		}
		String sql = "select * from user WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		User user = new User();
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, userId);
			rs = stat.executeQuery();// 执行SQL语句
			while (rs.next()) {
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPhoneNumber(rs.getString("phoneNumber"));
				user.setPassword(rs.getString("password"));
				user.setBalance(rs.getDouble("balance"));
				user.setEmail(rs.getString("email"));
				user.setCredit(rs.getInt("credit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
		if (user.getId() == null) {
			return null;
		} else {
			return user;
		}
	}

	@Override
	public void updateUser(User user) {
		Connection connection = DBUtil.getConnection();
		String sql = "UPDATE user SET name=?,phoneNumber=?,password=?,balance=?,email=?,credit=? WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, user.getName());
			stat.setString(2, user.getPhoneNumber());
			stat.setString(3, user.getPassword());
			stat.setDouble(4, user.getBalance());
			stat.setString(5, user.getEmail());
			stat.setInt(6, user.getCredit());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
	}

	@Override
	public void createNewUser() {
		Connection connection = DBUtil.getConnection();
		String sql = "INSERT user (id,name,phoneNumber,password,balance,email) VALUE (null,null,null,null,null,null);";
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
	}

	@Override
	public void addNewUser(String userId, int serialnum) {
		Connection connection = DBUtil.getConnection();
		String sql = "UPDATE user SET id=? WHERE serialnum=?";
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, userId);
			stat.setInt(2, serialnum);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}

	}

	@Override
	public int getSerialnum() {
		int serialnum = 0;
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT * FROM users WHERE id is null";
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = connection.prepareStatement(sql);
			rs = stat.executeQuery();
			while (rs.next()) {
				serialnum = rs.getInt("serialnum");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return serialnum;
	}

}
