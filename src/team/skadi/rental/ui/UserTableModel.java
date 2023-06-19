package team.skadi.rental.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import team.skadi.rental.bean.User;

@SuppressWarnings("serial")
public class UserTableModel extends AbstractTableModel {

	private String[] title = { "用户id", "用户名", "电话号码", "账号余额", " 邮箱", "信用分" };
	private List<User> userList;

	public UserTableModel(List<User> userList) {
		this.userList = userList;
	}

	public void addUser(User user) {
		userList.add(user);
	}

	public void removeUser(int rowIndex) {
		userList.remove(rowIndex);
	}

	@Override
	public int getRowCount() {
		return userList.size();
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public String getColumnName(int column) {
		return title[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = userList.get(columnIndex);
		switch (rowIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getName();
		case 2:
			return user.getPhoneNumber();
		case 3:
			return user.getBalance();
		case 4:
			return user.getEmail();
		case 5:
			return user.getCredit();
		default:
			return null;
		}
	}

}
