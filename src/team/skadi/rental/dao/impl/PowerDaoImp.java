package team.skadi.rental.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import team.skadi.rental.bean.Power;
import team.skadi.rental.dao.PowerDao;
import team.skadi.rental.utils.DBUtil;

public class PowerDaoImp implements PowerDao {

	@Override
	public List<Power> findPowersByStatus(int status) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT * FROM powers WHERE status=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Power> powers = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setInt(1, status);
			rs = stat.executeQuery();// 执行SQL语句
			powers = getlist(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
		return powers;
	}

	@Override
	public List<Power> findPowersByPowerLeft(double gtLeft) {
		Connection connection = DBUtil.getConnection();
		String sql = "SELECT * FROM powers WHERE left>?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		List<Power> powers = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setDouble(1, gtLeft);
			rs = stat.executeQuery();// 执行SQL语句
			powers = getlist(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
		return powers;
	}

	@Override
	public Power findPowerById(String powerId) {
		Connection connection = DBUtil.getConnection();
		if (powerId == null) {
			return null;
		}
		String sql = "select * from powers WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		Power power = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, powerId);
			rs = stat.executeQuery();// 执行SQL语句
			power = getlist(rs).get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
		if (power.getId() == null) {
			return null;
		} else {
			return power;
		}
	}

	@Override
	public void createNewPower() {
		Connection connection = DBUtil.getConnection();
		String sql = "INSERT powers (id,left,status) VALUE (null,null,null);";
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
	public void addNewPower(String powerId, int serialnum) {
		Connection connection = DBUtil.getConnection();
		String sql = "UPDATE powers SET id=? WHERE serialnum=?";
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, powerId);
			stat.setInt(2, serialnum);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, stat, rs);
		}
	}

	@Override
	public void updatePower(Power power) {
		Connection connection = DBUtil.getConnection();
		String sql = "UPDATE powers SET serialnum=?,left=?,status=? WHERE id=?;";
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setInt(1, power.getSerialnum());
			stat.setDouble(2, power.getLeft());
			stat.setInt(3, power.getStatus());
			stat.setString(4, power.getId());
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
		String sql = "SELECT * FROM powers WHERE id is null";
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

	private List<Power> getlist(ResultSet rs) throws SQLException {
		ArrayList<Power> powers = new ArrayList<>();
		while (rs.next()) {
			Power power = new Power();
			power.setId(rs.getString("id"));
			power.setSerialnum(rs.getInt("serialnum"));
			power.setLeft(rs.getDouble("left"));
			power.setStatus(rs.getInt("status"));
			powers.add(power);
		}
		return powers;
	}
}
