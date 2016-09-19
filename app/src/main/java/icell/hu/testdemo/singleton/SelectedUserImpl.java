package icell.hu.testdemo.singleton;

import icell.hu.testdemo.model.UserInfo;

/**
 * Created by ilaszlo on 15/09/16.
 */

public class SelectedUserImpl implements SelectedUser {

    UserInfo userInfo = new UserInfo();

    @Override
    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
