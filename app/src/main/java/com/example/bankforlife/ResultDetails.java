package com.example.bankforlife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ResultDetails extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {


    private String Type , title;
    private TextView mTitle;
    private SupportMapFragment mapFragment;
    //    private MapView mapView;
    private LocationManager mLocationManager;
    private GoogleMap googleMap;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private Marker locationMarker;


    public static class locationResult {                  // Create searchResult object for each row ....
        private double latitude;
        private double longitude;

        public locationResult(double lat, double lng) {
            this.latitude = lat;
            this.longitude = lng;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultinfo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        Type = intent.getStringExtra("Type");
        mTitle = findViewById(R.id.info_Title);
        mTitle.setText(title);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.storeMap);
        mapFragment.getMapAsync(this);

//        mapView = findViewById(R.id.storeMap);
//        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        ArrayList<locationResult> locationArray = new ArrayList<>();
        locationArray.add(0, new locationResult(25.036515, 121.551714));
        locationArray.add(1, new locationResult(25.041858, 121.547723));
        locationArray.add(2, new locationResult(25.051734, 121.550685));
        locationArray.add(3, new locationResult(25.079921, 121.590121));
        locationArray.add(4, new locationResult(25.083263, 121.568523));
        locationArray.add(5, new locationResult(25.051807, 121.572135));
        locationArray.add(6, new locationResult(25.052090, 121.564203));
        locationArray.add(7, new locationResult(24.961576, 121.207289));
        locationArray.add(8, new locationResult(24.871891, 121.209023));


        Collections.shuffle(locationArray);
        Random random = new Random();
        int rmp = random.nextInt(9);
        Log.d("tmp", "rmp : " + rmp);
        LatLng latLng = new LatLng(locationArray.get(rmp).getLatitude(), locationArray.get(rmp).getLongitude());   // generate random location...
        Log.d("tmp", "Longitude : " + locationArray.get(rmp).getLongitude() + "\t\tLatitude : " + locationArray.get(rmp).getLatitude());

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(ResultDetails.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ResultDetails.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            ActivityCompat.requestPermissions(ResultDetails.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        String gpsProvider = LocationManager.GPS_PROVIDER;
        mLocationManager.requestLocationUpdates(gpsProvider, 1000, 0, locationListener);
        Location location = mLocationManager.getLastKnownLocation(gpsProvider);
        if (location != null) {
            Toast.makeText(ResultDetails.this, "Longtitude: " + location.getLongitude() + " Latitude: " + location.getLatitude(), Toast.LENGTH_SHORT).show();
            System.out.println("Longtitude: " + location.getLongitude() + "\nLatitude: " + location.getLatitude());
        } else {
            System.out.println("Locating.....");
        }


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(true);
        MarkerOptions options = new MarkerOptions().position(latLng).title(Type).snippet(title);
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        locationMarker = googleMap.addMarker(options);
        locationMarker.showInfoWindow();

//        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            }
        });

//        googleMap.setOnMarkerClickListener(this);
//        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                System.out.println("Marker Clicked !!!!!!!!!");
//                Toast.makeText(ResultDetails.this, "Marker Clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                System.out.println("Map Clicked.......");
                MarkerOptions marker = new MarkerOptions().position(latLng);
                Toast.makeText(ResultDetails.this, latLng.toString(), Toast.LENGTH_LONG).show();
                googleMap.addMarker(marker);
            }
        });

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarker.getPosition(), 18));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(locationMarker.getPosition()), 250, null);

    }


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
//            mTextView = findViewById(R.id.text_location);
//            mTextView.setText("Longtitude: " + location.getLongitude() + ", Latitude: " + location.getLatitude());
//            System.out.println("Longtitude: " + location.getLongitude() + "\nLatitude: " + location.getLatitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d("Latitude", "status");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d("Latitude", "enable");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d("Latitude", "disable");
        }
    };

    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(ResultDetails.this, "Clickinggggg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(locationMarker)) {
            System.out.println("Marker Clicked !!!!!!!!!");
            Toast.makeText(ResultDetails.this, "Marker Clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
