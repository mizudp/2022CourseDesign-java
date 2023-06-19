package team.skadi.rental.ui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import team.skadi.rental.Main;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	MainFrame mainFrame;
	private JTextField accounTextField;
	private JPasswordField passwordField;
	private JButton loginBtn;
	private JButton signUpBtn;
	private JButton exitBtn;

	public LoginPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		bulidLayout();
		addListener();
	}

	public void bulidLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JLabel label;

		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.insets.bottom = 10;
		label = new JLabel("欢迎使用德莱联盟电源租凭系统", JLabel.CENTER);
		label.setFont(Main.TITLE_FONT);
		add(label, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.insets.right = 10;
		label = new JLabel("账户：", JLabel.CENTER);
		add(label, gbc);
		gbc.insets.right = 0;
		accounTextField = new JTextField(20);
		add(accounTextField, gbc);

		gbc.gridy++;// 2
		gbc.insets.right = 10;
		label = new JLabel("密码：", JLabel.CENTER);
		add(label, gbc);
		gbc.insets.right = 0;
		passwordField = new JPasswordField(20);
		add(passwordField, gbc);

		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.insets.bottom = 0;
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
		loginBtn = new JButton("登录");
		btnPanel.add(loginBtn);
		signUpBtn = new JButton("注册");
		btnPanel.add(signUpBtn);
		exitBtn = new JButton("退出");
		btnPanel.add(exitBtn);
		add(btnPanel, gbc);

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

	}

	private void login() {

	}
}
