package icell.hu.testdemo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.LoginActivity;
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.event.LoginEvent;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.ui.view.RoundedButton;

/**
 * Created by User on 2016. 09. 28..
 */

public class LoginFragment extends BaseFragment implements RoundedButton.RoundedButtomListener {

    public static final String TAG = LoginFragment.class.getSimpleName();

    @Inject
    DemoCredentials demoCredentials;

    @Inject
    SelectedUser selectedUser;


    @BindView(R.id.email)
    EditText emailText;
    @BindView(R.id.password)
    EditText passwordText;

    RoundedButton roundedButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        roundedButton = new RoundedButton(view, this);
        roundedButton.setText(getString(R.string.action_sign_in_short));
        bus.register(this);
        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    attemptLogin();
                }
                return false;
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        roundedButton.onDestroy();                          // TODO TEST
    }

    private void attemptLogin() {
        hideKeyboard(getView());
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
            if (roundedButton.isProcessRunning()) {
                return;
            }
            roundedButton.startProcess();
            demoCredentials.setUsername(email);
            demoCredentials.setPassword(password);
            callEventBus();
        }
    }

    private void callEventBus() {
        eventBusManager.login(demoCredentials);
    }


    @Subscribe
    public void onEvent(LoginEvent login) {
        roundedButton.stopProcess();
        if (login.isError()) {
            Toast.makeText(getActivity(), getString(R.string.error_something_went_wrong) + "!"
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        selectedUser.setUserInfo(login.getUserInfo());
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ((LoginActivity) getActivity()).activityPresenter.
                startActivity(getActivity(), intent);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onButtonClicked() {
        attemptLogin();
    }
}
