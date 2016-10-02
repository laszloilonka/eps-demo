package icell.hu.testdemo.model.event;

import icell.hu.testdemo.model.UserInfo;

/**
 * Created by User on 2016. 09. 30..
 */

public class LoginEvent extends EventParent{

    UserInfo user;

    public LoginEvent(){

    }

    public LoginEvent(UserInfo user) {
        this.user = user;
    }

    public UserInfo getUserInfo() {
        return user;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.user = userInfo;
    }
}
