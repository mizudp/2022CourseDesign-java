package team.skadi.rental.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import team.skadi.rental.bean.Log;
import team.skadi.rental.utils.DateUtil;

@SuppressWarnings("serial")
public class LogTableModel extends AbstractTableModel {

	private String[] title = { "充电宝id", "租借时间", "归还时间", "内容" };
	private List<Log> logList;

	public LogTableModel(List<Log> logList) {
		this.logList = logList;
	}

	@Override
	public int getRowCount() {
		return logList.size();
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
		Log log = logList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return log.getPowerId();
		case 1:
			return DateUtil.format(log.getStartDate());
		case 2:
			return DateUtil.format(log.getEndDate());
		case 3:
			return log.getContext();
		default:
			return null;
		}
	}

}
