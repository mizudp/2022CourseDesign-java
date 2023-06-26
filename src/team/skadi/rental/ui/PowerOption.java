package team.skadi.rental.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	private static final boolean[] ENABLE = { true, false, false, false, true };

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
		spinnerNumberModel = new SpinnerNumberModel(10, 0, 100, 1);
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
			checkBoxs.get(Power.AVAILABLE_INDEX).setEnabled(power.getLeft() > 10);
//			checkBoxs.get(Power.AVAILABLE_INDEX).setEnabled(power.getLeft() > 10);
			checkBoxs.get(Power.BROKEN_INDEX).addActionListener(e -> {
				JCheckBox brokenCheckBox = (JCheckBox) e.getSource();
				boolean selected = !brokenCheckBox.isSelected();
				boolean b = selected && spinnerNumberModel.getNumber().intValue() > 10;
				checkBoxs.get(Power.AVAILABLE_INDEX).setEnabled(b);
				checkBoxs.get(Power.AVAILABLE_INDEX).setSelected(b);
			});
		} else {
			idField.setEditable(false);
			idField.setText("该id由系统生成");
			checkBoxs.get(Power.AVAILABLE_INDEX).setSelected(true);
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
			int left = (int) spinnerNumberModel.getNumber();
			power.setLeft(left);
			checkBoxs.get(Power.NO_POWER_INDEX).setSelected(left == 0);
			checkBoxs.get(Power.LOW_POWER_INDEX).setSelected(left != 0 && left <= 10);
			checkBoxs.get(Power.AVAILABLE_INDEX).setSelected(left > 10);
			for (int i = 0, bin = 1; i < ENABLE.length; i++) {
				if (checkBoxs.get(i).isSelected()) {
					power.addStatus(bin);
				} else {
					power.removeStatus(bin);
				}
				bin <<= 1;
			}
			if (mode == ADD_MODE) {
				ManagerService.getInstance().addPower(power);
				JOptionPane.showMessageDialog(getOwner(), "增加成功！");
			} else {
				ManagerService.getInstance().modifyPower(power);
				JOptionPane.showMessageDialog(getOwner(), "修改成功！");
			}
			option = MODIFY_OPTION;
		}
		option = NOT_MODIFY_OPTION;
		return true;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int intValue = spinnerNumberModel.getNumber().intValue();
		checkBoxs.get(Power.NO_POWER_INDEX).setSelected(intValue == 0);
		checkBoxs.get(Power.LOW_POWER_INDEX).setSelected(intValue != 0 && intValue <= 10);
		checkBoxs.get(Power.AVAILABLE_INDEX).setSelected(intValue > 10);
		checkBoxs.get(Power.AVAILABLE_INDEX).setEnabled(mode == MODIFY_MODE ? intValue > 10 : false);
	}
}
