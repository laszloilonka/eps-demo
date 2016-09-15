package icell.hu.testdemo.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import icell.hu.testdemo.DemoApplication;

@Module
public class AppModule {

    DemoApplication application;

    public AppModule(DemoApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    DemoApplication providesApplication() {
        return application;
    }

}
