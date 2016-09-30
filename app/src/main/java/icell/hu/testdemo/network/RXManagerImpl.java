package icell.hu.testdemo.network;


import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import icell.hu.testdemo.model.AddVehicle;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.Interfaces.LoginListener;
import icell.hu.testdemo.network.Interfaces.ParkingListener;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
                                final LoginListener listener ) {

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
                        listener.failed();
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        listener.loginFinished(userInfo);
                    }
                });
        return subscription;
    }



    @Override
    public Subscription getVehicles( SelectedUser selectedUser , final VehicleListener listener ) {

        Log.d ( TAG , "getAvailableVehicles" ) ;

        DemoApi demoApi = demoClient.getDemoApi ( ) ;
        Observable<List<Vehicle>> vehicles = demoApi . getVehicles ( 33L ) ;                                                                 // getUserId -> /user/{userID}/vehicle

        Subscription subscription = vehicles
                . subscribeOn ( Schedulers . newThread ( ) )
                . observeOn ( AndroidSchedulers . mainThread ( ) )
                . subscribe ( new Subscriber < List<Vehicle> > () {
                    @Override
                    public void onCompleted ( ) {}

                    @Override
                    public void onError ( Throwable e ) {
                        listener . failed( ) ;
                        e.printStackTrace ( ) ;
                    }

                    @Override
                    public void onNext ( List<Vehicle> vehicles) {
                        listener .vehiclesLoaded( vehicles ) ;
                        Log.d ( TAG , "onNext" ) ;
                    }
                }) ;

        return subscription;
    }



    @Override
    public Subscription addVehicle ( SelectedUser selectedUser , AddVehicle vehicle , final VehicleListener listener ) {

        Log.d ( TAG , "getAvailableVehicles" ) ;

        DemoApi demoApi = demoClient.getDemoApi ( ) ;                                               // TODO inflate
        Observable<Vehicle> vehicles = demoApi . addVehicle ( selectedUser . getUserInfo ( )
                . getUserId ( ) , vehicle ) ;                                                                 // getUserId -> /user/{userID}/vehicle

        Subscription subscription = vehicles
                . subscribeOn ( Schedulers . newThread ( ) )
                . observeOn ( AndroidSchedulers . mainThread ( ) )
                . subscribe ( new Subscriber < Vehicle > () {
                    @Override
                    public void onCompleted ( ) {}

                    @Override
                    public void onError ( Throwable e ) {
                        listener . failed( ) ;
                        e.printStackTrace ( ) ;
                    }

                    @Override
                    public void onNext ( Vehicle vehicles ) {
                        listener . vehiclesAdded ( vehicles ) ;
                    }
                }) ;

        return subscription;
    }


    @Override
    public Subscription getParkings ( SelectedUser selectedUser , final ParkingListener listener ) {

        Log.d ( TAG , "getAvailableVehicles" ) ;

        DemoApi demoApi = demoClient.getDemoApi ( ) ;
        Observable<List<Parking>> parkings = demoApi . getParkings (
                selectedUser . getUserInfo ( ). getUserId ( )) ;

        Subscription subscription = parkings
                . subscribeOn ( Schedulers . newThread ( ) )
                . observeOn ( AndroidSchedulers . mainThread ( ) )
                . subscribe ( new Subscriber < List<Parking> > () {
                    @Override
                    public void onCompleted ( ) {}

                    @Override
                    public void onError ( Throwable e ) {
                        listener . failed( ) ;
                        e.printStackTrace ( ) ;
                    }

                    @Override
                    public void onNext ( List<Parking> parkings ) {
                        listener . parkingLoaded ( parkings ); ;
                    }
                }) ;

        return subscription;
    }







}
