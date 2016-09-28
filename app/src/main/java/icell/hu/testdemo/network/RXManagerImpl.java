package icell.hu.testdemo.network;


import android.util.Log;

import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.Interfaces.LoginListener;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

/**
 * Created by ilaszlo on 27/09/16.
 */

public class RXManagerImpl implements RXManager {

    public static final String TAG = RXManagerImpl.class.getSimpleName();


    private DemoClient demoClient;

    public RXManagerImpl(DemoClient demoClient) {
        this.demoClient = demoClient;
    }

    @Override
    public Subscription login ( DemoCredentials demoCredentials ,
                                final LoginListener loginListener) {

        DemoApi demoApi = demoClient.getDemoApi();

        UserCredentials user = new UserCredentials (
                demoCredentials . getUsername() ,
                demoCredentials . getPassword() ) ;

        Observable<UserInfo> userCall = demoApi . login( user );                                    // /user/ Post

        Subscription subscription = userCall.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserInfo>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        loginListener.failed();
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        loginListener.loginFinished(userInfo);
                    }
                });
        return subscription;
    }



    @Override
    public Subscription getAvailableVehicles(SelectedUser user , final VehicleListener listener ) {

        Log.d ( TAG , "getAvailableVehicles" ) ;

        DemoApi demoApi = demoClient.getDemoApi ( ) ;
        Observable<Vehicle[]> vehicles = demoApi . getVehicles( user . getUserInfo ( )
                . getUserId ( ) ) ;                                                                 // getUserId -> /user/{userID}/vehicle

        //ConnectableObservable<Vehicle[]> obj ;


        Subscription subscription = vehicles
                . subscribeOn ( Schedulers . newThread ( ) )
                . observeOn ( AndroidSchedulers . mainThread ( ) )
                . subscribe ( new Subscriber < Vehicle [ ] > () {
                    @Override
                    public void onCompleted ( ) {}

                    @Override
                    public void onError ( Throwable e ) {
                        listener . failed( ) ;
                        e.printStackTrace ( ) ;
                    }

                    @Override
                    public void onNext ( Vehicle [ ] vehicles) {                                    // on UIThread
                        listener . vehiclesFinished( vehicles ) ;                                   // send back (VehicleListener)
                        Log.d ( TAG , "onNext" ) ;
                    }
                }) ;

        return subscription;
    }

}
