package team.skadi.rental.ui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import team.skadi.rental.Main;
import team.skadi.rental.bean.Log;
import team.skadi.rental.utils.DateUtil;

@SuppressWarnings("serial")
public class UserPowerTableModel extends AbstractTableModel {

	private String[] title = { "移动电源id", "租借时间", "累计时长(小时)", "预计收费(元)", "选项" };
	private Log log;
	private ActionListener l;

	public UserPowerTableModel(ActionListener l) {
		this.l = l;
	}

	public void setLog(Log log) {
		this.log = log;
		fireTableDataChanged();
	}
	
	public void clearLog() {
		setLog(null);
	}

	@Override
	public int getRowCount() {
		if (log == null) {
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
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 4) {
			return JButton.class;
		} else {
			return String.class;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 4) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int timeSpan = DateUtil.getTimeSpan(log.getStartDate(), System.currentTimeMillis());
		switch (columnIndex) {
		case 0:
			return log.getPowerId();
		case 1:
			return DateUtil.format(log.getStartDate());
		case 2:
			return timeSpan;
		case 3:
			return Main.getCost(timeSpan);
		case 4:
			final JButton optionBtn = new JButton("归还该移动电源");
			optionBtn.addActionListener(l);
			return optionBtn;
		default:
			return null;
		}
	}
	
	public void setPreferredWidth(TableColumnModel columnModel) {
		columnModel.getColumn(0).setPreferredWidth(1);
		columnModel.getColumn(2).setPreferredWidth(1);
		columnModel.getColumn(3).setPreferredWidth(1);
	}

	public class ButtonTableCellRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JButton button = (JButton) value;
			return button;
		}

	}

	public class ButtonTableCellEditor extends AbstractCellEditor implements TableCellEditor {

		public ButtonTableCellEditor() {
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			JButton btn = (JButton) value;
			return btn;
		}

		@Override
		public Object getCellEditorValue() {
			return log;
		}
	}
}
