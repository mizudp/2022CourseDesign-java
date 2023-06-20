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

	private static final String[] TYPE_NAME = { "损坏", "没电", "已借", "可借" };

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

	public String getStatusName(int status) {
		if (status == 0) {
			return "空";
		}
		StringBuilder statusName = new StringBuilder();
		StringBuilder str = new StringBuilder(Integer.toBinaryString(status));
		int length = str.length();
		// 补全五位二进制字符串编码
		for (int i = 0; i < 4 - length; i++) {
			str.insert(0, '0');
		}
		int last = str.lastIndexOf("1");
		// 根据二进制字符串生成正则表达式
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '1') {
				statusName.append(TYPE_NAME[i]);
				if (i != last) {
					statusName.append('|');
				}
			}
		}
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
