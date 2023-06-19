package team.skadi.rental.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import team.skadi.rental.Main;

@SuppressWarnings("serial")
public class SignUpPanel extends JPanel implements ActionListener {

	MainFrame mianFrame;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private JButton finishBtn;
	private JButton returnBtn;

	public SignUpPanel(MainFrame mainFrame) {
		this.mianFrame = mainFrame;
		bulidLayout();
		addListener();
	}

	private void bulidLayout() {
		setLayout(new GridBagLayout());
		JLabel label;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridy = 0;
		gbc.gridwidth = 2;
		label = new JLabel("欢迎注册", JLabel.CENTER);
		label.setFont(Main.TITLE_FONT);
		add(label, gbc);

		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.weightx = 0;
		gbc.insets.top = 34;
		label = new JLabel("用户名：", JLabel.CENTER);
		add(label, gbc);
		nameField = new JTextField(20);
		add(nameField, gbc);

		gbc.gridy++;
		gbc.insets.top = 24;
		label = new JLabel("输入密码：", JLabel.CENTER);
		add(label, gbc);
		passwordField = new JPasswordField(20);
		add(passwordField, gbc);

		gbc.gridy++;
		label = new JLabel("重复密码：");
		add(label, gbc);
		passwordField2 = new JPasswordField(20);
		add(passwordField2, gbc);

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
		add(btnPanel, gbc);
	}

	private void addListener() {
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					passwordField.requestFocus();
				}
			}
		});
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
		finishBtn.addActionListener(System.out::println);
		returnBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(finishBtn)) {
			finish();
		} else {// returnBtn

		}
	}

	private void finish() {

	}

}
