package icell.hu.testdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.model.event.AddedVehicleEvent;
import icell.hu.testdemo.model.event.GetParkingsEvent;
import icell.hu.testdemo.model.event.GetVehiclesEvent;
import icell.hu.testdemo.model.event.ParkingStartEvent;
import icell.hu.testdemo.model.event.ParkingStopEvent;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.singleton.Vehicles;
import icell.hu.testdemo.ui.adapter.ParkingAdapter;
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

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.vehicle_container)
    RelativeLayout descriptionContainer;


    @BindView(R.id.vehicle_recycler_view)
    RecyclerView recyclerView;


    RoundedButton roundedButton;
    VehiclesAdapter vehiclesAdapter;
    ParkingAdapter parkingAdapter;


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
        vehicles.getVehicles().clear();
        vehicles.getVehicleParkings().clear();
        eventBusManager.getParkings(selectedUser);
        eventBusManager.getVehicles(selectedUser);
    }

    private void setVehiclesAdapter() {

        List<Vehicle> list = new ArrayList<>();

        for (int i = 0, nsize = vehicles.getVehicles().size(); i < nsize; i++) {
            list.add(vehicles.getVehicles().valueAt(i));
        }

        vehiclesAdapter = new VehiclesAdapter(list);
        spinner.setAdapter(vehiclesAdapter);
        if (descriptionContainer.getVisibility() != View.VISIBLE) {
            descriptionContainer.setVisibility(View.VISIBLE);
        }
    }

    public void setParkingAdapter() {
        Vehicle vehicle = (Vehicle) spinner.getSelectedItem();
        if (vehicle != null) {
            List<Parking> parkingList = vehicles.getVehicleParkings(vehicle.getVehicleId());
            parkingAdapter = new ParkingAdapter(getView(), parkingList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(parkingAdapter);
            recyclerView.setHasFixedSize(true);
            parkingAdapter.notifyDataSetChanged();
            recyclerView.invalidate();
        }
    }

    public void addParkingToAdapter(Parking parking) {
        if (parkingAdapter != null) {
            parkingAdapter.addNewItem(parking);
        } else
            setParkingAdapter();
    }

    // onclick

    @Override
    public void onButtonClicked() {
        if (!roundedButton.isProcessRunning()) {
            Vehicle vehicle = (Vehicle) spinner.getSelectedItem();
            Parking parking = vehicles.getLatestParking(vehicle.getVehicleId());
            Log.d(TAG, vehicle.getPlateNumber());
            roundedButton.startProcess();

            if (parking == null) {
                eventBusManager.startParking(selectedUser, vehicle);
                Log.d(TAG, "parking is null! start parking!");
            } else if (parking.isParking()) {
                eventBusManager.stopParking(selectedUser, parking);
                Log.d(TAG, "stop parking event sent");
            } else {
                eventBusManager.startParking(selectedUser, vehicle);
                Log.d(TAG, "Start parking event sent! " + parking.getFinishedAt());
            }
        }
    }


    @OnClick(R.id.vehicle_add_new_vehicle)
    public void addNewVehicle() {
        FragmentManager fm = getFragmentManager();
        AddVehicleDialog dialogFragment = new AddVehicleDialog();
        dialogFragment.show(fm, "dialog_fragment");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        roundedButton.stopProcess();
        if (vehicles.getVehicles().size() == 0) {
            return;
        }
        Vehicle vehicle = (Vehicle) spinner.getSelectedItem();
        Parking parking = vehicles.getLatestParking(vehicle.getVehicleId());
        if (parking != null) {
            roundedButton.setText(getString(R.string.action_stop_parking));
        } else {
            roundedButton.setText(getString(R.string.action_start_parking));
        }
        setParkingAdapter();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    // events
    @Subscribe
    public void onEvent(GetVehiclesEvent event) {
        if (event.isError()) {
            Toast.makeText(getActivity(), getString(R.string.error_get_vehicles)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        for (Vehicle vehicle : event.getVehicles()) {
            vehicles.addVehicle(vehicle);
        }
        setVehiclesAdapter();
    }

    @Subscribe
    public void onEvent(AddedVehicleEvent event) {
        Log.d(TAG, "Vehicle Added Event catched");
        if (event.isError()) {
            Toast.makeText(getActivity(), getString(R.string.error_default)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        vehicles.addVehicle(event.getVehicle());

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
        for (Parking parking : event.getParking()) {
            vehicles.addParking(parking);
        }
        setParkingAdapter();
    }

    @Subscribe
    public void onEvent(ParkingStartEvent event) {
        if (event.isError()) {
            roundedButton.stopProcess();
            Toast.makeText(getActivity(), getString(R.string.error_default)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d(TAG, "parking START event catched");
        Vehicle vehicle = (Vehicle) spinner.getSelectedItem();
        vehicles.addParking(event.getParking());

        if (event.getParking().getVehicleId() == vehicle.getVehicleId()) {
            roundedButton.stopProcess();
            roundedButton.setText(getString(R.string.action_stop_parking));
            addParkingToAdapter(event.getParking());
        }
    }

    @Subscribe
    public void onEvent(ParkingStopEvent event) {
        if (event.isError()) {
            Toast.makeText(getActivity(), getString(R.string.error_get_parkings)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        vehicles.addParking(event.getParking());
        Vehicle vehicle = (Vehicle) spinner.getSelectedItem();
        if (event.getParking().getVehicleId() == vehicle.getVehicleId()) {
            roundedButton.setText(getString(R.string.action_start_parking));
            roundedButton.stopProcess();
            parkingAdapter.changeRow(event.getParking());
        }


    }
}
