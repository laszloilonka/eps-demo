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
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.network.listener.LoginListener;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.singleton.ActivityPresenter;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;

public class LoginActivity extends AppCompatActivity implements Callback<UserInfo>, TextView.OnEditorActionListener {

    @Inject
    DemoClient demoClient;

    @Inject
    DemoCredentials demoCredentials;

    @Inject
    ActivityPresenter activityPresenter;

    @Inject
    SelectedUser selectedUser;

    @Inject
    RXManager rxManager;

    private EditText emailText;
    private EditText passwordText;
    private View progressView;
    private View loginFormView;

    private DemoApplication demoApplication;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDemoApplication().getComponent().inject(this);
        setContentView(R.layout.activity_login);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.password);
        passwordText.setOnEditorActionListener(this);

        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.login_progress);
    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    public DemoApplication getDemoApplication() {
        if (demoApplication == null) {
            demoApplication = ((DemoApplication) getApplicationContext());
        }
        return demoApplication;
    }

    private void attemptLogin() {
        emailText.setError(null);
        passwordText.setError(null);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            passwordText.setError(getString(R.string.error_field_required));
            focusView = passwordText;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailText.setError(getString(R.string.error_field_required));
            focusView = emailText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            demoCredentials.setUsername(email);
            demoCredentials.setPassword(password);
            showProgress(true);
            subscription = rxManager.login(demoCredentials, new LoginListener() {
                @Override
                public void loginFinished(UserInfo userInfo) {
                    showProgress(false);
                    selectedUser.setUserInfo(userInfo);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    activityPresenter.startActivity(LoginActivity.this, intent);
                }

                @Override
                public void failed() {
                    showProgress(false);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        loginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
        showProgress(false);
        handleResponse(response);
    }

    private void handleResponse(Response<UserInfo> response) {
        if (response.isSuccessful()) {
            selectedUser.setUserInfo(response.body());
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activityPresenter.startActivity(LoginActivity.this, intent);
        } else {
            passwordText.setError(getString(R.string.error_incorrect_password));
            passwordText.requestFocus();
        }
    }

    @Override
    public void onFailure(Call<UserInfo> call, Throwable t) {
        showProgress(false);
    }
}

