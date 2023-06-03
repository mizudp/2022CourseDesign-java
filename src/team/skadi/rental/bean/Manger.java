package team.skadi.rental.bean;

public class Manger {
   /**管理员id*/
    private String id;
    /**管理员密码*/
    private String password;

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

    public Manger(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Manger() {
    }
}
