package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import team.skadi.rental.bean.User;

@SuppressWarnings("serial")
public class ModifyInformation extends BasicDialog {

	public ModifyInformation(MainFrame mainFrame, User userLogin) {
		super(mainFrame, "修改个人信息");
		init();
	}

	@Override
	protected void bulidLayout() {
		JPanel centerPanel = new JPanel(new GridLayout(8, 1));
		JLabel label;
		label = new JLabel("", JLabel.LEFT);
		centerPanel.add(label);
		JTextField nameField = new JTextField();
		centerPanel.add(nameField);
		label = new JLabel("", JLabel.LEFT);
		centerPanel.add(label);
		JTextField emailField = new JTextField();
		centerPanel.add(emailField);
		label = new JLabel("", JLabel.LEFT);
		JTextField field = new JTextField();
		centerPanel.add(field);
		add(centerPanel, BorderLayout.CENTER);
	}

	@Override
	protected void addListener() {

	}

}
