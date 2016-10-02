package icell.hu.testdemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Unbinder;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.network.EventBusManager;
import icell.hu.testdemo.ui.activity.BaseActivity;
import rx.Subscription;

/**
 *
 * Created by User on 2016. 09. 28..
 *
 * @see LoginFragment
 * @see VehiclesFragment
 */

public class BaseFragment extends Fragment {

    @Inject
    protected DemoApplication application;

    @Inject
    protected EventBus bus;

    @Inject
    public EventBusManager eventBusManager;

    /**
     * UnBind views {@link butterknife.ButterKnife}
     */
    protected Unbinder unbinder;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if ( bus.isRegistered(this) )
            bus.unregister(this);
        if ( unbinder != null ) {
            unbinder.unbind();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();


        RefWatcher refWatcher = application . getRefWatcher ( ) ;
        refWatcher.watch(this);                                                                     // check memory leak... set this fragment as reference
    }





}
