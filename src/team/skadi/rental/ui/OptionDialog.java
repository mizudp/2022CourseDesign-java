package team.skadi.rental.ui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class OptionDialog extends BasicDialog {

	public static final int READ_ONLY_MODE = 0, MODIFY_MODE = 1, ADD_MODE = 2;
	public static final int MODIFY_OPTION = 1, NOT_MODIFY_OPTION = 2;

	protected int mode;

	public OptionDialog(JFrame frame, int mode) {
		super(frame, mode == MODIFY_MODE ? "编辑模式" : mode == ADD_MODE ? "添加模式" : "查看模式");
		this.mode = mode;
	}

	public int getMode() {
		return mode;
	}
}
