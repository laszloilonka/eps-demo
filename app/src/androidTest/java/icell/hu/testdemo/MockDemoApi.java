package icell.hu.testdemo;

import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.DemoApi;
import icell.hu.testdemo.network.UserCredentials;
import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Observable;

public class MockDemoApi implements DemoApi {

    private Observable loginResult;
    private Observable getVehiclesResult;

    public MockDemoApi(Observable loginResult, Observable getVehiclesResult) {
        this.loginResult = loginResult;
        this.getVehiclesResult = getVehiclesResult;
    }

    @Override
    public Observable<UserInfo> login(@Body UserCredentials credentials) {
        return loginResult;
    }

    @Override
    public Observable<Vehicle[]> getVehicles(@Path("userId") Long userId) {
        return getVehiclesResult;
    }
}