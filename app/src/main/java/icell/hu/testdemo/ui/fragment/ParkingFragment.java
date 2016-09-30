package icell.hu.testdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import icell.hu.testdemo.MainActivity;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.Interfaces.ParkingListener;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.singleton.Parkings;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.singleton.Vehicles;
import icell.hu.testdemo.ui.adapter.VehiclesAdapter;
import icell.hu.testdemo.ui.adapter.parking.ParkingAdapter;

/**
 * Created by User on 2016. 09. 29..
 */

public class ParkingFragment extends BaseFragment implements ParkingListener {

    @Inject
    SelectedUser selectedUser;

    @Inject
    RXManager rxManager;

    @Inject
    Parkings parkings;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    ParkingAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this) ;
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        View layout = inflater.inflate ( R.layout.fragment_parking , container, false);                //  TODO fragment_main
        return layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind (this, view);

        ((MainActivity) getActivity()).getSupportActionBar()
                .setTitle(R.string.title_fragment_parkings);
        if ( parkings.parking ( ) != null )
            setAdapter ( parkings.parking ( ) ) ;
        subscription = rxManager . getParkings ( selectedUser , this ) ;


    }


    @Override
    public void failed() {
        Toast.makeText( getActivity ( ) , getString(R.string.error_something_went_wrong) + " (" +
                selectedUser.getUserInfo().getUserId() + ")!" , Toast.LENGTH_SHORT ) . show ( ) ;
    }


    private void setAdapter ( List<Parking> parkings ) {
        adapter = new ParkingAdapter( (DemoApplication) getActivity().getApplication() , parkings);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter( adapter);
    }

    private void checkList() {
        adapter.notifyDataSetChanged();                                                             // TODO remove event....
    }



    @Override
    public void parkingLoaded(List<Parking> parkings) {
        setAdapter(parkings);
    }

    @Override
    public void onProcessFinished(Parking parking) {

    }
}
