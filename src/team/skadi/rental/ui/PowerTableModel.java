package team.skadi.rental.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import team.skadi.rental.bean.Power;
import team.skadi.rental.service.ManagerService;
import team.skadi.rental.service.PowerService;
import team.skadi.rental.ui.SearchPanel.SearchResult;

@SuppressWarnings("serial")
public class PowerTableModel extends BasicTableModel {

	private String[] title = { "移动电源id", "剩余电量", "租借状态" };
	private List<Power> powerList;

	public PowerTableModel(int mode) {
		super(mode);
	}

	public void changeData(List<Power> powerList) {
		this.powerList = powerList;
		fireTableDataChanged();
	}

	public List<Power> getData() {
		return powerList;
	}

	public void addPower(Power power) {
		powerList.add(power);
		int row = powerList.size() - 1;
		fireTableRowsInserted(row, row);
	}

	public void removePower(int rowIndex) {
		Power power = powerList.remove(rowIndex);
		ManagerService.getInstance().removePower(power);
		fireTableCellUpdated(rowIndex, rowIndex);
	}

	public void setPower(int rowIndex, Power power) {
		powerList.set(rowIndex, power);
		fireTableRowsUpdated(rowIndex, rowIndex);
	}

	public Power getPower(int rowIndex) {
		return powerList.get(rowIndex);
	}

	/**
	 * 
	 * @param searchIndex 当前的子选择模式
	 * @param content     要查询的内容
	 * @return true:有结果。
	 */
	public SearchResult changeData(int searchIndex, String content) {
		List<Power> powerList = null;
		switch (searchIndex) {
		case SearchPanel.POWER_ID:
			Power power = PowerService.getInstance().getPowerById(content);
			if (power != null) {
				powerList = new ArrayList<>(1);
				powerList.add(power);
			}
			break;
		case SearchPanel.POWER_LEFT:
			int powerLeft = 0;
			try {
				powerLeft = Integer.parseInt(content);
			} catch (Exception e) {
				return SearchResult.NAN;
			}
			powerList = PowerService.getInstance().getPowersByPowerLeft(powerLeft);
			break;
		case SearchPanel.POWER_STATUS:
			powerList = PowerService.getInstance().getPowersByStatus(Power.getStatusByStatusNmae(content));
			break;
		}
		if (powerList != null && powerList.size() != 0) {
			changeData(powerList);
			return SearchResult.HAVE_RESULT;
		}
		return SearchResult.NO_RESULT;
	}

	@Override
	public int getRowCount() {
		if (powerList==null) {
			return 0;
		}
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
			return power.getLeft() + "%";
		case 2:
			return Power.getStatusNameByStatu(power.getStatus());
		default:
			return null;
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
				Power power = powerList.get(table.getSelectedRow());
				new PowerOption(mainFrame, OptionDialog.READ_ONLY_MODE, power).setVisible(true);
			}
		}
	}

}
