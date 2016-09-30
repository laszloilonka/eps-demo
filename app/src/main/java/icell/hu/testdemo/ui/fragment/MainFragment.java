package icell.hu.testdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.Interfaces.ParkingListener;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.singleton.Parkings;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.singleton.Vehicles;

/**
 * Created by User on 2016. 09. 29..
 */

public class MainFragment extends BaseFragment implements VehicleListener, ParkingListener {

    @Inject
    SelectedUser selectedUser;

    @Inject
    RXManager rxManager;

    @Inject
    Vehicles vehicles;

    @Inject
    Parkings parkings;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this) ;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View layout = inflater.inflate ( R.layout.fragment_main , container, false);               //  TODO fragment_login

        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind (this, view);
        setHasOptionsMenu(true);
        reloadData();
    }
    @OnClick(R.id.frg_main_manage_vehicles)
    void manageVehiclesPushed() {
        clickedOn ( new VehiclesFragment() );
    }


    @OnClick (R.id.frg_main_manage_parking)
    void manageParkingPushed() {
        clickedOn ( new ParkingFragment() );
    }


    private void clickedOn(@NonNull Fragment fragment) {
        String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_refresh:
                reloadData();
                break;
        }
        return true;

    }

    private void reloadData() {
        if ( subscription == null ) {
            subscription = rxManager. getVehicles(selectedUser, this);
            return;
        }
        if ( ! subscription.isUnsubscribed() ){
            subscription.unsubscribe();
        }
        subscription = rxManager.getVehicles(selectedUser, this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy() ;
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
    }




    @Override
    public void vehiclesLoaded(List<Vehicle> list ) {
        vehicles . setVehicles ( list ) ;
        subscription.unsubscribe();
        subscription = rxManager.getParkings(selectedUser, this );
    }
    @Override
    public void vehiclesAdded ( Vehicle vehicles ) {}

    @Override
    public void failed() {
        Toast.makeText(getActivity(), getString(R.string.error_something_went_wrong) + " (" +
                selectedUser.getUserInfo().getUserId() + ")!" , Toast.LENGTH_SHORT ) . show ( ) ;
    }


    @Override
    public void parkingLoaded(List<Parking> list) {
        parkings.setVehicles(list);
    }

    @Override
    public void onProcessFinished(Parking parking) {

    }
}
