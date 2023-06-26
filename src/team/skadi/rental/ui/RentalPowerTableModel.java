package team.skadi.rental.ui;

import javax.swing.table.AbstractTableModel;

import team.skadi.rental.bean.Power;

@SuppressWarnings("serial")
public class RentalPowerTableModel extends AbstractTableModel {

	private String[] title = { "移动电源id", "剩余电量", };
	private Power power;

	public void setPower(Power power) {
		this.power = power;
		fireTableDataChanged();
	}

	public void clearPower() {
		setPower(null);
	}

	@Override
	public int getRowCount() {
		if (power == null) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public String getColumnName(int column) {
		return title[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return power.getId();
		case 1:
			return power.getLeft() + "%";
		default:
			return null;
		}
	}

}
