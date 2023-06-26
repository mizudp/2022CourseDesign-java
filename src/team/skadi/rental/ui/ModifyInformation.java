package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import team.skadi.rental.bean.User;
import team.skadi.rental.service.UserService;

@SuppressWarnings("serial")
public class ModifyInformation extends BasicDialog {

	public static final int MODIFY_OPTION = 1, NOT_MODIFY_OPTION = 2;

	private User userLogin;
	private JTextField nameField;
	private JTextField phoneNumField;
	private JTextField emailField;
	private JTextField passwordField;

	public ModifyInformation(MainFrame mainFrame, User userLogin) {
		super(mainFrame, "修改个人信息");
		this.userLogin = userLogin;
		init();
	}

	@Override
	protected void bulidLayout() {
		JPanel centerPanel = new JPanel(new GridLayout(8, 1));
		JLabel label;

		label = new JLabel("用户名：", JLabel.LEFT);
		centerPanel.add(label);
		nameField = new JTextField();
		nameField.setText(userLogin.getName());
		centerPanel.add(nameField);

		label = new JLabel("电话：", JLabel.LEFT);
		centerPanel.add(label);
		phoneNumField = new JTextField();
		phoneNumField.setText(userLogin.getPhoneNumber());
		phoneNumField.addKeyListener(new NumberOnly());
		centerPanel.add(phoneNumField);

		label = new JLabel("邮箱：", JLabel.LEFT);
		centerPanel.add(label);
		emailField = new JTextField();
		emailField.setText(userLogin.getEmail());
		centerPanel.add(emailField);

		label = new JLabel("密码：", JLabel.LEFT);
		centerPanel.add(label);
		passwordField = new JTextField();
		passwordField.setText(userLogin.getPassword());
		passwordField.addKeyListener(new PasswordKeyAdapter());
		centerPanel.add(passwordField);

		add(centerPanel, BorderLayout.CENTER);
	}

	@Override
	protected boolean onConfirmBtnClick() {
		String name = nameField.getText();
		String phoneNum = phoneNumField.getText();
		String email = emailField.getText();
		String password = passwordField.getText();
		if (name.equals(userLogin.getName()) && phoneNum.equals(userLogin.getPhoneNumber())
				&& email.equals(userLogin.getEmail()) && password.equals(userLogin.getPassword())) {
			JOptionPane.showMessageDialog(getOwner(), "请完善信息");
			option = NOT_MODIFY_OPTION;
			return true;
		}
		if (!Pattern.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", email)) {
			JOptionPane.showMessageDialog(getOwner(), "邮箱格式错误！");
			option = NOT_MODIFY_OPTION;
			return false;
		}
		userLogin.setName(nameField.getText());
		userLogin.setPhoneNumber(phoneNumField.getText());
		userLogin.setEmail(emailField.getText());
		userLogin.setPassword(passwordField.getText());
		UserService.getInstance().modify(userLogin);
		option = MODIFY_OPTION;
		return true;
	}
}
