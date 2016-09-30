package icell.hu.testdemo.ui.adapter.parking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.singleton.Vehicles;

/**
 * Created by User on 2016. 09. 29..
 */

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ViewHolder> {

    @Inject
    Vehicles vehicles;

    private List<Parking> parkingList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView plateNumber ;

        public ViewHolder(View view) {
            super(view);
            plateNumber = (TextView) view.findViewById(R.id.parking_platenumber);
        }
    }


    public ParkingAdapter(DemoApplication app, List<Parking> parkingList) {
        this.parkingList = parkingList;
        app.getComponent().inject(this) ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater .from(parent.getContext())
                .inflate(R.layout.adapter_parking, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder ( ViewHolder holder, int position ) {
        Parking parking = parkingList.get(position);
        holder.plateNumber.setText(
                getPlateNumberFromId(parking.getVehicleId()) );
    }

    private String getPlateNumberFromId(int vehicleId) {
        String plate = "a";
        if ( vehicles.veichles() == null )
            return plate;
        for ( Vehicle vehicle: vehicles.veichles () ) {
            if (vehicle.getVehicleId() == vehicleId)
                return vehicle.getPlateNumber();
        }
        return plate;
    }

    @Override
    public int getItemCount() {
        return parkingList.size();
    }

}
