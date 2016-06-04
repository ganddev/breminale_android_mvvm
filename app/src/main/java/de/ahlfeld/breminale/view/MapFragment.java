package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.core.domain.domain.Location;
import de.ahlfeld.breminale.databinding.FragmentBreminaleMapBinding;
import de.ahlfeld.breminale.viewmodel.MapViewModel;




/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, MapViewModel.DataListener {


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
        viewModel = new MapViewModel(getContext(),this);
        binding.setViewModel(viewModel);
        locations = new ArrayList<>();
        mMapView = binding.mapView;
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
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
    public void onMapReady(GoogleMap googleMap) {
        Log.i(TAG, "map is ready");
        mMap = googleMap;
        drawMarkers();
    }

    @Override
    public void onLocationsChanged(List<Location> locations) {
        Log.d(TAG, "Loaded " + locations.size() + " from realm");
        this.locations = locations;
        drawMarkers();
    }

    private void drawMarkers() {
        Log.d(TAG, "draw markers");
        if(mMap != null && locations != null) {
            for (Location location : locations) {
                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        } else {
            Log.e(TAG, "Map is null or locations is null");
        }
    }
}
