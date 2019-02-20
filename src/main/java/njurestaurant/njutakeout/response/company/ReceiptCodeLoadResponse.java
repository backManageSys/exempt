package njurestaurant.njutakeout.response.company;

import njurestaurant.njutakeout.entity.company.ReceiptCode;
import njurestaurant.njutakeout.response.Response;

import java.util.List;

public class ReceiptCodeLoadResponse extends Response {
    private int id;
    private String teamName;
    private String teamId;
    private String type;
    private int duration;
    private String priorityLevel;
    private String accountInfo;
    private String accountNumber;

    public ReceiptCodeLoadResponse() {
    }

    public ReceiptCodeLoadResponse(int id, String teamName, String teamId, String type, int duration, String priorityLevel, String accountInfo, String accountNumber) {
        this.id = id;
        this.teamName = teamName;
        this.teamId = teamId;
        this.type = type;
        this.duration = duration;
        this.priorityLevel = priorityLevel;
        this.accountInfo = accountInfo;
        this.accountNumber = accountNumber;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
