package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import team.skadi.rental.bean.Manager;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.service.LogService;
import team.skadi.rental.service.ManagerService;
import team.skadi.rental.service.PowerService;
import team.skadi.rental.ui.SearchPanel.SearchResult;

@SuppressWarnings("serial")
public class ManagerPanel extends JPanel implements ActionListener {

	private Manager loginManager;

	MainFrame mainFrame;
	private JTabbedPane tabbedPane;
	private JLabel idLabel;
	private JLabel managerLabel;
	private JButton addBtn;
	private JButton delBtn;
	private JButton moitfyBtn;
	private JTable logTable;
	private JTable powerTable;
	private JTable userTable;
	private PowerTableModel powerTableModel;
	private UserTableModel userTableModel;
	private LogTableModel logTableModel;
	private JButton exitBtn;
	private JButton refash;
	private JButton chargeAllBtn;
	private SearchPanel searchPanel;

	public ManagerPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		bulidLayout();
		addListener();
	}

	private void bulidLayout() {
		setLayout(new BorderLayout());
		JLabel label;
		JPanel northPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(northPanel, BoxLayout.X_AXIS);
		northPanel.setLayout(boxLayout);

		label = new JLabel("工号：");
		idLabel = new JLabel();
		northPanel.add(label);
		northPanel.add(idLabel);
		northPanel.add(Box.createHorizontalGlue());
		label = new JLabel("管理员姓名：");
		managerLabel = new JLabel();
		northPanel.add(label);
		northPanel.add(managerLabel);
		northPanel.add(Box.createVerticalStrut(50));
		add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets.set(15, 15, 15, 15);

		tabbedPane = new JTabbedPane();

		userTableModel = new UserTableModel(BasicTableModel.MANAGER_MODE);
		userTable = new JTable(userTableModel);
		userTable.addMouseListener(userTableModel.new DoubleClick(mainFrame));
		userTable.setRowHeight(30);
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userTableModel.setPreferredWidth(userTable.getColumnModel());
		tabbedPane.add("用户列表", new JScrollPane(userTable));

		powerTableModel = new PowerTableModel(BasicTableModel.MANAGER_MODE);
		powerTable = new JTable(powerTableModel);
		powerTable.addMouseListener(powerTableModel.new DoubleClick(mainFrame));
		powerTable.getSelectionModel().addListSelectionListener(e -> {
			int row = powerTable.getSelectedRow();
			if (row != -1) {
				moitfyBtn.setEnabled(!powerTableModel.getPower(row).hasStatus(Power.BORROWED));
			}
		});
		powerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		powerTable.setRowHeight(30);
		tabbedPane.add("移动电源列表", new JScrollPane(powerTable));

		logTableModel = new LogTableModel(LogTableModel.MANAGER_MODE);
		logTable = new JTable(logTableModel);
		logTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		logTable.setRowHeight(30);
		logTable.addMouseListener(logTableModel.new DoubleClick(mainFrame));
		logTableModel.setPreferredWidth(logTable.getColumnModel());
		tabbedPane.add("订单日志列表", new JScrollPane(logTable));

		centerPanel.add(tabbedPane, gbc);
		centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		add(centerPanel, BorderLayout.CENTER);

		JPanel eastPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.insets.set(0, 15, 25, 15);
		addBtn = new JButton("增加");
		eastPanel.add(addBtn, gbc);
		delBtn = new JButton("删除");
		eastPanel.add(delBtn, gbc);
		moitfyBtn = new JButton("修改");
		eastPanel.add(moitfyBtn, gbc);
		refash = new JButton("刷新");
		eastPanel.add(refash, gbc);
		chargeAllBtn = new JButton("充电");
		chargeAllBtn.setEnabled(false);
		eastPanel.add(chargeAllBtn, gbc);
		exitBtn = new JButton("注销");
		gbc.insets.bottom = 0;
		eastPanel.add(exitBtn, gbc);
		add(eastPanel, BorderLayout.EAST);

		searchPanel = new SearchPanel(mainFrame, 100, 100);
		searchPanel.addOnClickListener(new SearchPanel.OnClickListener() {

			@Override
			public SearchResult onSearchButtonClick(int searchMode, int searchIndex, String content) {
				switch (searchMode) {
				case SearchPanel.USER_MODE:
					return userTableModel.chageData(searchIndex, content);
				case SearchPanel.POWER_MODE:
					return powerTableModel.changeData(searchIndex, content);
				case SearchPanel.LOG_MODE:
					return logTableModel.changeData(searchIndex, content);
				}
				return SearchResult.NO_RESULT;
			}

			@Override
			public void onCloseButtonClick(int searchMode) {
				switch (searchMode) {
				case SearchPanel.USER_MODE:
					userTableModel.changeData(ManagerService.getInstance().getAllUsers());
					break;
				case SearchPanel.POWER_MODE:
					powerTableModel.changeData(PowerService.getInstance().getAllPowers());
					break;
				case SearchPanel.LOG_MODE:
					logTableModel.changeData(LogService.getAllLogs());
					break;
				}
			}
		});
		add(searchPanel, BorderLayout.SOUTH);
	}

	private void addListener() {
		tabbedPane.addChangeListener(e -> {
			int selectedIndex = tabbedPane.getSelectedIndex();
			searchPanel.setSearchMode(selectedIndex);
			if (selectedIndex == 2) { // log
				addBtn.setEnabled(false);
				moitfyBtn.setEnabled(false);
				delBtn.setEnabled(false);
				chargeAllBtn.setEnabled(false);
			} else {
				addBtn.setEnabled(true);
				moitfyBtn.setEnabled(true);
				delBtn.setEnabled(true);
				chargeAllBtn.setEnabled(false);
			}
			if (selectedIndex == 1) {// power
				chargeAllBtn.setEnabled(true);
			}
		});
		addBtn.addActionListener(this);
		delBtn.addActionListener(this);
		moitfyBtn.addActionListener(this);
		refash.addActionListener(this);
		chargeAllBtn.addActionListener(this);
		exitBtn.addActionListener(this);
	}

	public void setLoginManager(Manager loginManager) {
		this.loginManager = loginManager;
		updateLable();
		userTableModel.changeData(ManagerService.getInstance().getAllUsers());
		powerTableModel.changeData(PowerService.getInstance().getAllPowers());
		logTableModel.changeData(LogService.getAllLogs());
		tabbedPane.setSelectedIndex(0);
	}

	public void updateLable() {
		idLabel.setText(loginManager.getId());
		managerLabel.setText(loginManager.getName());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(addBtn)) {
			onAddBtnClick();
		} else if (source.equals(delBtn)) {
			onDelBtnClick();
		} else if (source.equals(moitfyBtn)) {
			onMoitfyBtnClick();
		} else if (source.equals(refash)) {
			onRefashBtnClick();
		} else if (source.equals(exitBtn)) {
			mainFrame.showPreviousPanel();
			loginManager = null;
		} else if (source.equals(chargeAllBtn)) {
			PowerService.getInstance().chageAll();
			powerTableModel.changeData(PowerService.getInstance().getAllPowers());
		}
	}

	private void onRefashBtnClick() {
		switch (searchPanel.getSearchMode()) {
		case SearchPanel.USER_MODE:
			userTableModel.changeData(ManagerService.getInstance().getAllUsers());
			break;
		case SearchPanel.POWER_MODE:
			powerTableModel.changeData(PowerService.getInstance().getAllPowers());
			break;
		case SearchPanel.LOG_MODE:
			logTableModel.changeData(LogService.getAllLogs());
			break;
		}
		searchPanel.close();
		JOptionPane.showMessageDialog(mainFrame, "刷新成功！");
	}

	private void onMoitfyBtnClick() {
		int row;
		switch (searchPanel.getSearchMode()) {
		case SearchPanel.USER_MODE:
			row = userTable.getSelectedRow();
			if (row != -1) {
				User user = new UserOption(mainFrame, OptionDialog.MODIFY_MODE, userTableModel.getUser(row)).getUser();
				userTableModel.setUser(row, user);
			}
			break;
		case SearchPanel.POWER_MODE:
			row = powerTable.getSelectedRow();
			if (row != -1) {
				Power power2 = powerTableModel.getPower(row);
				if (!power2.hasStatus(Power.BORROWED)) {
					Power power = new PowerOption(mainFrame, OptionDialog.MODIFY_MODE, power2).getPower();
					powerTableModel.setPower(row, power);
				}
			}
			break;
		}
	}

	private void onDelBtnClick() {
		int row;
		switch (searchPanel.getSearchMode()) {
		case SearchPanel.USER_MODE:
			row = userTable.getSelectedRow();
			if (row != -1) {
				userTableModel.removeUser(row);
				userTableModel.changeData(ManagerService.getInstance().getAllUsers());
			}
			break;
		case SearchPanel.POWER_MODE:
			row = powerTable.getSelectedRow();
			if (row != -1) {
				powerTableModel.removePower(row);
				powerTableModel.changeData(PowerService.getInstance().getAllPowers());
			}
			break;
		}
	}

	private void onAddBtnClick() {
		switch (searchPanel.getSearchMode()) {
		case SearchPanel.USER_MODE:
			User user = new UserOption(mainFrame).getUser();
			if (user != null) {
				userTableModel.addUser(user);
			}
			break;
		case SearchPanel.POWER_MODE:
			Power power = new PowerOption(mainFrame).getPower();
			if (power != null) {
				powerTableModel.addPower(power);
			}
			break;
		}
	}

}
