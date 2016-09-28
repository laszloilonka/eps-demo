package icell.hu.testdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.network.DemoApi;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.network.Interfaces.LoginListener;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.network.UserCredentials;
import icell.hu.testdemo.singleton.ActivityPresenter;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.ui.activity.BaseActivity;
import icell.hu.testdemo.ui.fragment.LoginFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

public class LoginActivity extends BaseActivity {

    @Inject
    public DemoClient demoClient;

    @Inject
    public DemoCredentials demoCredentials;

    @Inject
    public ActivityPresenter activityPresenter;

    @Inject
    public SelectedUser selectedUser;

    @Inject
    public RXManager rxManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDemoApplication().getComponent().inject(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new LoginFragment() , this.toString())
                    .commit();
        }
    }
}

