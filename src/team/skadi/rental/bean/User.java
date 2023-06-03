package team.skadi.rental.bean;

public class User {
    private String id;
    private int serialnum;
    private String name;
    private String phoneNumber;
    private String idcard;
    private String password;
    private double balance;
    private String email;

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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
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

    public User(String id, int serialnum, String name, String phoneNumber, String idcard, String password, double balance, String email) {
        this.id = id;
        this.serialnum = serialnum;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.idcard = idcard;
        this.password = password;
        this.balance = balance;
        this.email = email;
    }

    public User() {
    }
}
