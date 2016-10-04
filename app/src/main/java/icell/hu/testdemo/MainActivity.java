package icell.hu.testdemo;

import android.os.Bundle;

import icell.hu.testdemo.ui.activity.BaseActivity;
import icell.hu.testdemo.ui.fragment.VehiclesFragment;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDemoApplication().getComponent().inject(this);

        getSupportActionBar().setElevation(0);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new VehiclesFragment(), this.toString())
                    .commit();

            //Toast.makeText(this, selectedUser.getUserInfo().getLastName(), Toast.LENGTH_SHORT).show();
        }
    }

}
