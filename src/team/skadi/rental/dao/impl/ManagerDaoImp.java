package team.skadi.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import team.skadi.rental.bean.Manager;
import team.skadi.rental.dao.ManagerDao;
import team.skadi.rental.utils.DBUtil;

public class ManagerDaoImp implements ManagerDao {

	@Override
	public Manager findMangerById(String id) {
		Connection connection = DBUtil.getConnection();
		if (id == null) {
			return null;
		}
		String sql = "select * from mangers WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		Manager manger = new Manager();
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, id);
			rs = stat.executeQuery();
			while (rs.next()) {
				manger.setId(rs.getString("id"));
				manger.setName(rs.getString("name"));
				manger.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
		return manger;
	}

}
