package icell.hu.testdemo.ui.adapter;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Parking;

/**
 * Created by User on 2016. 09. 29..
 */

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ViewHolder> {

    public static final String TAG = ParkingAdapter.class.getSimpleName();

    View parent;
    public List<Parking> parkingList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView duration;
        public TextView address;
        public LinearLayout container;

        public ViewHolder(View view) {
            super(view);
            address = (TextView) view.findViewById(R.id.parking_address);
            duration = (TextView) view.findViewById(R.id.parking_duration);
            container = (LinearLayout) view.findViewById(R.id.parking_container);
        }
    }


    public ParkingAdapter(View app, List<Parking> parkingList) {
        this.parkingList = parkingList;
        parent = app;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_parking, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onViewDetachedFromWindow(final ViewHolder holder) {
        ((ViewHolder) holder).container.clearAnimation();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Parking parking = parkingList.get(position);
        updateRow(holder, parking);
        setAnimation(holder.container, parking);
    }

    private void setAnimation(View viewToAnimate, Parking parking) {
        if (viewToAnimate.getAnimation() == null && parking.isParking()) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(),
                    android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
        }
    }


    private void updateRow(ViewHolder holder, Parking parking) {

        holder.address.setText(
                getPlateNumberFromId(parking.getParkingId()));

        if (parking.getStartedAt() != null && parking.getFinishedAt() != null) {

            long duration = parking.getFinishedAt() - parking.getStartedAt();

            String durationText = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            holder.duration.setText(durationText);
            holder.container.setBackground(new ColorDrawable(parent.getResources()
                    .getColor(R.color.colorLightOrange)));
        } else {
            holder.container.setBackground(new ColorDrawable(parent.getResources()
                    .getColor(R.color.colorLightBlue)));
            holder.duration.setText("");
        }
    }


    private String getPlateNumberFromId(Long parkingID) {                                           // TODO
        return "Lorem ipsum dolor sit amet, consectetur adipisicing elit. ID:" + parkingID;
    }

    @Override
    public int getItemCount() {
        return parkingList.size();
    }


    public void addNewItem(Parking parking) {
        if (parking != null && !parkingList.contains(parking)) {
            parkingList.add(0, parking);
            notifyDataSetChanged();
        }
    }

    public void changeRow(Parking changedParking) {
        if (changedParking != null) {
            for (int i = 0; i < parkingList.size(); i++) {
                if (changedParking.getParkingId() == parkingList.get(i).getParkingId()) {
                    parkingList.set(i, changedParking);
                    break;
                }
            }
            notifyDataSetChanged();
        }
    }


}