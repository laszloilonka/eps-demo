package icell.hu.testdemo.network;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.event.GetParkingsEvent;
import icell.hu.testdemo.model.event.GetVehiclesEvent;
import icell.hu.testdemo.model.event.LoginEvent;
import icell.hu.testdemo.model.event.ParkingStartEvent;
import icell.hu.testdemo.model.event.ParkingStopEvent;
import icell.hu.testdemo.model.event.AddedVehicleEvent;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016. 09. 30..
 */

public class EventBusManagerImpl implements EventBusManager{

    public static final String TAG = EventBusManagerImpl.class.getSimpleName();

    private EventBus bus = EventBus.getDefault();
    private DemoClient demoClient;

    public EventBusManagerImpl ( DemoClient demoClient ) {
        this.demoClient = demoClient;
    }
                                                                                                    // user  -login
    @Override
    public void login( DemoCredentials demoCredentials ) {
        UserCredentials user = new UserCredentials (
                demoCredentials . getUsername() ,
                demoCredentials . getPassword() ) ;

        final Call<UserInfo> loginCall = demoClient.getDemoApi().login( user );
        loginCall.enqueue( new Callback<UserInfo>(){
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                LoginEvent loginEvent = new LoginEvent();
                if (response.code() == 200) {
                    loginEvent.setUserInfo(response.body());
                } else {
                    loginEvent.setError(true);
                }
                bus.post(loginEvent);
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                LoginEvent event = new LoginEvent();
                event.setError(true);
                bus.post(event);
            }
        });
    }
                                                                                                    //    Vehicle
    @Override
    public void getVehicles(SelectedUser selectedUser) {
        Call<List<Vehicle>> vehiclesCall = demoClient.getDemoApi().
                getVehicles(selectedUser.getUserInfo().getUserId());
        vehiclesCall.enqueue( new Callback<List<Vehicle>>(){
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                bus.post( new GetVehiclesEvent(response.body()));
            }
            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                GetVehiclesEvent event = new GetVehiclesEvent();
                event.setError(true);
                bus.post(event);
            }
        });
    }

    @Override
    public void addVehicle(SelectedUser selectedUser, Vehicle vehicle) {
        Log.d(TAG, "add new vehicle");
        Call<Vehicle> vehiclesCall = demoClient.getDemoApi().
                addVehicle(selectedUser.getUserInfo().getUserId() , vehicle);
        vehiclesCall.enqueue( new Callback<Vehicle>(){
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                bus.post(new AddedVehicleEvent(response.body()));
            }
            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                AddedVehicleEvent event = new AddedVehicleEvent();
                event.setError(true);
                bus.post(event);
            }
        });
    }

                                                                                                    //    Parking

    @Override
    public void getParkings(SelectedUser selectedUser) {
        Call<List<Parking>>  vehiclesCall = demoClient.getDemoApi().
                getParkings(selectedUser.getUserInfo().getUserId());
        vehiclesCall.enqueue( new Callback<List<Parking>>(){
            @Override
            public void onResponse(Call<List<Parking>> call, Response<List<Parking>> response) {
                bus.post(new GetParkingsEvent(response.body()));
            }

            @Override
            public void onFailure(Call<List<Parking>> call, Throwable t) {
                GetParkingsEvent event = new GetParkingsEvent();
                event.setError(true);
                bus.post(event);
            }
        });
    }


    @Override
    public void startParking(SelectedUser selectedUser, Vehicle vehicle) {

        Call<Parking>  vehiclesCall = demoClient.getDemoApi().
                startParking(selectedUser.getUserInfo().getUserId() , vehicle);

        vehiclesCall.enqueue( new Callback<Parking>(){
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                bus.post(new ParkingStartEvent(response.body()));
            }
            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                ParkingStartEvent event = new ParkingStartEvent();
                event.setError(true);
                bus.post(event);
            }
        });
    }

    @Override
    public void stopParking(SelectedUser selectedUser, Parking parking) {
        Call<Parking> vehiclesCall = demoClient.getDemoApi().
                stopParking(selectedUser.getUserInfo().getUserId() , parking);

        vehiclesCall.enqueue( new Callback<Parking>(){
            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                bus.post(new ParkingStopEvent(response.body()));
            }
            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                ParkingStopEvent event = new ParkingStopEvent();
                event.setError(true);
                bus.post(event);
            }
        });
    }
}
