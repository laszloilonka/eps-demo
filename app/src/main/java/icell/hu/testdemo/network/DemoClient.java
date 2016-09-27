package icell.hu.testdemo.network;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static icell.hu.testdemo.consts.Consts.BASE_URL;

/**
 * Created by ilaszlo on 13/09/16.
 */

public class DemoClient {

    private EventBus eventBus;

    private DemoApi demoApi;

    private Interceptor lastAuthenticationInterceptor = null;

    public DemoClient(EventBus eventBus) {
        this.eventBus = eventBus;

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(6, TimeUnit.SECONDS)
                .connectTimeout(6, TimeUnit.SECONDS)
                /*.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        String credential = Credentials.basic(username, password);
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", credential)
                                .header("Accept", "application/json")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })*/
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        demoApi = retrofit.create(DemoApi.class);

    }

    public DemoApi getDemoApi() {
        return demoApi;
    }
}
