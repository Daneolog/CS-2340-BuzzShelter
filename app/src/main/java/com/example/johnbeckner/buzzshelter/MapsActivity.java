package com.example.johnbeckner.buzzshelter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double currentLat = 33.774737;
    private double currentLng = -84.397406;
    private final float zoomLevel = 10.0f;
    private Marker usrLoc;
    private final LocationListener ll = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            currentLat = location.getLatitude();
            currentLng = location.getLongitude();
            LatLng newLoc = new LatLng(currentLat, currentLng);
            usrLoc.setPosition(newLoc);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Iterable<Shelter> shelterList;

        // Add a marker in Sydney and move the camera
        LatLng atlanta = new LatLng(currentLat, currentLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));
        LatLng userLatLng = new LatLng(currentLat, currentLng);
        MarkerOptions usrOpts = new MarkerOptions();
        usrOpts.position(userLatLng);
        usrOpts.title("Your location");
        usrOpts.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        usrLoc = mMap.addMarker(usrOpts);

        if (!ShelterList.getFilteredList().isEmpty()) {
            shelterList = ShelterList.getFilteredList();
        } else {
            shelterList = ShelterList.getShelters();
        }

        for (Shelter s : shelterList) {
            LatLng loc = new LatLng(s.getLatitude(), s.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(s.getShelterName())
                    .snippet(s.getRestrictions()));
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, ll);
            mMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLng)));
        }
    }
}
