package com.example.johnbeckner.buzzshelter;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for Google Maps view.
 * @author John Beckner
 * @version 1.0
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final double techGreenLat = 33.774737;
    private final double techGreenLng = -84.397406;
    private final float zoomLevel = 10.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager fragMan = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fragMan.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressWarnings("FeatureEnvy") // Fixing this warning would require us to re-write ShelterList
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Iterable<Shelter> shelterList;

        // Add a marker in Sydney and move the camera
        LatLng atlanta = new LatLng(techGreenLat, techGreenLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));

        List<Shelter> filteredList = ShelterList.getFilteredList();
        if (!filteredList.isEmpty()) {
            shelterList = ShelterList.getFilteredList();
        } else {
            shelterList = ShelterList.getShelters();
        }

        for (Shelter s : shelterList) {
            LatLng loc = new LatLng(s.getLatitude(), s.getLongitude());

            Log.e(s.getShelterName(), s.getLatitude() + " " + s.getLongitude());

            MarkerOptions marOpt = new MarkerOptions();
            marOpt = marOpt.position(loc);
            marOpt = marOpt.title(s.getShelterName());
            mMap.addMarker(marOpt.snippet(s.getRestrictions()));
        }
    }
}
