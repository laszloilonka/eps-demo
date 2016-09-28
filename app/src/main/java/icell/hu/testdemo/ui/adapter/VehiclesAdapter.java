package icell.hu.testdemo.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Vehicle;

public class VehiclesAdapter extends BaseAdapter {

    private Vehicle[] vehicles;

    public VehiclesAdapter(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public int getCount() {
        return vehicles.length;
    }

    @Override
    public Object getItem(int i) {
        return vehicles[i];
    }

    @Override
    public long getItemId(int i) {
        return vehicles[i].getVehicleId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.vehicle_view, viewGroup, false);
        Vehicle vehicle = vehicles[i];
        TextView vehicleInfoText = (TextView) view.findViewById(R.id.info_text);
        vehicleInfoText.setText(vehicle.getVehicleId() + " - " + vehicle.getPlateNumber());
        return view;
    }
}