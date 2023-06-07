package team.skadi.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		String sql = "select * from users WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		User user = new User();
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, userId);
			rs = stat.executeQuery();// 执行SQL语句
			while (rs.next()) {
				user.setId(rs.getString("id"));
				user.setSerialnum(rs.getInt("serialnum"));
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
		String sql = "UPDATE users SET serialnum=?,name=?,phoneNumber=?,password=?,balance=?,email=?,credit=? WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setInt(1, user.getSerialnum());
			stat.setString(2, user.getName());
			stat.setString(3, user.getPhoneNumber());
			stat.setString(4, user.getPassword());
			stat.setDouble(5, user.getBalance());
			stat.setString(6, user.getEmail());
			stat.setInt(7, user.getCredit());
			stat.setString(8, user.getId());
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByBalance(double balance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNewUser() {
		Connection connection = DBUtil.getConnection();
		String sql = "INSERT users (id,name,phoneNumber,password,balance,email) VALUE (null,null,null,null,null,null);";
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
		String sql = "UPDATE users SET id=? WHERE serialnum=?";
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
