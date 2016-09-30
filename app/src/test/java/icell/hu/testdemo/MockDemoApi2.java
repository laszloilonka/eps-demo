package icell.hu.testdemo;

import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.DemoApi;
import icell.hu.testdemo.network.UserCredentials;
import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Observable;

public class MockDemoApi2 implements DemoApi {

    @Override
    public Observable<UserInfo> login(@Body UserCredentials credentials) {
        return Observable.just(new UserInfo());
    }

    @Override
    public Observable<Vehicle[]> getVehicles(@Path("userId") Long userId) {
        return Observable.just(null);
    }
}