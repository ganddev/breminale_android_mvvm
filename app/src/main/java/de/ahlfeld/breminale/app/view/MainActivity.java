package de.ahlfeld.breminale.app.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.databinding.ActivityMainBinding;
import de.ahlfeld.breminale.app.services.GcmRegistrationService;
import de.ahlfeld.breminale.app.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity implements MainViewModel.Navigation, FavoritsListFragment.OnProgramClickListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    private BottomBar bottomBar;
    private Tracker tracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        binding.setViewModel(mainViewModel);
        setupBottomBar(savedInstanceState);

        // Obtain the shared Tracker instance.
        BreminaleApplication application = (BreminaleApplication) getApplication();
        tracker = application.getDefaultTracker();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, GcmRegistrationService.class);
            startService(intent);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        tracker.setScreenName("MainActivity");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mainViewModel != null) {
            mainViewModel.destroy();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        bottomBar.onSaveInstanceState(outState);
    }


    public void setupBottomBar(Bundle savedInstanceState) {
        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.noTopOffset();
        // Disable the left bar on tablets and behave exactly the same on mobile and tablets instead.
        bottomBar.noTabletGoodness();
        bottomBar.noNavBarGoodness();
        bottomBar.setTextAppearance(R.style.MyBottomBarTextExperience);
        bottomBar.setTypeFace("fonts/Roboto-Regular.ttf");
        bottomBar.useFixedMode();
        bottomBar.setItemsFromMenu(R.menu.menu_main, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {
                switch (menuItemId) {
                    case R.id.program_item:
                        mainViewModel.showProgram();
                        return;
                    case R.id.map_item:
                        mainViewModel.showMap();
                        return;
                    case R.id.favorite_item:
                        mainViewModel.showFavorits();
                        return;
                    case R.id.brefunk_item:
                        mainViewModel.showBrefunk();
                        return;
                    case R.id.more_item:
                        mainViewModel.showMore();
                        return;
                }

            }

            @Override
            public void onMenuTabReSelected(int menuItemId) {

            }
        });
    }

    @Override
    public void showFavorits() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, FavoritsListFragment.newInstance());
        ft.commit();
    }

    @Override
    public void showBrefunk(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, BrefunkFragment.newInstance());
        ft.commit();
    }

    @Override
    public void showMore() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, MoreFragment.newInstance());
        ft.commit();
    }

    @Override
    public void showProgram() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, ProgramFragment.newInstance());
        ft.commit();
    }

    @Override
    public void showMap() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment_container, MapFragment.newInstance());
        ft.commit();
    }

    @Override
    public void onProgramClick() {
        if(bottomBar != null) {
            bottomBar.selectTabAtPosition(0, true);
        }
    }
}
