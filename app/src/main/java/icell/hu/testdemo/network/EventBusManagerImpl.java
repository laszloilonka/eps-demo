package icell.hu.testdemo.network;

/**
 * Created by User on 2016. 09. 30..
 */

public class EventBusManagerImpl implements EventBusManager{

    public static final String TAG = EventBusManagerImpl.class.getSimpleName();


    private DemoClient demoClient;

    public EventBusManagerImpl ( DemoClient demoClient ) {
        this.demoClient = demoClient;
    }


    
}
