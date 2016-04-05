package de.ahlfeld.breminale.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.adapters.EventAdapter;
import de.ahlfeld.breminale.databinding.ActivityMainBinding;
import de.ahlfeld.breminale.models.BreminaleService;
import de.ahlfeld.breminale.models.Location;
import de.ahlfeld.breminale.viewmodel.MainViewModel;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {


    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this, this);
        binding.setViewModel(mainViewModel);
        setupRecyclerView(binding.eventsRecyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new EventAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLocationsChanged(List<Location> locations) {
        Log.d(TAG, "locations loaded size: " + locations.size());
        //TODO Add new adapter to recycler view...
    }
}
