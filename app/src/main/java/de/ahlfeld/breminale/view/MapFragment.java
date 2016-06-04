package de.ahlfeld.breminale.view;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.MapView;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.androidmapsextensions.OnMapReadyCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.databinding.FragmentBreminaleMapBinding;
import de.ahlfeld.breminale.utils.DPtoPXUtils;
import de.ahlfeld.breminale.viewmodel.MapViewModel;



/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, MapViewModel.DataListener, MapViewModel.NavigateListener {


    private static final String TAG = MapFragment.class.getSimpleName();
    private FragmentBreminaleMapBinding binding;
    private MapViewModel viewModel;

    private MapView mMapView;
    private GoogleMap mMap;

    private List<Location> locations;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_breminale_map, container, false);
        viewModel = new MapViewModel(getContext(), this, this);

        setHasOptionsMenu(true);

        binding.setViewModel(viewModel);
        locations = new ArrayList<>();
        mMapView = binding.mapView;
        mMapView.onCreate(savedInstanceState);
        mMapView.getExtendedMapAsync(this);
        return binding.getRoot();
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
        viewModel.destroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onLocationsChanged(List<Location> locations) {
        this.locations = locations;
        drawMarkers();
    }

    private void drawMarkers() {
        if (mMap != null && locations != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Location location : locations) {
                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()));
                markerOptions.draggable(false);
                Marker marker = mMap.addMarker(markerOptions);
                marker.setData(location);
                builder.include(marker.getPosition());
                loadMarkerIcon(marker, location);
            }
            LatLngBounds bounds = builder.build();
            int padding = 0;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,padding);
            mMap.animateCamera(cu);
        } else {
            Log.e(TAG, "Map is null or locations is null");
        }
    }

    private void loadMarkerIcon(@NonNull final Marker marker, @NonNull final Location location) {
        Glide.with(this).load(location.getMediumImageUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(resource);
                marker.setIcon(icon);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "map is ready");
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng position) {
                if(viewModel != null) {
                    viewModel.onMapClick();
                }
                if(mMapView != null) {
                    mMapView.setPadding(0,0,0,0);
                }
            }
        });
        mMap.setOnMarkerClickListener(marker -> {
            if(viewModel != null) {
                viewModel.onMarkerClick(marker.getData());
            }
            if(mMapView != null) {
                mMapView.setPadding(0,0,0, (int) DPtoPXUtils.convertDpToPixel(60F,getContext()));
            }
            return true;
        });
        drawMarkers();
    }

    @Override
    public void navigateTo(@NonNull Location location) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+location.getLatitude()+","+location.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(getContext(),R.string.no_google_maps,Toast.LENGTH_SHORT).show();
        }
    }
}
