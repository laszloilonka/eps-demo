package icell.hu.testdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.network.RXManagerImpl;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.singleton.Vehicles;
import icell.hu.testdemo.ui.adapter.VehiclesAdapter;

/**
 * Created by User on 2016. 09. 28..
 */

public class VehiclesFragment extends BaseFragment implements VehicleListener {

    public static final String TAG = VehiclesFragment.class.getSimpleName();

    @Inject
    SelectedUser selectedUser;

    @Inject
    RXManager rxManager;

    @Inject
    Vehicles vehicles;

    @BindView(R.id.spinner) Spinner spinner;


    VehiclesAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this) ;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View layout = inflater.inflate ( R.layout.activity_main , container, false);                //  TODO fragment_main
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind (this, view);
        Log.d ( TAG , "register" ) ;
        if ( vehicles .veichles ( ) != null )
            setAdapter ( vehicles . veichles ( ) ) ;
        subscription = rxManager . getVehicles ( selectedUser, this ) ;


    }


    @Override
    public void failed() {
        Toast.makeText( getActivity ( ) , getString(R.string.error_something_went_wrong) + " (" +
                selectedUser.getUserInfo().getUserId() + ")!" , Toast.LENGTH_SHORT ) . show ( ) ;
    }


    private void setAdapter ( List<Vehicle> vehicles ) {
        adapter = new VehiclesAdapter(vehicles);
        spinner.setAdapter(adapter);
    }

    private void checkList() {
        adapter.notifyDataSetChanged();                                                             // TODO remove event....
    }



    @Override
    public void vehiclesLoaded(List<Vehicle> vehicles ) {
        if ( adapter == null ){
            setAdapter(vehicles);
            return;
        }else
            checkList();
    }


    @Override
    public void vehiclesAdded (Vehicle vehicles) {                                                   // TODO new event eseten!!
        Toast.makeText(getActivity(), getResources().getString(R.string.vehicles_added) ,
                Toast.LENGTH_SHORT ) . show ( ) ;
    }
}
