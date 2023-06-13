package team.skadi.rental.bean;

public class Manager {
	/** 管理员id */
	private String id;
	/** 管理员密码 */
	private String password;
	/** 管理员姓名 */
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Manager(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}

	public Manager() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
