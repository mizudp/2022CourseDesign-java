package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import team.skadi.rental.Main;

@SuppressWarnings("serial")
public class HelpPanel extends JPanel implements ActionListener {

	MainFrame mainFrame;
	private HashMap<String, String> hashMap;
	private JLabel titleLabel;
	private JTextArea textArea;
	private static JButton exitBtn;

	public HelpPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initData();
		bulidLayout();
	}

	private void initData() {
		hashMap = new HashMap<>();
		hashMap.put("关于注册", "注册时，邮箱和手机号码二选一");
		hashMap.put("关于信誉分", "当余额小于零时，信誉分每天减少10。当信誉分小于50时，不能租借移动电源！");
		hashMap.put("关于移动电源租借", "每个账户只能借一个移动电源。");
	}

	private void bulidLayout() {
		setLayout(new BorderLayout());
		titleLabel = new JLabel("欢迎来到帮助页面", JLabel.CENTER);
		titleLabel.setFont(Main.MIDDLE_FONT);
		add(titleLabel, BorderLayout.NORTH);

		textArea = new JTextArea();
		textArea.setText("欢迎来到帮助界面，请选择右边你想要查询的功能。");
		textArea.setEditable(false);
		textArea.setBackground(new Color(230, 230, 230));
		textArea.setBorder(BorderFactory.createLoweredBevelBorder());
		add(textArea, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.weightx = 1;
		gbc.insets.set(0, 10, 10, 10);
		Set<String> keySet = hashMap.keySet();
		for (String string : keySet) {
			JButton button = new JButton(string);
			button.addActionListener(this);
			btnPanel.add(button, gbc);
		}
		exitBtn = new JButton("返回");
		exitBtn.addActionListener(this);
		btnPanel.add(exitBtn, gbc);
		btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
		add(btnPanel, BorderLayout.EAST);

		JLabel label = new JLabel("如有问题请致电11451419198", JLabel.RIGHT);
		add(label, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (btn.equals(exitBtn)) {
			mainFrame.showPreviousPanel();
			return;
		}
		String text = btn.getText();
		textArea.setText(hashMap.get(text));
		titleLabel.setText(text);
	}

}
