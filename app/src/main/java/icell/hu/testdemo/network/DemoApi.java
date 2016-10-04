package icell.hu.testdemo.network;

import java.util.List;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.UserInfo;
import icell.hu.testdemo.model.Vehicle;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ilaszlo on 13/09/16.
 */

public interface DemoApi {


    @POST("/login")
    Call<UserInfo> login(@Body UserCredentials credentiale);

    @GET("/user/{userId}/vehicles")
    Call<List<Vehicle>> getVehicles(@Path("userId") Long userId);

    @POST("/user/{userId}/vehicles")
    Call<Vehicle> addVehicle(@Path("userId") Long userId , @Body Vehicle vehicle );

    @GET("/user/{userId}/current_parkings")
    Call<List<Parking>> getParkings(@Path("userId") Long userId);


    @POST("/user/{userId}/start_parking")
    Call<Parking> startParking(@Path("userId") Long userId , @Body Vehicle vehicle );

    @POST("/user/{userId}/stop_parking")
    Call<Parking> stopParking(@Path("userId") Long userId , @Body Parking parking);

}
