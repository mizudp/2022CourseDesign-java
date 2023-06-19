package team.skadi.rental.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import team.skadi.rental.bean.Power;

@SuppressWarnings("serial")
public class PowerTableModel extends AbstractTableModel {

	private String[] title = { "充电宝id", "剩余电量", "租借状态" };
	private List<Power> powerList;

	public PowerTableModel(List<Power> powerList) {
		this.powerList = powerList;
	}

	public void removePower(int rowIndex) {
		powerList.remove(rowIndex);
	}

	@Override
	public int getRowCount() {
		return powerList.size();
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
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Power power = powerList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return power.getId();
		case 1:
			return power.getLeft();
		case 2:
			return power.getStatus();
		default:
			return null;
		}
	}

}
