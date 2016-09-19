package icell.hu.testdemo.di;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Component;
import icell.hu.testdemo.LoginActivity;
import icell.hu.testdemo.MainActivity;

@Singleton
@Component(modules = {AppModule.class, DemoModule.class})
public interface DemoComponent {

    EventBus eventBus();

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

}