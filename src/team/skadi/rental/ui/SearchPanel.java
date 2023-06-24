package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SearchPanel extends JPanel implements ActionListener {

	public static final int USER_MODE = 0, POWER_MODE = 1, LOG_MODE = 2;
	public static final int USER_ID = 0, USER_BALANCE = 1, USER_CREDIT = 2;
	public static final int POWER_ID = 0, POWER_STATUS = 1, POWER_LEFT = 2;
	public static final int LOG_USERID = 0, LOG_POWERID = 1;

	private JLabel label;
	private JTextField textField;
	private JButton searchBtn;
	private JButton closeBtn;
	private OnClickListener l;

	private boolean isSeraching;
	private int searchMode;
	private int[] searchIndex = new int[3];
	private String[][] optionStrings;
	MainFrame mainFrame;

	public enum SearchResult {
		HAVE_RESULT, NO_RESULT, NAN;
	}

	public SearchPanel(MainFrame mainFrame, int searchMode) {
		searchIndex[LOG_MODE] = 1;
		init(mainFrame, 0, 0, searchMode);
		optionStrings = new String[][] { { "按id查询", "按余额查询", "按信用点查询" }, { "按id查询", "按状态查询", "按剩余电量查询" },
				{ null, "按照移动电源id查询" } };
	}

	public SearchPanel(MainFrame mainFrame, int leftPadding, int rightPadding) {
		init(mainFrame, leftPadding, rightPadding, USER_MODE);
		optionStrings = new String[][] { { "按id查询", "按余额查询", "按信用点查询" }, { "按id查询", "按状态查询", "按剩余电量查询" },
				{ "按用户id查询", "按照移动电源id查询" } };
	}

	private void init(MainFrame mainFrame, int leftPadding, int rightPadding, int searchMode) {
		this.mainFrame = mainFrame;
		bulidLayout(leftPadding, rightPadding);
		addListener();
		setSearchMode(searchMode);
	}

	public void setSearchMode(int searchMode) {
		this.searchMode = searchMode;
		updateLabel();
	}

	private void updateLabel() {
		label.setText("查询模式(" + getSelectedName(searchMode, searchIndex[searchMode]) + "):");
		textField.setText("");
	}

	public void addOnClickListener(OnClickListener l) {
		this.l = l;
	}

	public int getSearchMode() {
		return searchMode;
	}

	public void close() {
		if (isSeraching) {
			isSeraching = false;
			l.onCloseButtonClick(searchMode);
			closeBtn.setEnabled(isSeraching);
		}
	}

	private void bulidLayout(int leftPadding, int rightPadding) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 0;
		gbc.weightx = .1;
		gbc.insets.set(10, 0, 10, 0);
		gbc.insets.left = leftPadding;
		label = new JLabel();
		label.setOpaque(true);
		add(label, gbc);
		gbc.insets.left = 0;
		gbc.weightx = .7;
		textField = new JTextField();
		add(textField, gbc);
		gbc.weightx = .1;
		searchBtn = new JButton("查询");
		add(searchBtn, gbc);
		gbc.weightx = .1;
		gbc.insets.right = rightPadding;
		closeBtn = new JButton("关闭");
		closeBtn.setEnabled(false);
		add(closeBtn, gbc);
	}

	private void addListener() {
		searchBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		label.addMouseListener(new MouseAdapter() {
			Color defaultColor = Color.decode("#eeeeee");
			Color clickColor = Color.decode("#b8cfe5");
			Color hoverColor = Color.decode("#cce3ff");

			@Override
			public void mousePressed(MouseEvent e) {
				label.setBackground(clickColor);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				label.setBackground(defaultColor);
				int result = new SelectOptionDialog(mainFrame).getResult();
				if (result != BasicDialog.CENCEL_OPTION) {
					searchIndex[searchMode] = result;
					updateLabel();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				label.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setBackground(defaultColor);
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();

				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (l == null)
			return;
		if (e.getSource().equals(searchBtn)) {
			search();
		} else { // closeBtn
			close();
		}
	}

	private void search() {
		if (!textField.getText().equals("")) {
			switch (l.onSearchButtonClick(searchMode, searchIndex[searchMode], textField.getText())) {
			case NAN:
				JOptionPane.showMessageDialog(mainFrame, "你输入的不是数字，请重新输入！");
				break;
			case NO_RESULT:
				JOptionPane.showMessageDialog(mainFrame, "未查找到相关电源");
				break;
			case HAVE_RESULT:
				isSeraching = true;
				break;
			}
			closeBtn.setEnabled(isSeraching);
		}
	}

	public static String getSelectedName(int selectMode, int selectIndex) {
		if (selectMode == USER_MODE) {
			switch (selectIndex) {
			case USER_ID:
				return "用户id";
			case USER_BALANCE:
				return "用户余额";
			case USER_CREDIT:
				return "用户信用分";
			}
		} else if (selectMode == POWER_MODE) {
			switch (selectIndex) {
			case POWER_ID:
				return "移动电源id";
			case POWER_LEFT:
				return "移动电源剩余电量";
			case POWER_STATUS:
				return "移动电源状态";
			}
		} else if (selectMode == LOG_MODE) {
			switch (selectIndex) {
			case LOG_USERID:
				return "订单-用户id";
			case LOG_POWERID:
				return "订单-移动电源id";
			}
		}
		return null;
	}

	public static interface OnClickListener {
		/**
		 * 当查询按钮点击时触发
		 * 
		 * @param searchMode  当前查询模式
		 * @param searchIndex 当前子查询模式
		 * @param content     要查询的内容，不会为空
		 * @return {@link SearchResult}
		 */
		SearchResult onSearchButtonClick(int searchMode, int searchIndex, String content);

		/**
		 * 当关闭按钮点击时触发
		 * 
		 * @param searchMode 当前查询模式
		 */
		void onCloseButtonClick(int searchMode);

	}

	private class SelectOptionDialog extends BasicDialog {

		ArrayList<JRadioButton> radioButtons;

		public SelectOptionDialog(MainFrame mainFrame) {
			super(mainFrame, "请选择：");
			radioButtons = new ArrayList<>();
			init();
		}

		@Override
		protected void bulidLayout() {
			String str = "";
			if (searchMode == USER_MODE) {
				str = "用户";
			} else if (searchMode == POWER_MODE) {
				str = "移动电源";
			} else if (searchMode == LOG_MODE) {
				str = "用户订单";
			}
			JLabel label = new JLabel("请选择" + str + "查询模式：", JLabel.LEFT);

			add(label, BorderLayout.NORTH);
			ButtonGroup buttonGroup = new ButtonGroup();
			String[] selection = optionStrings[searchMode];
			JPanel centerPanel = new JPanel();
			for (int i = 0; i < selection.length; i++) {
				String text = selection[i];
				JRadioButton radioButton = new JRadioButton(text);
				radioButton.setSelected(i == searchIndex[searchMode]);
				radioButton.setVisible(text != null);
				buttonGroup.add(radioButton);
				radioButtons.add(radioButton);
				centerPanel.add(radioButton);
			}
			add(centerPanel, BorderLayout.CENTER);
		}

		@Override
		protected boolean onConfirmBtnClick() {
			for (int i = 0; i < radioButtons.size(); i++) {
				if (radioButtons.get(i).isSelected()) {
					option = i;
					break;
				}
			}
			return true;
		}
	}
}
