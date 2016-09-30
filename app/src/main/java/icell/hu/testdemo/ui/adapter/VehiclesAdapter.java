package icell.hu.testdemo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Vehicle;

public class VehiclesAdapter extends BaseAdapter {

    private List<Vehicle> vehicles;

    public VehiclesAdapter(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public int getCount() {
        return vehicles.size();
    }

    @Override
    public Object getItem(int i) {
        return vehicles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return vehicles.get(i).getVehicleId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vehicle_view, viewGroup, false);
        Vehicle vehicle = vehicles.get(i);
        TextView vehicleInfoText = (TextView) view.findViewById(R.id.info_text);
        vehicleInfoText.setText(vehicle.getVehicleId() + " - " + vehicle.getPlateNumber());
        return view;
    }
}