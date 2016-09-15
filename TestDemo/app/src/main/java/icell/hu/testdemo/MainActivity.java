package icell.hu.testdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import javax.inject.Inject;

import icell.hu.testdemo.model.Vehicle;
import icell.hu.testdemo.network.DemoClient;
import icell.hu.testdemo.singleton.DemoCredentials;
import icell.hu.testdemo.singleton.SelectedUser;
import icell.hu.testdemo.view.VehiclesAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<Vehicle[]> {

    @Inject
    DemoClient demoClient;

    @Inject
    DemoCredentials demoCredentials;

    @Inject
    SelectedUser selectedUser;

    private Spinner spinner;
   /* private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;*/
    private DemoApplication demoApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDemoApplication().getComponent().inject(this);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);


        //recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        Toast.makeText(this, selectedUser.getUserInfo().getLastName(), Toast.LENGTH_SHORT).show();
        
        refreshRepositories();
    }

    private void refreshRepositories() {
        Call<Vehicle[]> call = demoClient.getDemoApi().getVehicles(selectedUser.getUserInfo().getUserId());
        call.enqueue(this);
    }

    private void createRepository() {
      /*  Call<Repository> call = demoClient.getDemoApi().createRepository(demoCredentials.getBasicScheme(), new RepositoryRequest("test" + recyclerView.getAdapter().getItemCount()));
        call.enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                refreshRepositories();
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {

            }
        });*/
    }

    public DemoApplication getDemoApplication() {
        if (demoApplication == null) {
            demoApplication = ((DemoApplication) getApplicationContext());
        }
        return demoApplication;
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
    public void onResponse(Call<Vehicle[]> call, Response<Vehicle[]> response) {
       // adapter = new VehicleListAdapter(response.body());
       // recyclerView.setAdapter(adapter);
        VehiclesAdapter adapter = new VehiclesAdapter(response.body());
        spinner.setAdapter(adapter);
    }

    @Override
    public void onFailure(Call<Vehicle[]> call, Throwable t) {

    }
}
