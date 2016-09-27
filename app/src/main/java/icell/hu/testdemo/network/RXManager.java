package icell.hu.testdemo.network;

import rx.Subscription;

/**
 * Created by ilaszlo on 27/09/16.
 */

public interface RXManager {
    Subscription login(LoginListener loginListener);
}
