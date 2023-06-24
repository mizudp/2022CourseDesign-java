package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import team.skadi.rental.bean.Log;
import team.skadi.rental.utils.DateUtil;

@SuppressWarnings("serial")
public class LogOption extends OptionDialog {

	private Log log;

	public LogOption(JFrame frame, Log log) {
		super(frame, READ_ONLY_MODE);
		this.log = log;
		init();
	}

	@Override
	protected void bulidLayout() {
		JPanel centerPanel = new JPanel(new GridLayout(10, 1));
		JLabel label;

		label = new JLabel("用户id：", JLabel.LEFT);
		centerPanel.add(label);
		JTextField useridField = new JTextField();
		useridField.setText(log.getUserId());
		useridField.setEditable(false);
		centerPanel.add(useridField);

		label = new JLabel("移动电源id：", JLabel.LEFT);
		centerPanel.add(label);
		JTextField powerField = new JTextField();
		powerField.setText(log.getPowerId());
		powerField.setEditable(false);
		centerPanel.add(powerField);

		label = new JLabel("租借时间：", JLabel.LEFT);
		centerPanel.add(label);
		JTextField startDateField = new JTextField();
		startDateField.setText(DateUtil.format(log.getStartDate()));
		startDateField.setEditable(false);
		centerPanel.add(startDateField);

		label = new JLabel("归还时间：", JLabel.LEFT);
		centerPanel.add(label);
		JTextField endDateField = new JTextField();
		if (log.getEndDate() == 0) {
			endDateField.setText("尚未归还");
		} else {
			endDateField.setText(DateUtil.format(log.getEndDate()));
		}
		endDateField.setEnabled(false);
		centerPanel.add(endDateField);

		label = new JLabel("订单内容：", JLabel.LEFT);
		centerPanel.add(label);
		JTextField contextField = new JTextField();
		contextField.setText(log.getContext());
		contextField.setEditable(false);
		centerPanel.add(contextField);
		
		add(centerPanel, BorderLayout.CENTER);
	}

}
