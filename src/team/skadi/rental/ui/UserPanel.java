package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import team.skadi.rental.Main;
import team.skadi.rental.bean.Log;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.service.LogService;
import team.skadi.rental.service.PowerService;
import team.skadi.rental.service.UserService;
import team.skadi.rental.ui.MainFrame.PanelName;
import team.skadi.rental.ui.SearchPanel.SearchResult;
import team.skadi.rental.utils.DateUtil;

@SuppressWarnings("serial")
public class UserPanel extends JPanel implements ActionListener {

	public static final String RENTAL_PANEL = "rental", EMPTY_PANEL = "empty", RENTAL_INFO_PANEL = "infor",
			LOG_PANEL = "log";

	private User userLogin;
	private Log log;

	MainFrame mainFrame;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel balanceLabel;
	private JLabel creditLabel;
	private JButton mainBtn;
	private JButton rentalBtn;
	private JButton logBtn;
	private JButton rechargeBtn;
	private JButton modifyBtn;
	private JButton exitBtn;
	private JPanel centerPanel;
	private JPanel innerPanel;
	private JButton lentbtn;

	private JTable logTable;
	private LogTableModel logTableModel;
	private JTable rentalTable;
	private UserPowerTableModel userPowerTableModel;
	private JTable userPowerTable;
	private PowerTableModel powerTableModel;

	private CardLayout userLayout;

	public UserPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		buildLayout();
		addListener();
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		JLabel label;

		JPanel inforPanel = new JPanel();
		inforPanel.setLayout(new BoxLayout(inforPanel, BoxLayout.X_AXIS));

		inforPanel.add(Box.createVerticalStrut(60));

		label = new JLabel("当前账号：");
		idLabel = new JLabel();
		inforPanel.add(label);
		inforPanel.add(idLabel);
		inforPanel.add(Box.createHorizontalGlue());

		label = new JLabel("用户名：");
		nameLabel = new JLabel();
		inforPanel.add(label);
		inforPanel.add(nameLabel);
		inforPanel.add(Box.createHorizontalGlue());

		label = new JLabel("余额：");
		balanceLabel = new JLabel();
		inforPanel.add(label);
		inforPanel.add(balanceLabel);
		inforPanel.add(Box.createHorizontalGlue());

		label = new JLabel("信用分：");
		creditLabel = new JLabel();
		inforPanel.add(label);
		inforPanel.add(creditLabel);
		inforPanel.add(Box.createVerticalStrut(60));

		add(inforPanel, BorderLayout.NORTH);

		JPanel btnPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets.set(0, 15, 0, 15);

		mainBtn = new JButton("主页面");
		btnPanel.add(mainBtn, gbc);

		gbc.insets.set(25, 15, 0, 15);
		rentalBtn = new JButton("借移动电源");
		btnPanel.add(rentalBtn, gbc);

		logBtn = new JButton("查看历史订单");
		btnPanel.add(logBtn, gbc);

		rechargeBtn = new JButton("余额充值");
		btnPanel.add(rechargeBtn, gbc);

		modifyBtn = new JButton("修改个人信息");
		btnPanel.add(modifyBtn, gbc);

		JButton refashBtn = new JButton("刷新");
		btnPanel.add(refashBtn, gbc);

		exitBtn = new JButton("退出系统");
		btnPanel.add(exitBtn, gbc);
		add(btnPanel, BorderLayout.EAST);

		centerPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets.set(15, 15, 15, 15);

		userLayout = new CardLayout();
		innerPanel = new JPanel(userLayout);

		innerPanel.add(RENTAL_INFO_PANEL, getRentalInfoPanel());

		innerPanel.add(RENTAL_PANEL, getRentalPanel());

		label = new JLabel("你还没有租借任何移动电源！", JLabel.CENTER);
		label.setFont(Main.TITLE_FONT);
		innerPanel.add(EMPTY_PANEL, label);

		innerPanel.add(LOG_PANEL, getLogPanel());

