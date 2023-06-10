package team.skadi.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import team.skadi.rental.bean.Manger;
import team.skadi.rental.dao.MangerDao;
import team.skadi.rental.utils.DBUtil;

public class MangerDaoImp implements MangerDao {

	@Override
	public Manger findMangerById(String id) {
		Connection connection = DBUtil.getConnection();
		if (id == null) {
			return null;
		}
		String sql = "select * from mangers WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		Manger manger = new Manger();
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, id);
			rs = stat.executeQuery();
			while (rs.next()) {
				manger.setId(rs.getString("id"));
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
