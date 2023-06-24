package team.skadi.rental.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import team.skadi.rental.bean.User;
import team.skadi.rental.service.ManagerService;
import team.skadi.rental.ui.SearchPanel.SearchResult;

@SuppressWarnings("serial")
public class UserTableModel extends BasicTableModel {

	private String[] title = { "用户id", "用户名", "电话号码", "账号余额", " 邮箱", "信用分" };
	private List<User> userList;

	public UserTableModel(int mode) {
		super(mode);
		this.userList = new ArrayList<>();
	}

	public void changeData(List<User> userList) {
		this.userList = userList;
		fireTableDataChanged();
	}

	public SearchResult chageData(int searchIndex, String content) {
		List<User> users = null;
		switch (searchIndex) {
		case SearchPanel.USER_ID:
			User user = ManagerService.getInstance().findUserById(content);
			if (user != null) {
				users = new ArrayList<>(1);
				users.add(user);
			}
			break;
		case SearchPanel.USER_BALANCE:
			double balance = 0;
			try {
				balance = Double.parseDouble(content);
			} catch (NumberFormatException e) {
				return SearchResult.NAN;
			}
			users = ManagerService.getInstance().getUsersByBalance(balance);
			break;
		case SearchPanel.USER_CREDIT:
			int credit = 0;
			try {
				credit = Integer.parseInt(content);
			} catch (NumberFormatException e) {
				return SearchResult.NAN;
			}
			users = ManagerService.getInstance().getUsersByCredit(credit);
			break;
		}
		if (users != null && users.size() != 0) {
			changeData(users);
			return SearchResult.HAVE_RESULT;
		}
		return SearchResult.NO_RESULT;
	}

	public void addUser(User user) {
		userList.add(user);
		int row = userList.size() - 1;
		fireTableRowsInserted(row, row);
	}

	public void setPreferredWidth(TableColumnModel columnModel) {
		columnModel.getColumn(0).setPreferredWidth(1);
		columnModel.getColumn(1).setPreferredWidth(1);
		columnModel.getColumn(3).setPreferredWidth(1);
		columnModel.getColumn(5).setPreferredWidth(1);
	}

	public void removeUser(int rowIndex) {
		User user = userList.remove(rowIndex);
		ManagerService.getInstance().removeUser(user);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	public User getUser(int rowIndex) {
		return userList.get(rowIndex);
	}

	public void setUser(int rowIndex, User user) {
		userList.set(rowIndex, user);
		fireTableRowsUpdated(rowIndex, rowIndex);
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
		User user = userList.get(rowIndex);
		switch (columnIndex) {
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

	public class DoubleClick extends MouseAdapter {

		private MainFrame mainFrame;

		public DoubleClick(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			if (e.getClickCount() == 2) {
				User user = userList.get(table.getSelectedRow());
				new UserOption(mainFrame, OptionDialog.READ_ONLY_MODE, user).setVisible(true);
			}
		}
	}
}
