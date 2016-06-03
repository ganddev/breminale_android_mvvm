package de.ahlfeld.breminale.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.databinding.ActivityEventBinding;
import de.ahlfeld.breminale.viewmodel.EventViewModel;



public class EventActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, OnMapReadyCallback, EventViewModel.DataListener {

    private static final String EXTRA_EVENT = "event";
    private EventViewModel eventViewModel;
    private ActivityEventBinding binding;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.7f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private RelativeLayout mTitleContainer;
    private TextView mTitle;

    private String TAG = EventActivity.class.getSimpleName();
    private MapView mMapView;
    private GoogleMap mMap;
    private Location location;

    public static Intent newIntent(Context context, Event event) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event);

        Event event = getIntent().getParcelableExtra(EXTRA_EVENT);

        eventViewModel = new EventViewModel(this, event, this);

        binding.setViewModel(eventViewModel);

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.appBarLayout.addOnOffsetChangedListener(this);
        setBackButtonColor();

        mTitle = binding.toolbarTitle;
        mTitleContainer = binding.titleContainer;

        setTitle("");

        mMapView = binding.mapImageView;
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        addSoundcloudFragment(event);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
    }

    private void addSoundcloudFragment(Event event) {
        try {
            long soundcloudUserId = Long.parseLong(event.getSoundcloudUserId());
            if(soundcloudUserId > 0 ) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SoundcloudFragment sFm = SoundcloudFragment.newInstance(soundcloudUserId);
                ft.replace(R.id.container_soundcloud, sFm);
                ft.commit();
            }
        }catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private void setBackButtonColor() {
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        drawLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    private void drawLocation() {
        if(mMap != null && location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng));
            CameraUpdate cameraUpdate = CameraUpdateFactory
                    .newLatLngZoom(latLng, 17);
            mMap.moveCamera(cameraUpdate);
        }
    }
}
