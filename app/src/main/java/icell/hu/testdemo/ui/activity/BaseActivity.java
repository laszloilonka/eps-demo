package icell.hu.testdemo.ui.activity;

import android.support.v7.app.AppCompatActivity;

import icell.hu.testdemo.DemoApplication;

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



}
