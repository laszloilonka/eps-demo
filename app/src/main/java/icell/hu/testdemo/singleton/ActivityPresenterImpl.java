package icell.hu.testdemo.singleton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ilaszlo on 14/09/16.
 */

public class ActivityPresenterImpl implements ActivityPresenter {

    @Override
    public void startActivity(Activity presenter, Intent intent) {
        presenter.startActivity(intent);
    }
}
