package icell.hu.testdemo.network;

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

    @GET("/{userId}/vehicles")
    Call<Vehicle[]> getVehicles(@Path("userId") Long userId);

    //@POST("/user/repos")
    //Call<Repository> createRepository(@Header(HEADER_AUTHORIZATION) String authorization, @Body RepositoryRequest repository);
}
