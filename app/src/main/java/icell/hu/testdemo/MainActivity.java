package icell.hu.testdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.network.Interfaces.VehicleListener;
import icell.hu.testdemo.network.RXManager;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.ui.activity.BaseActivity;
import icell.hu.testdemo.ui.adapter.VehiclesAdapter;
import icell.hu.testdemo.ui.fragment.BaseFragment;
import icell.hu.testdemo.ui.fragment.LoginFragment;
import icell.hu.testdemo.ui.fragment.VehiclesFragment;
import rx.Subscription;

public class MainActivity extends BaseActivity implements VehicleListener {

    @Inject
    DemoClient demoClient;

    @Inject
    DemoCredentials demoCredentials;

    @Inject
    SelectedUser selectedUser;

    @Inject
    RXManager rxManager;

    Subscription subscription ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDemoApplication().getComponent().inject(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new VehiclesFragment() , this.toString())
                    .commit();

            Toast.makeText(this, selectedUser.getUserInfo().getLastName(), Toast.LENGTH_SHORT).show();

            refreshRepositories();

        }

    }

    /**
     * Just for TEST
     */
    private void refreshRepositories() {
        if ( subscription == null ) {
            subscription = rxManager.getAvailableVehicles(selectedUser, this);
            return;
        }
        if ( ! subscription.isUnsubscribed() ){
            subscription.unsubscribe();
        }
        subscription = rxManager.getAvailableVehicles(selectedUser, this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy() ;
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
    }

    private void createRepository() {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_repos) {
            refreshRepositories();
            return true;
        } else if (item.getItemId() == R.id.new_repos) {
            createRepository();
            return true;
        }
        return false;
    }


    @Override
    public void vehiclesFinished(Vehicle[] vehicles) {

        Toast.makeText(this, "Activity - Vehicles cathced: " + vehicles.length , Toast.LENGTH_SHORT )
                . show ( ) ;

    }

    @Override
    public void failed() {
        Toast.makeText(this, getString(R.string.error_something_went_wrong) + " (" +
                selectedUser.getUserInfo().getUserId() + ")!" , Toast.LENGTH_SHORT ) . show ( ) ;
    }
}
