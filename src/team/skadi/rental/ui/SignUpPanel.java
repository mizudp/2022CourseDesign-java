package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
public class SignUpPanel extends JPanel implements ActionListener {

	MainFrame mainFrame;
	private JTextField nameField;
	private JTextField phoneNumField;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private JButton finishBtn;
	private JButton returnBtn;
	private ImageButton helpBtn;

	public SignUpPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		bulidLayout();
		addListener();
	}

	private void bulidLayout() {
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel(new GridBagLayout());
		JLabel label;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridy = 0;
		gbc.gridwidth = 2;
		label = new JLabel("欢迎注册", JLabel.CENTER);
		label.setFont(Main.TITLE_FONT);
		centerPanel.add(label, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.insets.top = 34;
		label = new JLabel("用户名：", JLabel.CENTER);
		centerPanel.add(label, gbc);
		nameField = new JTextField(20);
		centerPanel.add(nameField, gbc);

		gbc.gridy++;
		gbc.insets.top = 24;
		label = new JLabel("电话号码：", JLabel.CENTER);
		centerPanel.add(label, gbc);
		phoneNumField = new JTextField(20);
		centerPanel.add(phoneNumField, gbc);

		gbc.gridy++;
		label = new JLabel("邮箱(选填)：", JLabel.CENTER);
		centerPanel.add(label, gbc);
		emailField = new JTextField(20);
		centerPanel.add(emailField, gbc);

		gbc.gridy++;
		label = new JLabel("输入密码：", JLabel.CENTER);
		centerPanel.add(label, gbc);
		passwordField = new JPasswordField(20);
		centerPanel.add(passwordField, gbc);

		gbc.gridy++;
		label = new JLabel("重复密码：");
		centerPanel.add(label, gbc);
		passwordField2 = new JPasswordField(20);
		centerPanel.add(passwordField2, gbc);

		gbc.gridy++;
		gbc.gridwidth = 2;
		JPanel btnPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.gridy = 0;
		gbc2.weightx = 1;
		gbc2.insets.set(0, 38, 0, 19);
		finishBtn = new JButton("完成");
		btnPanel.add(finishBtn, gbc2);

		gbc2.insets.set(0, 19, 0, 38);
		returnBtn = new JButton("返回");
		btnPanel.add(returnBtn, gbc2);
		centerPanel.add(btnPanel, gbc);

		add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		helpBtn = new ImageButton("帮助", "res/help.png");
		southPanel.add(helpBtn);
		add(southPanel, BorderLayout.SOUTH);

	}

	private void addListener() {
		nameField.addKeyListener(new EnterPress(phoneNumField));
		phoneNumField.addKeyListener(new EnterPress(emailField));
		emailField.addKeyListener(new EnterPress(passwordField));
		PasswordKeyAdapter passwordKeyAdapter = new PasswordKeyAdapter() {
			@Override
			protected void onEnterPressed(KeyEvent e) {
				if (e.getSource().equals(passwordField)) {
					passwordField2.requestFocus();
				} else {
					finish();
				}
			}
		};
		passwordField.addKeyListener(passwordKeyAdapter);
		passwordField2.addKeyListener(passwordKeyAdapter);
		returnBtn.addActionListener(this);
		finishBtn.addActionListener(this);
		helpBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(finishBtn)) {
			finish();
		} else if (source.equals(returnBtn)) {// returnBtn
			mainFrame.showPreviousPanel();
		} else if (source.equals(helpBtn)) {
			mainFrame.showPanel(PanelName.SIGN_UP, PanelName.HELP);
		}
	}

	private void finish() {
		String name = nameField.getText();
		if (name.equals("")) {
			JOptionPane.showMessageDialog(mainFrame, "你还没有输入你的姓名呢！");
			return;
		}
		String phoneNum = phoneNumField.getText();
		String email = emailField.getText();
		if (phoneNum.equals("") && email.equals("")) {
			JOptionPane.showMessageDialog(mainFrame, "邮箱和电话号码二选一");
			return;
		}
		String pwd1 = new String(passwordField.getPassword());
		String pwd2 = new String(passwordField2.getPassword());
		if (pwd1.equals("") || pwd2.equals("")) {
			JOptionPane.showMessageDialog(mainFrame, "你还没有输入你的密码呢！");
			return;
		}
		if (!pwd1.equals(pwd2)) {
			JOptionPane.showMessageDialog(mainFrame, "你两次输入的密码不相同！");
			return;
		}
		User user = UserService.getInstance().signIn(name, pwd2, phoneNum, email);
		JOptionPane.showMessageDialog(mainFrame, "注册成功，欢迎使用本电源租凭系统！\n你的账户是：" + user.getId() + "。请牢记你的登录账号和密码");
		mainFrame.showPreviousPanel();
		nameField.setText("");
		phoneNumField.setText("");
		emailField.setText("");
		passwordField.setText("");
		passwordField2.setText("");
	}

	private class EnterPress extends KeyAdapter {

		private JTextField textField;

		public EnterPress(JTextField textField) {
			this.textField = textField;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				textField.requestFocus();
			}
		}
	}
}
