package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

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

import team.skadi.rental.service.LogService;
import team.skadi.rental.service.ManagerService;
import team.skadi.rental.service.PowerService;

@SuppressWarnings("serial")
public class ManagerPanel extends JPanel {

	MainFrame mainFrame;
	private JLabel idLabel;
	private JLabel managerLabel;
	private JTabbedPane tabbedPane;
	private JButton addBtn;
	private JButton delBtn;
	private JButton moitfyBtn;
	private JTable powerTable;
	private JTable userTable;
	private PowerTableModel powerTableModel;
	private UserTableModel userTableModel;

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

		label = new JLabel("账号：");
		idLabel = new JLabel();
		northPanel.add(label);
		northPanel.add(idLabel);
		northPanel.add(Box.createHorizontalGlue());
		label = new JLabel("管理员：");
		managerLabel = new JLabel();
		northPanel.add(label);
		northPanel.add(managerLabel);
		northPanel.add(Box.createHorizontalGlue());
		add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets.set(15, 15, 15, 15);

		userTableModel = new UserTableModel(ManagerService.getInstance().getUserList());
		tabbedPane = new JTabbedPane();
		userTable = new JTable(userTableModel);
		userTable.setRowHeight(30);
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabbedPane.add("用户列表", new JScrollPane(userTable));

		powerTableModel = new PowerTableModel(PowerService.getInstance().getAllPowers());
		powerTable = new JTable(powerTableModel);
		powerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		powerTable.setRowHeight(30);
		tabbedPane.add("充电宝列表", new JScrollPane(powerTable));
		
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
		JButton refash = new JButton("刷新");
		gbc.insets.bottom = 0;
		eastPanel.add(refash, gbc);
		add(eastPanel, BorderLayout.EAST);
	}

	private void addListener() {

	}
}
