package icell.hu.testdemo.model;

import java.io.Serializable;

/**
 * Created by ilaszlo on 13/09/16.
 */

public class UserInfo {

    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Long balance;

    public UserInfo() {
    }

    public UserInfo(Long userId, String userName, String firstName, String lastName, Long balance) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}

