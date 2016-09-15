package icell.hu.testdemo.singleton;

import okhttp3.Credentials;

/**
 * Created by ilaszlo on 13/09/16.
 */

public class DemoCredentials {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasicScheme() {
        String credential = Credentials.basic(username, password);
        return credential;
    }
}
