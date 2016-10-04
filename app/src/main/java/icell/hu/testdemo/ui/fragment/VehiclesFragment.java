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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import icell.hu.testdemo.model.event.ParkingStartedEvent;
import icell.hu.testdemo.model.event.ParkingStoppedEvent;
import icell.hu.testdemo.model.event.ParkingsDownloadedEvent;
import icell.hu.testdemo.model.event.VehicleAddedEvent;
import icell.hu.testdemo.model.event.VehiclesDownloadedEvent;
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
    public Spinner spinner;

    @BindView(R.id.vehicle_container)
    RelativeLayout descriptionContainer;


    @BindView(R.id.vehicle_recycler_view)
    RecyclerView recyclerView;


    RoundedButton roundedButton;
    VehiclesAdapter vehiclesAdapter;
    ParkingAdapter parkingAdapter;

    ImageView refreshIcon;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this);
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
        eventBus.register(this);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_menu, menu);
        refreshIcon = (ImageView) menu.findItem(R.id.main_refresh).getActionView();
        if (refreshIcon != null) {
            startMeunItemAnimation();
            refreshIcon.setPadding(10 , 10 , 20 , 0);
            refreshIcon.setImageResource(R.drawable.ic_autorenew_white_36dp);
            refreshIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startMeunItemAnimation();
                    reloadData();
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void startMeunItemAnimation() {
        if (refreshIcon != null) {
            if (refreshIcon.getAnimation() == null) {
                Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                rotation.setRepeatCount(Animation.INFINITE);
                rotation.setFillEnabled(true);
                rotation.setFillAfter(true);
                refreshIcon.setAnimation(rotation);
            }
            refreshIcon.startAnimation(refreshIcon.getAnimation());
        }
    }
    private void stopMeunItemAnimation() {
        if (refreshIcon != null) {
            refreshIcon.getAnimation().cancel();
            //refreshIcon.clearAnimation();
        }
    }

    private void reloadData() {
        vehicles.getVehicles().clear();
        vehicles.getVehicleParkings().clear();
        eventBusManager.getVehicles(selectedUser);
        eventBusManager.getParkings(selectedUser);
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
    public void onEvent(VehiclesDownloadedEvent event) {
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
    public void onEvent(VehicleAddedEvent event) {
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
    public void onEvent(ParkingsDownloadedEvent event) {
        stopMeunItemAnimation();
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
    public void onEvent(ParkingStartedEvent event) {
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
    public void onEvent(ParkingStoppedEvent event) {
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
