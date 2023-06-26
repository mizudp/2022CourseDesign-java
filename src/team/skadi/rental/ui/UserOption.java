package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import team.skadi.rental.bean.User;
import team.skadi.rental.service.ManagerService;
import team.skadi.rental.service.UserService;

@SuppressWarnings("serial")
public class UserOption extends OptionDialog {

	private User user;
	private JTextField idField;
	private JTextField nameField;
	private JTextField phoneNumberField;
	private JTextField emailField;
	private JTextField passwordField;
	private JTextField balanceField;
	private JTextField creditField;

	public UserOption(JFrame frame, int mode, User user) {
		super(frame, mode);
		this.user = user;
		init();
	}

	public UserOption(JFrame frame) {
		this(frame, ADD_MODE, null);
	}

	@Override
	protected void bulidLayout() {
		JPanel centerPanel = new JPanel(new GridLayout(14, 1));
		JLabel label;
		label = new JLabel("用户id：", JLabel.LEFT);
		centerPanel.add(label);
		idField = new JTextField(10);
		centerPanel.add(idField);

		label = new JLabel("用户姓名：", JLabel.LEFT);
		centerPanel.add(label);
		nameField = new JTextField(10);
		centerPanel.add(nameField);

		NumberOnly l = new NumberOnly();
		label = new JLabel("电话号码：", JLabel.LEFT);
		centerPanel.add(label);
		phoneNumberField = new JTextField(10);
		phoneNumberField.addKeyListener(l);
		centerPanel.add(phoneNumberField);

		label = new JLabel("邮箱：", JLabel.LEFT);
		centerPanel.add(label);
		emailField = new JTextField(10);
		centerPanel.add(emailField);

		label = new JLabel("密码：", JLabel.LEFT);
		centerPanel.add(label);
		passwordField = new JTextField(10);
		passwordField.addKeyListener(new PasswordKeyAdapter());
		centerPanel.add(passwordField);

		label = new JLabel("余额(元)：", JLabel.LEFT);
		centerPanel.add(label);
		balanceField = new JTextField(10);
		balanceField.addKeyListener(l);
		centerPanel.add(balanceField);

		label = new JLabel("信用分：", JLabel.LEFT);
		centerPanel.add(label);
		creditField = new JTextField(10);
		creditField.addKeyListener(l);
		centerPanel.add(creditField);

		initData();

		add(centerPanel, BorderLayout.CENTER);
	}

	private void initData() {
		boolean enable = mode == MODIFY_MODE || mode == ADD_MODE;
		idField.setEditable(false);
		nameField.setEditable(enable);
		phoneNumberField.setEditable(enable);
		emailField.setEditable(enable);
		passwordField.setEditable(enable);
		balanceField.setEditable(enable);
		creditField.setEditable(enable);
		if (user != null) {
			idField.setText(user.getId());
			nameField.setText(user.getName());
			phoneNumberField.setText(user.getPhoneNumber());
			emailField.setText(user.getEmail());
			passwordField.setText(user.getPassword());
			balanceField.setText(String.valueOf(user.getBalance()));
			creditField.setText(String.valueOf(user.getCredit()));
		} else {
			idField.setText("该id由系统生成");
			balanceField.setText("0");
			creditField.setText("0");
		}
	}

	public User getUser() {
		setVisible(true);
		return user;
	}

	@Override
	protected boolean onConfirmBtnClick() {
		if (mode == MODIFY_MODE || mode == ADD_MODE) {
			if (user == null) {
				user = new User();
			}
			String name = nameField.getText();
			String phoneNumber = phoneNumberField.getText();
			String email = emailField.getText();
			String password = passwordField.getText();
			double balance = user.getBalance();
			int credit = user.getCredit();
			try {
				String balanceStr = balanceField.getText();
				balance = balanceStr.equals("") ? 0 : Double.parseDouble(balanceStr);
				String creditStr = creditField.getText();
				credit = creditStr.equals("") ? 0 : Integer.parseInt(creditStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (mode == MODIFY_MODE && name.equals(user.getName()) && phoneNumber.equals(user.getPhoneNumber())
					&& email.equals(user.getEmail()) && password.equals(user.getPassword())
					&& balance == user.getBalance() && credit == user.getCredit()) {
				option = NOT_MODIFY_OPTION;
				JOptionPane.showMessageDialog(getOwner(), "数据未修改！");
				return true;
			}
			if (mode == ADD_MODE
					&& (name.equals("") || (phoneNumber.equals("") && email.equals("")) || password.equals(""))) {
				option = NOT_MODIFY_OPTION;
				JOptionPane.showMessageDialog(getOwner(), "检测到数据为空！");
				user = null;
				return true;
			}
			user.setName(name);
			user.setPhoneNumber(phoneNumber);
			user.setEmail(email);
			user.setPassword(password);
			user.setBalance(balance);
			user.setCredit(credit);
			if (mode == MODIFY_MODE) {
				UserService.getInstance().modify(user);
				JOptionPane.showMessageDialog(getOwner(), "修改成功！");
			} else if (mode == ADD_MODE) {
				user = ManagerService.getInstance().addUser(user);
				JOptionPane.showMessageDialog(getOwner(), "增加成功！");
			}
		}
		option = MODIFY_OPTION;
		return true;
	}
}