		centerPanel.add(innerPanel, gbc);
		centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		add(centerPanel, BorderLayout.CENTER);
	}

	private JPanel getLogPanel() {
		JPanel logPanel = new JPanel(new BorderLayout());
		SearchPanel searchPanel = new SearchPanel(mainFrame, SearchPanel.LOG_MODE);
		searchPanel.addOnClickListener(new SearchPanel.OnClickListener() {

			@Override
			public SearchResult onSearchButtonClick(int searchMode, int searchIndex, String content) {
				return logTableModel.changeData(searchIndex, content);
			}

			@Override
			public void onCloseButtonClick(int searchMode) {
				logTableModel.changeData(LogService.queryLogs(userLogin));
			}
		});
		logPanel.add(searchPanel, BorderLayout.NORTH);
		logTableModel = new LogTableModel(LogTableModel.USER_MODE);
		logTable = new JTable(logTableModel);
		logTableModel.setPreferredWidth(logTable.getColumnModel());
		logTable.addMouseListener(logTableModel.new DoubleClick(mainFrame));
		logTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		logTable.setRowHeight(30);
		logPanel.add(new JScrollPane(logTable));
		return logPanel;
	}

	private JPanel getRentalPanel() {
		JPanel rentalPanel = new JPanel(new BorderLayout());

		JPanel northPanel = new JPanel(new GridLayout(2, 1));
		JLabel label = new JLabel("请选择您想借的移动电源：");
		label.setFont(Main.MIDDLE_FONT);
		northPanel.add(label);
		SearchPanel searchPanel = new SearchPanel(mainFrame, SearchPanel.POWER_MODE);
		searchPanel.addOnClickListener(new SearchPanel.OnClickListener() {

			@Override
			public SearchResult onSearchButtonClick(int searchMode, int searchIndex, String content) {
				return powerTableModel.changeData(searchIndex, content);
			}

			@Override
			public void onCloseButtonClick(int searchMode) {
				powerTableModel.changeData(PowerService.getInstance().getAllPowers());
			}
		});
		northPanel.add(searchPanel);

		rentalPanel.add(northPanel, BorderLayout.NORTH);

		// 获取所有移动电源
		powerTableModel = new PowerTableModel(BasicTableModel.USER_MODE);

		rentalTable = new JTable(powerTableModel);
		rentalTable.addMouseListener(powerTableModel.new DoubleClick(mainFrame));
		rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rentalTable.setRowHeight(30);
		rentalTable.getSelectionModel().addListSelectionListener(e -> {
			if (rentalTable.getSelectedRow() != -1) {
				Power power = powerTableModel.getData().get(rentalTable.getSelectedRow());
				lentbtn.setEnabled(!power.hasStatus(Power.BORROWED | Power.NO_POWER | Power.BROKEN));
			}
		});
		rentalPanel.add(new JScrollPane(rentalTable), BorderLayout.CENTER);

		lentbtn = new JButton("借选中的移动电源");
		lentbtn.setEnabled(false);
		rentalPanel.add(lentbtn, BorderLayout.SOUTH);
		return rentalPanel;
	}

	private JPanel getRentalInfoPanel() {
		JLabel label;

		JPanel rentalPanel = new JPanel(new BorderLayout());
		label = new JLabel("已经租借的移动电源：", JLabel.LEFT);
		label.setFont(Main.MIDDLE_FONT);
		rentalPanel.add(label, BorderLayout.NORTH);

		userPowerTableModel = new UserPowerTableModel(e -> {
			int timeSpan = DateUtil.getTimeSpan(log.getStartDate(), System.currentTimeMillis());
			double cost = Main.getCost(timeSpan);
			if (userLogin.getBalance() > cost) {
				if (JOptionPane.showConfirmDialog(mainFrame, String.format("你确定要归还该移动电源吗？\n使用时间：%d小时，需要付费： %.2f元",
						timeSpan, cost)) == JOptionPane.YES_OPTION) {
					giveBack();
				}
			} else {
				double leftBalance = userLogin.getBalance() - cost;
				if (leftBalance >= -10) {
					if (JOptionPane.showConfirmDialog(mainFrame, String.format(
							"你的账户余额为%.2f。使用时长：%d小时，还该移动电源需要花费%.2f元。\n归还之后你的账户余额为%.2f\n如果账户余额小于0元每天扣除10点信用分。负面影响请看帮助页面。",
							userLogin.getBalance(), timeSpan, cost, leftBalance)) == JOptionPane.YES_OPTION) {
						giveBack();
					}
				} else {
					JOptionPane.showMessageDialog(mainFrame, "余额不足！");
				}
			}
		});
		userPowerTable = new JTable(userPowerTableModel);
		TableColumn column = userPowerTable.getColumnModel().getColumn(4);
		column.setCellRenderer(userPowerTableModel.new ButtonTableCellRenderer());
		column.setCellEditor(userPowerTableModel.new ButtonTableCellEditor());
		userPowerTableModel.setPreferredWidth(userPowerTable.getColumnModel());
		userPowerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userPowerTable.setRowHeight(30);

		rentalPanel.add(new JScrollPane(userPowerTable), BorderLayout.CENTER);

		rentalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return rentalPanel;
	}

	private void giveBack() {
		Power power = PowerService.getInstance().getPowerById(log.getPowerId());
		UserService.getInstance().giveBack(userLogin, power);
		userPowerTableModel.clearLog();
		changePanel(EMPTY_PANEL);
		log = null;
		powerTableModel.changeData(PowerService.getInstance().getAllPowers());
		logTableModel.changeData(LogService.getAllLogs());
	}

	public void setUserLogin(User userLogin) {
		if (userLogin != null) {
			this.userLogin = userLogin;
			idLabel.setText(userLogin.getId());
			nameLabel.setText(userLogin.getName());
			updateLabel();
			log = LogService.getLog(userLogin);
			if (log == null) {
				changePanel(EMPTY_PANEL);
			} else {
				userPowerTableModel.setLog(log);
				changePanel(RENTAL_INFO_PANEL);
			}
			// 获取用户的所有订单
			logTableModel.changeData(LogService.getAllLogs());
			// 获得所有电源
			powerTableModel.changeData(PowerService.getInstance().getAllPowers());

		}
	}

	private void updateLabel() {
		balanceLabel.setText(userLogin.getBalance() + "元");
		creditLabel.setText(userLogin.getCredit() + "分");
	}

	private void changePanel(String panelName) {
		userLayout.show(innerPanel, panelName);
	}

	public User getUserLogin() {
		return userLogin;
	}

	private void addListener() {
		mainBtn.addActionListener(this);
		rentalBtn.addActionListener(this);
		logBtn.addActionListener(this);
		rechargeBtn.addActionListener(this);
		modifyBtn.addActionListener(this);
		exitBtn.addActionListener(this);

		lentbtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(mainBtn)) {
			if (log != null) {
				changePanel(RENTAL_INFO_PANEL);
			} else {
				changePanel(EMPTY_PANEL);
			}
		} else if (obj.equals(rentalBtn)) {
			changePanel(RENTAL_PANEL);
		} else if (obj.equals(logBtn)) {
			changePanel(LOG_PANEL);
		} else if (obj.equals(rechargeBtn)) {
			rechargeBalance();
		} else if (obj.equals(modifyBtn)) {
			modifyUserInformation();
		} else if (obj.equals(exitBtn)) {
			mainFrame.showPanel(PanelName.LOGIN);
			userLogin = null;
		} else if (obj.equals(lentbtn)) {
			lentPower();
		}

	}

	private void lentPower() {
		if (userLogin.getBalance() < -10 || userLogin.getCredit() < 50) {
			JOptionPane.showInternalMessageDialog(mainFrame, "租借失败，余额或信用分不足！");
			return;
		}
		Power power = powerTableModel.getData().get(rentalTable.getSelectedRow());
		if (UserService.getInstance().borrow(userLogin, power)) {
			JOptionPane.showMessageDialog(mainFrame, "租借" + power.getId() + "成功！");
			log = LogService.getLog(userLogin);
			userPowerTableModel.setLog(log);
			powerTableModel.changeData(PowerService.getInstance().getAllPowers());
		} else {
			JOptionPane.showMessageDialog(mainFrame, "租借失败，你已经租借了一个了！");
		}
	}

	private void modifyUserInformation() {
		String password = JOptionPane.showInputDialog(mainFrame, "请输入你的密码：");
		if (password != null) {
			if (password.equals(userLogin.getPassword())) {
				int result = new ModifyInformation(mainFrame, userLogin).getResult();
				if (result == ModifyInformation.MODIFY_OPTION) {
					JOptionPane.showMessageDialog(mainFrame, "修改成功，请重新登录！");
					mainFrame.showPanel(PanelName.LOGIN);
				}
			} else {
				JOptionPane.showMessageDialog(mainFrame, "密码错误！");
			}
		}
	}

	private void rechargeBalance() {
		String input = JOptionPane.showInputDialog(mainFrame, "请输入你要充值的金额：");
		if (input != null && !input.equals("")) {
			try {
				double balance = Double.parseDouble(input);
				userLogin.setBalance(userLogin.getBalance() + balance);
				UserService.getInstance().modify(userLogin);
				JOptionPane.showMessageDialog(mainFrame, "充值成功！你现在的账户余额为：" + userLogin.getBalance());
				updateLabel();
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(mainFrame, "你输入的不是数字！");
			}
		}
	}
}
