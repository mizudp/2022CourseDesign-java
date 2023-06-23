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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import team.skadi.rental.bean.Manager;
import team.skadi.rental.service.LogService;
import team.skadi.rental.service.ManagerService;
import team.skadi.rental.service.PowerService;
import team.skadi.rental.ui.MainFrame.PanelName;
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
		
		userTableModel = new UserTableModel(ManagerService.getInstance().getAllUsers());
		userTable = new JTable(userTableModel);
		userTable.setRowHeight(30);
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userTableModel.setPreferredWidth(userTable.getColumnModel());
		tabbedPane.add("用户列表", new JScrollPane(userTable));

		powerTableModel = new PowerTableModel(PowerService.getInstance().getAllPowers());
		powerTable = new JTable(powerTableModel);
		powerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		powerTable.setRowHeight(30);
		tabbedPane.add("充电宝列表", new JScrollPane(powerTable));

		logTableModel = new LogTableModel(LogService.getAllLogs(), LogTableModel.MANAGER_MODE);
		logTable = new JTable(logTableModel);
		logTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		logTable.setRowHeight(30);
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
			searchPanel.setSearchMode(tabbedPane.getSelectedIndex());
		});
		addBtn.addActionListener(this);
		delBtn.addActionListener(this);
		moitfyBtn.addActionListener(this);
		refash.addActionListener(this);
		exitBtn.addActionListener(this);
	}

	public void setLoginManager(Manager loginManager) {
		this.loginManager = loginManager;
		updateLable();
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

		} else if (source.equals(delBtn)) {

		} else if (source.equals(refash)) {

		} else if (source.equals(exitBtn)) {
			mainFrame.showPanel(PanelName.MANAGER_LOGIN);
			loginManager = null;
		}
	}

}
