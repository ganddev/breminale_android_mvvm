package de.ahlfeld.breminale.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.ActivityMainBinding;
import de.ahlfeld.breminale.models.Location;
import de.ahlfeld.breminale.viewmodel.MainViewModel;

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
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewModel.destroy();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        //recyclerView.setAdapter(new ExploreAdapter());
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLocationsChanged(List<Location> locations) {
        Log.d(TAG, "locations loaded size: " + locations.size());
        //TODO Add new adapter to recycler view...
    }
}
