package team.skadi.rental.bean;

public class Logs {

    private String userId;
    private String powerId;
    private String startDate;
    private String endDate;
    /**租借日志内容*/
    private String context;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Logs(String userId, String powerId, String startDate, String endDate, String context) {
        this.userId = userId;
        this.powerId = powerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.context = context;
    }

    public Logs() {
    }
}
