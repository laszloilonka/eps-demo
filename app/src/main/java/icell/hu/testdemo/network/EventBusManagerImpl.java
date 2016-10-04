package icell.hu.testdemo.network;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.event.LoginEvent;
import icell.hu.testdemo.model.event.ParkingStartedEvent;
import icell.hu.testdemo.model.event.ParkingStoppedEvent;
import icell.hu.testdemo.model.event.ParkingsDownloadedEvent;
import icell.hu.testdemo.model.event.VehicleAddedEvent;
import icell.hu.testdemo.model.event.VehiclesDownloadedEvent;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 2016. 09. 30..
 */

public class EventBusManagerImpl implements EventBusManager {

    public static final String TAG = EventBusManagerImpl.class.getSimpleName();

    private EventBus eventBus = EventBus.getDefault();
    private DemoClient demoClient;

    public EventBusManagerImpl(DemoClient demoClient) {
        this.demoClient = demoClient;
    }


    public void setDemoClient(DemoClient demoClient) {
        this.demoClient = demoClient;
    }

    // user  -login
    @Override
    public void login(DemoCredentials demoCredentials) {
        UserCredentials user = new UserCredentials(
                demoCredentials.getUsername(),
                demoCredentials.getPassword());

        final Call<UserInfo> loginCall = demoClient.getDemoApi().login(user);
        loginCall.enqueue(new Callback<UserInfo>() {

            LoginEvent event = new LoginEvent();

            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.code() == 200 && response.body() != null) {
                    event.setUserInfo(response.body());
                } else {
                    event.setError(true);
                }
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                event.setError(true);
                eventBus.post(event);
            }
        });
    }

    //    Vehicle
    @Override
    public void getVehicles(SelectedUser selectedUser) {
        Call<List<Vehicle>> vehiclesCall = demoClient.getDemoApi().
                getVehicles(selectedUser.getUserInfo().getUserId());
        vehiclesCall.enqueue(new Callback<List<Vehicle>>() {

            VehiclesDownloadedEvent event = new VehiclesDownloadedEvent();

            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (response.code() == 200) {
                    event.setVehicles(response.body());
                } else {
                    event.setError(true);
                }
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                event.setError(true);
                eventBus.post(event);
            }
        });
    }

    @Override
    public void addVehicle(SelectedUser selectedUser, Vehicle vehicle) {
        Call<Vehicle> vehiclesCall = demoClient.getDemoApi().
                addVehicle(selectedUser.getUserInfo().getUserId(), vehicle);
        vehiclesCall.enqueue(new Callback<Vehicle>() {

            VehicleAddedEvent event = new VehicleAddedEvent();

            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.code() == 200) {
                    event.setVehicle(response.body());
                } else {
                    event.setError(true);
                }
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                event.setError(true);
                eventBus.post(event);
            }
        });
    }

    //    Parking

    @Override
    public void getParkings(SelectedUser selectedUser) {
        Call<List<Parking>> vehiclesCall = demoClient.getDemoApi().
                getParkings(selectedUser.getUserInfo().getUserId());
        vehiclesCall.enqueue(new Callback<List<Parking>>() {

            ParkingsDownloadedEvent event = new ParkingsDownloadedEvent();

            @Override
            public void onResponse(Call<List<Parking>> call, Response<List<Parking>> response) {
                if (response.code() == 200) {
                    event.setParking(response.body());
                } else {
                    event.setError(true);
                }
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<List<Parking>> call, Throwable t) {
                event.setError(true);
                eventBus.post(event);
            }
        });
    }


    @Override
    public void startParking(SelectedUser selectedUser, Vehicle vehicle) {

        Call<Parking> vehiclesCall = demoClient.getDemoApi().
                startParking(selectedUser.getUserInfo().getUserId(), vehicle);

        vehiclesCall.enqueue(new Callback<Parking>() {

            ParkingStartedEvent event = new ParkingStartedEvent();

            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                if (response.code() == 200) {
                    event.setParking(response.body());
                } else {
                    event.setError(true);
                }
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                event.setError(true);
                eventBus.post(event);
            }
        });
    }

    @Override
    public void stopParking(SelectedUser selectedUser, Parking parking) {
        Call<Parking> vehiclesCall = demoClient.getDemoApi().
                stopParking(selectedUser.getUserInfo().getUserId(), parking);

        vehiclesCall.enqueue(new Callback<Parking>() {

            ParkingStoppedEvent event = new ParkingStoppedEvent();

            @Override
            public void onResponse(Call<Parking> call, Response<Parking> response) {
                if (response.code() == 200) {
                    event.setParking(response.body());
                } else {
                    event.setError(true);
                }
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<Parking> call, Throwable t) {
                event.setError(true);
                eventBus.post(event);
            }
        });
    }
}
