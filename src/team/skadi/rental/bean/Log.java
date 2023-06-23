package team.skadi.rental.bean;

public class Log {

	public static final Log EMPTY_LOG = new Log("", "", 0, 0, "");

	private String userId;
	private String powerId;
	private long startDate;
	private long endDate;
	/** 租借日志内容 */
	private String context;

	public Log(String userId, String powerId, long startDate, long endDate, String context) {
		super();
		this.userId = userId;
		this.powerId = powerId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.context = context;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPowerId() {
		return powerId;
	}

	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Log() {
	}
}
