package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.service.LogService;
import team.skadi.rental.service.PowerService;

@SuppressWarnings("serial")
public class UserPanel extends JPanel implements ActionListener {

	private User userLogin;

	MainFrame mainFrame;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel balanceLabel;
	private JLabel creditLabel;
	private JButton lentBtn;
	private JButton logBtn;
	private JButton rechargeBtn;
	private JButton modifyBtn;
	private JButton exitBtn;
	private JPanel centerPanel;

	private JTable table;
	private LogTableModel logTableModel;

	public UserPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		buildLayout();
		addListener();
		setUserLogin(new User("000001", 1, "ppap", "1145141919", "123456", 123, "154641@qq.com", 100));
	}

	private void buildLayout() {
		setLayout(new BorderLayout());
		JLabel label;

		JPanel inforPanel = new JPanel();
		inforPanel.setLayout(new BoxLayout(inforPanel, BoxLayout.X_AXIS));

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
		inforPanel.add(Box.createHorizontalStrut(50));

		add(inforPanel, BorderLayout.NORTH);

		JPanel btnPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets.set(0, 15, 0, 15);
		lentBtn = new JButton("借/还充电宝");
		btnPanel.add(lentBtn, gbc);

		gbc.insets.set(25, 15, 0, 15);
		logBtn = new JButton("查看历史订单");
		btnPanel.add(logBtn, gbc);

		rechargeBtn = new JButton("余额充值");
		btnPanel.add(rechargeBtn, gbc);

		modifyBtn = new JButton("修改个人信息");
		btnPanel.add(modifyBtn, gbc);

		exitBtn = new JButton("退出系统");
		btnPanel.add(exitBtn, gbc);
		add(btnPanel, BorderLayout.EAST);

		centerPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets.set(15, 15, 15, 15);
		List<Power> allPowers = PowerService.getInstance().getAllPowers();
		PowerTableModel powerTableModel = new PowerTableModel(allPowers);

		table = new JTable(powerTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(30);

		centerPanel.add(new JScrollPane(table), gbc);
		centerPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		add(centerPanel, BorderLayout.CENTER);
	}

	public void setUserLogin(User userLogin) {
		if (userLogin != null) {
			this.userLogin = userLogin;
			idLabel.setText(userLogin.getId());
			nameLabel.setText(userLogin.getName());
			balanceLabel.setText(userLogin.getBalance() + "元");
			creditLabel.setText(userLogin.getCredit() + "分");
		}
	}

	public User getUserLogin() {
		return userLogin;
	}

	private void addListener() {
		lentBtn.addActionListener(this);
		logBtn.addActionListener(this);
		rechargeBtn.addActionListener(this);
		modifyBtn.addActionListener(this);
		exitBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(lentBtn)) {

		} else if (obj.equals(logBtn)) {
			logTableModel = new LogTableModel(LogService.queryLogs(userLogin));
			table.setModel(logTableModel);
		} else if (obj.equals(rechargeBtn)) {

		} else if (obj.equals(modifyBtn)) {
			new ModifyInformation(mainFrame, userLogin).setVisible(true);
		} else if (obj.equals(exitBtn)) {

		}

	}
}
