package icell.hu.testdemo.di;

import org.greenrobot.eventbus.EventBus;
import javax.inject.Singleton;
import dagger.Component;
import icell.hu.testdemo.LoginActivity;
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.ui.adapter.parking.ParkingAdapter;
import icell.hu.testdemo.ui.fragment.LoginFragment;
import icell.hu.testdemo.ui.fragment.MainFragment;
import icell.hu.testdemo.ui.fragment.ParkingFragment;
import icell.hu.testdemo.ui.fragment.VehiclesFragment;

@Singleton
@Component(modules = {AppModule.class, DemoModule.class})
public interface DemoComponent {

    EventBus eventBus();

    void inject(MainActivity activity);

    void inject(LoginActivity activity);




    void inject(MainFragment fragment);

    void inject(LoginFragment fragment);

    void inject(VehiclesFragment fragment);

    void inject(ParkingFragment fragment);




    void inject(ParkingAdapter adapter);


}