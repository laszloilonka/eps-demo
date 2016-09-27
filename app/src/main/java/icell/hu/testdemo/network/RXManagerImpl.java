package icell.hu.testdemo.network;


import icell.hu.testdemo.model.UserInfo;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ilaszlo on 27/09/16.
 */

public class RXManagerImpl implements RXManager {


    private DemoClient demoClient;

    public RXManagerImpl(DemoClient demoClient) {
        this.demoClient = demoClient;
    }

    @Override
    public Subscription login(final LoginListener loginListener) {
        DemoApi demoApi = demoClient.getDemoApi();
        Observable<UserInfo> userCall = demoApi.login(new UserCredentials("a", "a"));
        Subscription subscription = userCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {

                    private UserInfo userInfo;

                    @Override
                    public void onCompleted() {
                        loginListener.loginFinished(userInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginListener.loginFailed();
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        this.userInfo = userInfo;
                    }
                });
        return subscription;
    }

}
