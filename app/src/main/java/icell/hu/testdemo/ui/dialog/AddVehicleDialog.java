package icell.hu.testdemo.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import icell.hu.testdemo.DemoApplication;
import icell.hu.testdemo.R;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.EventBusManager;
import icell.hu.testdemo.singleton.SelectedUser;

/**
 * Created by User on 2016. 09. 30..
 */

public class AddVehicleDialog extends DialogFragment {

    @Inject
    SelectedUser selectedUser;

    @Inject
    EventBusManager busManager;

    @BindView(R.id.dialog_vehicle_edittext)
    EditText plateNumberEdittext;


    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication) getActivity().getApplication()).getComponent().inject(this) ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_new_vehicle, container, false);
        getDialog().setTitle( getString(R.string.dialog_vehicles_title));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unbinder = ButterKnife.bind (this, view);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( unbinder != null )
            unbinder.unbind();
    }

    @OnClick(R.id.dialog_ok)
    void okPushed() {
        if (TextUtils.isEmpty(plateNumberEdittext.getText().toString())){
            Toast.makeText(getContext() , getResources().getString(R.string.prompt_vehicle_error)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber(plateNumberEdittext.getText().toString());
        busManager.addVehicle(selectedUser , vehicle );
        dismiss();
    }

    @OnClick(R.id.dialog_cancel)
    void cancelPushed() {
        dismiss();
    }



}
