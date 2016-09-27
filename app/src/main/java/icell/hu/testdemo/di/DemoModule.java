package icell.hu.testdemo.di;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.network.RXManagerImpl;
import icell.hu.testdemo.singleton.ActivityPresenter;
import icell.hu.testdemo.singleton.ActivityPresenterImpl;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.singleton.SelectedUserImpl;

@Module
public class DemoModule {

    public DemoModule() {
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    public DemoClient provideDemoClient(EventBus eventBus) {
        return new DemoClient(eventBus);
    }

    @Provides
    @Singleton
    DemoCredentials provideDemoCredentials() {
        return new DemoCredentials();
    }

    @Provides
    @Singleton
    public ActivityPresenter provideActivityPresenter() {
        return new ActivityPresenterImpl();
    }

    @Provides
    @Singleton
    public SelectedUser provideSelectedUser() {
        return new SelectedUserImpl();
    }

    @Provides
    @Singleton
    public RXManager provideRxManager(DemoClient demoClient) {
        return new RXManagerImpl(demoClient);
    }

}