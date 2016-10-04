package icell.hu.testdemo;

import java.util.List;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.DemoApi;
import icell.hu.testdemo.network.UserCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;

public class MockDemoApi2 implements DemoApi {

   /* @Override
    public Observable<UserInfo> login(@Body UserCredentials credentials) {
        return Observable.just(new UserInfo());
    }

    @Override
    public Observable<Vehicle[]> getVehicles(@Path("userId") Long userId) {
        return Observable.just(null);
    }*/

    @Override
    public Call<UserInfo> login(@Body UserCredentials credentiale) {
        return null;
    }

    @Override
    public Call<List<Vehicle>> getVehicles(@Path("userId") Long userId) {
        return null;
    }

    @Override
    public Call<Vehicle> addVehicle(@Path("userId") Long userId, @Body Vehicle vehicle) {
        return null;
    }

    @Override
    public Call<List<Parking>> getParkings(@Path("userId") Long userId) {
        return null;
    }

    @Override
    public Call<Parking> startParking(@Path("userId") Long userId, @Body Vehicle vehicle) {
        return null;
    }

    @Override
    public Call<Parking> stopParking(@Path("userId") Long userId, @Body Parking parking) {
        return null;
    }
}