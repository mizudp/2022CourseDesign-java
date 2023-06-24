package team.skadi.rental.ui;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public abstract class BasicTableModel extends AbstractTableModel {
	
	public static final int USER_MODE = 0, MANAGER_MODE = 1;
	
	protected int mode;

	public BasicTableModel(int mode) {
		this.mode = mode;
	}
}
