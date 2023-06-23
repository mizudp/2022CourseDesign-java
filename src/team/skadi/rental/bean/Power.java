package team.skadi.rental.bean;

public class Power {

	/** 可借 */
	public static final int AVAILABLE = 1;
	/** 已借出 */
	public static final int BORROWED = 2;
	/** 能源不足 */
	public static final int NO_POWER = 4;
	/** 已损坏 */
	public static final int BROKEN = 8;
	/** 该充电宝为空 */
	public static final int NULL = 0;

	private static final String[] STATUS_NAME = { "可借", "已借", "没电", "损坏" };

	/** 电源id */
	private String id;
	/** 电源序列号 */
	private int serialnum;
	/** 剩余电量 */
	private int left;
	/** 租借状态 */
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(int serialnum) {
		this.serialnum = serialnum;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void addStatus(int status) {
		this.status |= status;
	}

	public void removeStatus(int status) {
		this.status &= status ^ 0xff;
	}

	public boolean hasStatus(int status) {
		if ((this.status & status) != 0) {
			return true;
		}
		return false;
	}

	public static int getStatusByStatusNmae(String statusName) {
		int status = 0, bin = 1;
		for (int i = 0; i < STATUS_NAME.length; i++) {
			if (statusName.contains(STATUS_NAME[i])) {
				status |= bin;
				bin <<= 1;
			}
		}
		return status;
	}

	public static String getStatusNameByStatu(int status) {
		if (status == 0) {
			return "空";
		}
		StringBuilder statusName = new StringBuilder();
		for (int i = 0, bin = 1; i < STATUS_NAME.length; i++) {
			// 使用二进制与运算更快更方便
			if ((status & bin) != 0) {
				statusName.append(STATUS_NAME[i]).append('|');
			}
			bin <<= 1;
		}
		statusName.deleteCharAt(statusName.length() - 1);
		return statusName.toString();
	}

	public void reset() {
		id = null;
		left = 0;
		status = NULL;
	}

	public Power(String id, int serialnum, int left, int status) {
		this.id = id;
		this.serialnum = serialnum;
		this.left = left;
		this.status = status;
	}

	public Power() {
	}
}
