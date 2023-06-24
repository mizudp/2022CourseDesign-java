package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import team.skadi.rental.bean.Power;
import team.skadi.rental.service.ManagerService;

/**
 * 确定按钮按下时会更新数据库
 * 
 * @author mizulk
 *
 */
@SuppressWarnings("serial")
public class PowerOption extends OptionDialog implements ChangeListener {

	private Power power;
	private JTextField idField;
	private JSpinner leftsSpinner;
	private SpinnerNumberModel spinnerNumberModel;
	private ArrayList<JCheckBox> checkBoxs;

	private static final boolean[] ENABLE = { true, false, false, true };

	public PowerOption(JFrame frame, int mode, Power power) {
		super(frame, mode);
		this.power = power;
		checkBoxs = new ArrayList<>();
		init();
	}

	public PowerOption(JFrame frame) {
		this(frame, ADD_MODE, null);
	}

	@Override
	protected void bulidLayout() {
		JPanel centerPanel = new JPanel(new GridLayout(6, 1));

		JLabel label;
		label = new JLabel("移动电源id：", JLabel.LEFT);
		centerPanel.add(label);
		idField = new JTextField(10);
		centerPanel.add(idField);

		label = new JLabel("剩余电量[0,100]：", JLabel.LEFT);
		centerPanel.add(label);
		spinnerNumberModel = new SpinnerNumberModel(0, 0, 100, 1);
		leftsSpinner = new JSpinner(spinnerNumberModel);
		leftsSpinner.addChangeListener(this);
		centerPanel.add(leftsSpinner);

		label = new JLabel("当前状态：", JLabel.LEFT);
		centerPanel.add(label);

		JPanel statusPanel = new JPanel(new GridLayout(1, ENABLE.length));
		for (int i = 0; i < ENABLE.length; i++) {
			JCheckBox checkBox = new JCheckBox(Power.STATUS_NAME[i]);
			statusPanel.add(checkBox);
			checkBoxs.add(checkBox);
		}
		centerPanel.add(statusPanel);

		initData();

		add(centerPanel, BorderLayout.CENTER);
	}

	private void initData() {
		idField.setEditable(false);
		leftsSpinner.setEnabled(mode == MODIFY_MODE || mode == ADD_MODE);
		if (power != null) {
			idField.setText(power.getId());
			spinnerNumberModel.setValue(power.getLeft());
			for (int i = 0, bin = 1; i < ENABLE.length; i++) {
				JCheckBox checkBox = checkBoxs.get(i);
				checkBox.setSelected(power.hasStatus(bin));
				if (mode == READ_ONLY_MODE || power.hasStatus(Power.BORROWED)) {
					checkBox.setEnabled(false);
				} else {
					checkBox.setEnabled(ENABLE[i]);
				}
				bin <<= 1;
			}
		} else {
			idField.setEditable(false);
			idField.setText("该id由系统生成");
			checkBoxs.get(0).setSelected(true);
			for (int i = 0; i < ENABLE.length; i++) {
				checkBoxs.get(i).setEnabled(false);
			}
		}
	}

	public Power getPower() {
		setVisible(true);
		return power;
	}

	@Override
	protected boolean onConfirmBtnClick() {
		if (mode == MODIFY_MODE || mode == ADD_MODE) {
			if (power == null) {
				power = new Power();
			}
			power.setId(idField.getText());
			power.setLeft((int) spinnerNumberModel.getNumber());
			for (int i = 0, bin = 1; i < ENABLE.length; i++) {
				if (checkBoxs.get(i).isSelected()) {
					power.addStatus(bin);
				} else {
					power.removeStatus(bin);
				}
				bin <<= 1;
			}
		}
		if (mode == ADD_MODE) {
			ManagerService.getInstance().addPower(power);
		} else {
			ManagerService.getInstance().modifyPower(power);
		}
		option = MODIFY_OPTION;
		return true;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		checkBoxs.get(2).setSelected(spinnerNumberModel.getNumber().intValue() == 0);
	}
}
