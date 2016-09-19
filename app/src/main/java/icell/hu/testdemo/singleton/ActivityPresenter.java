package icell.hu.testdemo.singleton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ilaszlo on 14/09/16.
 */

public interface ActivityPresenter {

    void startActivity(Activity presenter, Intent intent);
}
