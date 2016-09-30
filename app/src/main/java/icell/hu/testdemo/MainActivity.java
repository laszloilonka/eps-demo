package icell.hu.testdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import icell.hu.testdemo.model.Parking;
import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.network.Interfaces.ParkingListener;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.Parkings;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.singleton.Vehicles;
import icell.hu.testdemo.ui.activity.BaseActivity;
import icell.hu.testdemo.ui.fragment.MainFragment;
import rx.Subscription;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDemoApplication().getComponent().inject(this);

        getSupportActionBar().setElevation(0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new MainFragment() , this.toString())
                    .commit();

            //Toast.makeText(this, selectedUser.getUserInfo().getLastName(), Toast.LENGTH_SHORT).show();

        }

    }

}
