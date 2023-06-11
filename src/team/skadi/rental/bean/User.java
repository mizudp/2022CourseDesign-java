package team.skadi.rental.bean;

public class User {
	private String id;
	private int serialnum;
	private String name;
	private String phoneNumber;
	private String password;
	private double balance;
	private String email;
	private int credit;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public User(String id, int serialnum, String name, String phoneNumber, String password, double balance,
			String email, int credit) {
		super();
		this.id = id;
		this.serialnum = serialnum;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.balance = balance;
		this.email = email;
		this.credit = credit;
	}

	public void del() {
		id = "";
		name = "";
		phoneNumber = "";
		password = "";
		balance = 0;
		email = "";
		credit = 0;
	}

	public User() {
	}
}
