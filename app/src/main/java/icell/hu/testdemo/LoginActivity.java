package icell.hu.testdemo;

import android.os.Bundle;

import javax.inject.Inject;

import icell.hu.testdemo.singleton.ActivityPresenter;
import icell.hu.testdemo.ui.activity.BaseActivity;
import icell.hu.testdemo.ui.fragment.LoginFragment;

public class LoginActivity extends BaseActivity {

    @Inject
    public ActivityPresenter activityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDemoApplication().getComponent().inject(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new LoginFragment(), this.toString())
                    .commit();
        }
    }
}

