package icell.hu.testdemo.ui.activity;

import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.helper.InputMethodReflector;

/**
 * Created by User on 2016. 09. 28..
 */

public class BaseActivity extends AppCompatActivity {


    public DemoApplication demoApplication;


    public DemoApplication getDemoApplication() {
        if (demoApplication == null) {
            demoApplication = ((DemoApplication) getApplicationContext());
        }
        return demoApplication;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //fix for memory leak: http://code.google.com/p/android/issues/detail?id=34731
        try {
            fixInputMethodManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fixInputMethodManager() {
        final Object imm = getSystemService(Context.INPUT_METHOD_SERVICE);

        final InputMethodReflector.TypedObject windowToken
                = new InputMethodReflector.TypedObject(getWindow().getDecorView().getWindowToken(), IBinder.class);

        InputMethodReflector.invokeMethodExceptionSafe(imm, "windowDismissed", windowToken);

        final InputMethodReflector.TypedObject view
                = new InputMethodReflector.TypedObject(null, View.class);

        InputMethodReflector.invokeMethodExceptionSafe(imm, "startGettingWindowFocus", view);
    }


}
