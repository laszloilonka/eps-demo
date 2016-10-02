package icell.hu.testdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.event.GetParkingsEvent;
import icell.hu.testdemo.model.event.GetVehiclesEvent;
import icell.hu.testdemo.model.event.ParkingStartEvent;
import icell.hu.testdemo.model.event.ParkingStopEvent;
import icell.hu.testdemo.model.event.AddedVehicleEvent;
import icell.hu.testdemo.singleton.Parkings;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.singleton.Vehicles;
import icell.hu.testdemo.ui.adapter.VehiclesAdapter;
import icell.hu.testdemo.ui.dialog.AddVehicleDialog;
import icell.hu.testdemo.ui.view.RoundedButton;

/**
 * Created by User on 2016. 09. 28..
 */

public class VehiclesFragment extends BaseFragment implements
        AdapterView.OnItemSelectedListener, RoundedButton.RoundedButtomListener {

    public static final String TAG = VehiclesFragment.class.getSimpleName();

    @Inject
    SelectedUser selectedUser;

    @Inject
    Vehicles vehicles;

    @Inject
    Parkings parkings;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.vehicle_container)
    RelativeLayout descriptionContainer;

    RoundedButton roundedButton;
    VehiclesAdapter vehiclesAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this);
        reloadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_vehicles, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.title_fragment_vehicles);

        roundedButton = new RoundedButton(view, this);
        descriptionContainer.setVisibility(View.INVISIBLE);

        reloadData();

        spinner.setOnItemSelectedListener(this);
        bus.register(this);
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
        eventBusManager.getParkings(selectedUser);
        eventBusManager.getVehicles(selectedUser);
    }

    private void setVehiclesAdapter() {
        vehiclesAdapter = new VehiclesAdapter(vehicles.getVeichles());
        spinner.setAdapter(vehiclesAdapter);
        if (descriptionContainer.getVisibility() != View.VISIBLE) {
            descriptionContainer.setVisibility(View.VISIBLE);
        }
    }

    public void setButtonText ( Parking parking ){
        if (parking == null) {
            roundedButton.setText(getString(R.string.action_start_parking));
            return;
        }
        String text = parking.isParking() ?
                getString(R.string.action_stop_parking) :
                getString(R.string.action_start_parking);
        roundedButton.setText(text);
    }


    // onclick

    @Override
    public void onButtonClicked() {
        Vehicle vehicle = vehicles.getVeichles().get(spinner.getSelectedItemPosition());

        Parking parking = getCurrentParkingFromVehicle(vehicle);

        if (parking == null) {                                                              // TODO meg nincs parkolasi info
            //parkingButton.setVisibility(View.INVISIBLE);
            //progressBar.setVisibility(View.INVISIBLE);
            if (vehicle != null) {
                eventBusManager.startParking(selectedUser, vehicle);
                roundedButton.startProcess();
            }
            return;
        }
        Log.d(TAG , vehicle.getPlateNumber());
        Log.d(TAG , "parking: " + parking.getFinishedAt());

        roundedButton.startProcess();

        if (parking.isParking()) {
            eventBusManager.stopParking(selectedUser, parking);
            Log.d(TAG, "stop parking event sent");
        } else {
            eventBusManager.startParking(selectedUser, vehicle);
            Log.d(TAG, "start parking event sent");
        }

    }


    @OnClick(R.id.vehicle_add_new_vehicle)
    public void addNewVehicle() {
        FragmentManager fm = getFragmentManager();
        AddVehicleDialog dialogFragment = new AddVehicleDialog();
        dialogFragment.show(fm, "dialog_fragment");
    }


    private Parking getCurrentParkingFromVehicle(Vehicle vehicle) {
        if (parkings.getParkings() == null)
            return null;

        for (Parking parking : parkings.getParkings()) {
            if (parking.getVehicleId() == vehicle.getVehicleId()) {
                return parking;
            }
        }
        return null;
    }

    private Parking getStoredParkingEvent(Parking eventParking) {
        if (parkings.getParkings() == null)
            return null;

        for (Parking parking : parkings.getParkings()) {
            if (parking.getParkingId() == eventParking.getParkingId()) {
                return parking;
            }
        }
        return null;
    }
    // events

    @Subscribe
    public void onEvent(GetVehiclesEvent event) {
        if (event.isError()) {
            Toast.makeText(getActivity(), getString(R.string.error_get_vehicles)
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        vehicles.setVehicles(event.getVehicles());
        setVehiclesAdapter();

    }

    @Subscribe
    public void onEvent(AddedVehicleEvent event) {
        Log.d(TAG, "Vehicle Added Event catched");

        if (event.isError()) {
            Toast.makeText(getActivity(), getString(R.string.error_add_vehicle)
                    , Toast.LENGTH_SHORT).show();
            return;
        }

        if (vehicles.getVeichles() == null)
            vehicles.setVehicles(new ArrayList<Vehicle>());

        for (Vehicle vehicle : vehicles.getVeichles()) {
            if (vehicle.getVehicleId() == event.getVehicle().getVehicleId()) {
                Log.d(TAG, "Vehicle has been created previously...");
                return;
            }
        }
        vehicles.getVeichles().add(event.getVehicle());
        if (vehiclesAdapter == null) {
            setVehiclesAdapter();
        } else {
            vehiclesAdapter.addNewVehicle(event.getVehicle());
        }
    }

    // Parkings events
    @Subscribe
    public void onEvent(GetParkingsEvent event) {

        if (event.isError()) {
            Toast.makeText(getActivity(), getString(R.string.error_get_parkings)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        parkings.setParking(event.getParking());
    }

    @Subscribe
    public void onEvent(ParkingStartEvent event) {
        Log.d(TAG, "parking start event catched");
        roundedButton.stopProcess();
        Vehicle vehicle = vehicles.getVeichles().get(spinner.getSelectedItemPosition());
        if (vehicle == null) {
            return;
        }
        Parking parking = getStoredParkingEvent(event.getParking());
        if (parking == null) {
            return;
        }

        parking.setFinishedAt(null);

        if (parking.getVehicleId() == vehicle.getVehicleId()) {
            setButtonText(parking);
        }
    }

    @Subscribe
    public void onEvent(ParkingStopEvent event) {
        Log.d(TAG, "parking stop event catched");
        roundedButton.stopProcess();
        Parking parking = getStoredParkingEvent(event.getParking());
        if (parking == null) {
            Log.e(TAG, "ERROR parking obj");
            return;
        }
        Vehicle selectedVehicle = vehicles.getVeichles().get(spinner.getSelectedItemPosition());
        if (selectedVehicle == null) {
            Log.e(TAG, "ERROR vehicle obj");
            return;
        }
        parking.setFinishedAt(System.currentTimeMillis());
        if (parking.getVehicleId() == selectedVehicle.getVehicleId()) {
            setButtonText(parking);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roundedButton.stopProcess();
        if (vehicles.getVeichles() == null) {
            return;
        }
        Parking parking = getCurrentParkingFromVehicle(vehicles.getVeichles().get(position));
        setButtonText(parking);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}