package icell.hu.testdemo.network;

import icell.hu.testdemo.model.UserInfo;

/**
 * Created by ilaszlo on 27/09/16.
 */
public interface LoginListener {

    void loginFinished(UserInfo userInfo);
    void loginFailed();
}
