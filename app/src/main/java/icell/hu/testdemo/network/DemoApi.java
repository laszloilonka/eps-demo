package icell.hu.testdemo.network;

import java.util.List;

import icell.hu.testdemo.model.AddVehicle;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.StartParking;
import icell.hu.testdemo.model.StopParking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.UserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ilaszlo on 13/09/16.
 */

public interface DemoApi {

    @POST("/user")
    Observable<UserInfo> login(@Body UserCredentials credentials);

    @GET("/user/{userId}/vehicles")
    Observable<List<Vehicle>> getVehicles(@Path("userId") Long userId);



    @POST("/user/{userId}/vehicles")
    Observable<Vehicle> addVehicle(@Path("userId") Long userId , @Body AddVehicle vehicle );



    @GET("/user/{userId}/current_parkings")
    Observable<List<Parking>> getParkings(@Path("userId") Long userId);


    @POST("/user/{userId}/start_parking")
    Observable<Parking> startParking(@Path("userId") Long userId , @Body StartParking startParking );

    @POST("/user/{userId}/stop_parking")
    Observable<Parking> startParking(@Path("userId") Long userId , @Body StopParking stopParking);



    //@POST("/user/repos")
    //Call<Repository> createRepository(@Header(HEADER_AUTHORIZATION) String authorization, @Body RepositoryRequest repository);
}
