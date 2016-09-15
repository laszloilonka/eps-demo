package icell.hu.testdemo.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Vehicle;

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {
    private Vehicle[] vehicles;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView vehicleInfoText;

        public ViewHolder(View v) {
            super(v);
            vehicleInfoText = (TextView) v.findViewById(R.id.info_text);
        }
    }

    public VehicleListAdapter(Vehicle[] vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public VehicleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vehicle vehicle = vehicles[position];
        holder.vehicleInfoText.setText(vehicle.getVehicleId() + " - " + vehicle.getPlateNumber());
    }

    @Override
    public int getItemCount() {
        return vehicles.length;
    }
}

