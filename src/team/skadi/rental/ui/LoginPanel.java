package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import team.skadi.rental.bean.User;
import team.skadi.rental.service.UserService;
import team.skadi.rental.ui.MainFrame.PanelName;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {

	MainFrame mainFrame;
	private JTextField accounTextField;
	private JPasswordField passwordField;
	private JButton loginBtn;
	private JButton signUpBtn;
	private ImageButton exitBtn;
	private ImageButton managerBtn;

	public LoginPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		bulidLayout();
		addListener();
	}

	public void bulidLayout() {
		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		JLabel label;

		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.insets.bottom = 70;
		label = new JLabel("欢迎使用电源租凭系统", JLabel.CENTER);
		label.setFont(Main.TITLE_FONT);
		centerPanel.add(label, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.insets.right = 10;
		gbc.insets.bottom = 30;
		label = new JLabel("账户：", JLabel.CENTER);
		centerPanel.add(label, gbc);
		gbc.insets.right = 0;
		accounTextField = new JTextField(20);
		centerPanel.add(accounTextField, gbc);

		gbc.gridy++;// 2
		gbc.insets.right = 10;
		gbc.insets.bottom = 40;
		label = new JLabel("密码：", JLabel.CENTER);
		centerPanel.add(label, gbc);
		gbc.insets.right = 0;
		passwordField = new JPasswordField(20);
		centerPanel.add(passwordField, gbc);

		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.insets.bottom = 0;
		JPanel btnPanel = new JPanel(new GridLayout(1, 2, 50, 50));
		loginBtn = new JButton("登录");
		btnPanel.add(loginBtn);
		signUpBtn = new JButton("注册");
		btnPanel.add(signUpBtn);
		centerPanel.add(btnPanel, gbc);

		add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

		managerBtn = new ImageButton("管理员入口", "res/manager.png");
		managerBtn.setTextPosition(JButton.CENTER, JButton.BOTTOM);
		southPanel.add(managerBtn);

		exitBtn = new ImageButton("退出", "res/exit.png");
		exitBtn.setTextPosition(JButton.CENTER, JButton.BOTTOM);
		southPanel.add(exitBtn);

		add(southPanel, BorderLayout.SOUTH);
	}

	private void addListener() {
		accounTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
		signUpBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		managerBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(loginBtn)) {
			login();
		} else if (source.equals(signUpBtn)) {
			mainFrame.showPanel(PanelName.SIGN_UP);
		} else if (source.equals(exitBtn)) {
			mainFrame.dispose();
		} else if (source.equals(managerBtn)) {
			mainFrame.showPanel(PanelName.MANAGER_LOGIN);
		}
	}

	private void login() {
//		String account = accounTextField.getText();
//		String password = new String(passwordField.getPassword());
//		if (account.equals("")) {
//			JOptionPane.showMessageDialog(mainFrame, "你还没有输入账号！");
//			return;
//		}
//		if (password.equals("")) {
//			JOptionPane.showMessageDialog(mainFrame, "你还没有输入密码！");
//			return;
//		}
		User user = UserService.getInstance().login("000001", "123456");
//		User user = UserService.getInstance().login(account, password);
		if (user == null) {
			JOptionPane.showMessageDialog(mainFrame, "账号或者密码错误！");
			return;
		}
		mainFrame.userPanel.setUserLogin(user);
		JOptionPane.showMessageDialog(mainFrame, "登录成功，欢迎你，" + user.getName());
		mainFrame.showPanel(PanelName.USER);
		passwordField.setText("");
	}

}
