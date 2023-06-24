package team.skadi.rental.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import team.skadi.rental.bean.Log;
import team.skadi.rental.bean.Power;
import team.skadi.rental.bean.User;
import team.skadi.rental.service.LogService;
import team.skadi.rental.ui.SearchPanel.SearchResult;
import team.skadi.rental.utils.DateUtil;

@SuppressWarnings("serial")
public class LogTableModel extends BasicTableModel {

	private String[] title;
	private List<Log> logList;

	public LogTableModel(int mode) {
		super(mode);
		this.logList = new ArrayList<>();
		this.mode = mode;
		if (mode == USER_MODE) {
			title = new String[] { "充电宝id", "租借时间", "归还时间", "内容" };
		} else {
			title = new String[] { "用户id", "充电宝id", "租借时间", "归还时间", "内容" };
		}
	}

	public void changeData(List<Log> logList) {
		this.logList = logList;
		fireTableDataChanged();
	}

	public SearchResult changeData(int searchIndex, String content) {
		List<Log> result = null;
		switch (searchIndex) {
		case SearchPanel.LOG_USERID:
			User user = new User();
			user.setId(content);
			result = LogService.queryLogs(user);
			break;
		case SearchPanel.LOG_POWERID:
			Power power = new Power();
			power.setId(content);
			result = LogService.queryLogs(power);
			break;
		}
		if (result != null && result.size() != 0) {
			changeData(result);
			return SearchResult.HAVE_RESULT;
		}
		return SearchResult.NO_RESULT;
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
		if (mode == USER_MODE) {
			columnIndex++;
		}
		switch (columnIndex) {
		case 0:
			return log.getUserId();
		case 1:
			return log.getPowerId();
		case 2:
			return DateUtil.format(log.getStartDate());
		case 3:
			if (log.getEndDate() == 0) {
				return "尚未归还";
			}
			return DateUtil.format(log.getEndDate());
		case 4:
			return log.getContext();
		default:
			return null;
		}
	}

	public void setPreferredWidth(TableColumnModel columnModel) {
		columnModel.getColumn(0).setPreferredWidth(1);
		if (mode == MANAGER_MODE) {
			columnModel.getColumn(1).setPreferredWidth(1);
		}
	}

	public class DoubleClick extends MouseAdapter {
		private MainFrame mainFrame;

		public DoubleClick(MainFrame mainFrame) {
			this.mainFrame = mainFrame;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			if (e.getClickCount() == 2) {
				new LogOption(mainFrame, logList.get(table.getSelectedRow())).setVisible(true);
			}
		}
	}

}
