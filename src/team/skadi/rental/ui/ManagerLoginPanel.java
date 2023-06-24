package team.skadi.rental.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import team.skadi.rental.Main;
import team.skadi.rental.bean.Manager;
import team.skadi.rental.service.ManagerService;
import team.skadi.rental.ui.MainFrame.PanelName;

@SuppressWarnings("serial")
public class ManagerLoginPanel extends JPanel implements ActionListener {

	private MainFrame mainFrame;
	private JTextField idField;
	private JPasswordField passwordField;
	private JButton loginBtn;
	private JButton returnBtn;

	public ManagerLoginPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		bulidLayout();
		addListener();
	}

	private void bulidLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 1;

		JLabel label;
		gbc.gridwidth = 2;
		gbc.insets.bottom = 70;
		label = new JLabel("管理员入口", JLabel.CENTER);
		label.setFont(Main.TITLE_FONT);
		add(label, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.insets.bottom = 30;
		label = new JLabel("工号：");
		add(label, gbc);
		idField = new JTextField(20);
		add(idField, gbc);

		gbc.gridy++;
		gbc.insets.bottom = 40;
		label = new JLabel("密码：");
		add(label, gbc);
		passwordField = new JPasswordField(20);
		add(passwordField, gbc);

		gbc.gridy++;
		gbc.gridwidth = 2;
		JPanel btnPanel = new JPanel(new GridLayout(1, 2, 50, 50));
		loginBtn = new JButton("登录");
		btnPanel.add(loginBtn);
		returnBtn = new JButton("返回");
		btnPanel.add(returnBtn);
		add(btnPanel, gbc);
	}

	private void addListener() {
		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					passwordField.requestFocus();
				}
			}
		});
		passwordField.addKeyListener(new PasswordKeyAdapter() {
			@Override
			protected void onEnterPressed(KeyEvent e) {
				login();
			}
		});
		loginBtn.addActionListener(this);
		returnBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(loginBtn)) {
			login();
		} else {// returnBtn
			mainFrame.showPreviousPanel();
		}
	}

	private void login() {
		String id = idField.getText();
		if (id.equals("")) {
			JOptionPane.showMessageDialog(mainFrame, "你还没有输入你的工号！");
			return;
		}
		String password = new String(passwordField.getPassword());
		if (password.equals("")) {
			JOptionPane.showMessageDialog(mainFrame, "你还没有输入你的密码！");
			return;
		}
		Manager manager = ManagerService.getInstance().login(id, password);
//		Manager manager = ManagerService.getInstance().login("123456X", "123456");
		if (manager == null) {
			JOptionPane.showMessageDialog(mainFrame, "工号或者密码错误！");
			return;
		}
		mainFrame.managerPanel.setLoginManager(manager);
		JOptionPane.showMessageDialog(mainFrame, "登录成功，欢迎你，" + manager.getName());
		mainFrame.showPanel(PanelName.MANAGER_LOGIN, PanelName.MANAGER);
		passwordField.setText("");
	}

}
