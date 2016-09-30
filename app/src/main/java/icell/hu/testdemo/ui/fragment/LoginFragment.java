package icell.hu.testdemo.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.LoginActivity;
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.network.Interfaces.LoginListener;
import rx.Subscription;

/**
 * Created by User on 2016. 09. 28..
 */

public class LoginFragment extends BaseFragment {

    @BindView (R.id.email) EditText emailText;
    @BindView (R.id.password) EditText passwordText;
    //@BindView(R.id.email_sign_in_button) Button btnLogin;

    ProgressDialog dialog ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this) ;
    }

    @Override
    public View onCreateView ( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View layout = inflater.inflate ( R.layout. activity_login, container, false);               //  TODO fragment_login

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind (this, view);

    }

    @OnClick (R.id.email_sign_in_button)
    void loginPushed() {
        attemptLogin();
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
            ( ( LoginActivity ) getActivity() ) . demoCredentials.setUsername(email);
            ( ( LoginActivity ) getActivity() ) . demoCredentials.setPassword(password);
            showProgress(true);
            subscription = ( ( LoginActivity ) getActivity() ) .
                    rxManager . login ( ( ( LoginActivity ) getActivity() ) . demoCredentials ,
                    new LoginListener ( ) {

                @Override
                public void loginFinished(UserInfo userInfo) {
                    showProgress(false);
                    ( ( LoginActivity ) getActivity() ) . selectedUser.setUserInfo(userInfo);
                    Intent intent = new Intent( getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    ( ( LoginActivity ) getActivity() ) . activityPresenter .
                            startActivity ( getActivity() , intent ) ;
                }

                @Override
                public void failed() {
                    showProgress(false);
                }
            });
        }
    }

    private void showProgress ( boolean show) {
        if ( dialog == null )
            dialog = ProgressDialog.show(getActivity(),
                    getString(R.string.dialog_wait) ,
                    getString(R.string.dialog_loading), true , false);
        if ( show ) {
            if ( dialog.isShowing() )
                return;
            dialog . show ( ) ;
        }else
            if ( dialog.isShowing() )
                dialog.dismiss() ;
    }

}
