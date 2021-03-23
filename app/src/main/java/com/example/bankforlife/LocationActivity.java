package com.example.bankforlife;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private SupportMapFragment mapFragment;
    private LocationManager mLocationManager;
    private GoogleMap googleMap;
    private boolean change = false;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private Marker locationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationchoice);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
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
            Toast.makeText(LocationActivity.this, "Longtitude: " + location.getLongitude() + " Latitude: " + location.getLatitude(), Toast.LENGTH_SHORT).show();
            System.out.println("Longtitude: " + location.getLongitude() + "\nLatitude: " + location.getLatitude());
        } else {
            System.out.println("Locating.....");
        }

    }


    @Override
    public void onMapReady(GoogleMap map) {

        googleMap = map;
        String title = "Bank";
        LatLng latLng = new LatLng(25.046591, 121.539248);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        MarkerOptions options = new MarkerOptions().position(latLng).title(title).snippet("彰化銀行");
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
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                change = true;
                System.out.println("Marker Clicked !!!!!!!!!");
                Toast.makeText(LocationActivity.this, "Marker Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                change = false;
                googleMap.clear();
                System.out.println("Map Clicked.......");
                MarkerOptions marker = new MarkerOptions().position(latLng);
                Toast.makeText(LocationActivity.this, latLng.toString(), Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(LocationActivity.this, "Clickinggggg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(locationMarker)) {
            change = true;
            System.out.println("Marker Clicked !!!!!!!!!");
            Toast.makeText(LocationActivity.this, "Marker Clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}



//                if (mSpinner1.getSelectedItemPosition() == 0) {
//                    list2.removeAll(list2);
//                    list2.add(0, "縣市");
//                } else if (mSpinner1.getSelectedItemPosition() == 1) {
//                    list2.removeAll(list2);
//                    list2.add(0, "台北市");
//                    list2.add(1, "新北市");
//                    list2.add(2, "基隆市");
//                    list2.add(3, "桃園市");
//                    list2.add(4, "宜蘭縣");
//                    list2.add(5, "新竹縣");
//                } else if (mSpinner1.getSelectedItemPosition() == 2) {
//                    list2.removeAll(list2);
//                    list2.add(0, "台中市");
//                    list2.add(1, "彰化市");
//                    list2.add(2, "苗栗縣");
//                    list2.add(3, "南投縣");
//                    list2.add(4, "雲林縣");
//                } else if (mSpinner1.getSelectedItemPosition() == 3) {
//                    list2.removeAll(list2);
//                    list2.add(0, "嘉義縣");
//                    list2.add(1, "嘉義市");
//                    list2.add(2, "台南市");
//                    list2.add(3, "高雄市");
//                    list2.add(4, "屏東縣");
//                } else if (mSpinner1.getSelectedItemPosition() == 4) {
//                    list2.removeAll(list2);
//                    list2.add(0, "花蓮縣");
//                    list2.add(1, "台東縣");
//                } else {
//                    list2.add("Location");
//                }
//                tmp = list2;


//                    mSwitch.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            System.out.println("Delayed.......");
//                            Intent intent = new Intent(LocationChoice.this, LocationActivity.class);
//                            startActivity(intent);
//                        }
//                    }, 500);       // Delay for 0.5 sec
// Map setting...


//                intent.putExtra("gps", gps);
//                intent.putExtra("distance", distance);
//                startActivityForResult(intent, 120);  // 120 for location services
//                SharedPreferences pref = getSharedPreferences("lastData",MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putBoolean("status",gps);
//                editor.putString("result",result);
//                editor.putBoolean("isPopular",isPopular);
//                editor.putStringSet("chosenList",set);
//                editor.putInt("district",district);
//                editor.commit();


//                    Chip chip = findViewById(checkedChipid);
//                    for (int i = 0; i < mPopularCity.getChildCount(); i++) {
//                        if (mPopularCity.getChildAt(i).getId() == checkedChipid) {
//                            System.out.println("Canceled City: " + chip.getText());
//                            flag = 0;
//                            chip.setChecked(false);
//                            chip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
//                            System.out.println("Is Checked: " + chip.isChecked());
//                        }
//                    }



//                        currentList.add(tmp);
//                        for (int i = 0; i < currentList.size(); i++) {
//                            if(currentList.get(i)==-1)
//                            {
//                                currentList.remove(i);
//                            }
//                        }



//        else if (requestCode == 120 && resultCode == RESULT_OK) {
//            if (data != null) {
//                gps = data.getBooleanExtra("status", true);
//                System.out.println("Status in main: " + gps);
//                System.out.println("!!!!!!!!!!!!!!!!!!!!");
//                mShow = findViewById(R.id.show);
//
//                if (gps) {
//                    mMapView.setBackground(getDrawable(R.drawable.gpson));
//                    distance = data.getIntExtra("distance", 0);
//                    System.out.println("User Chose Distance :" + distance);
//                    mShow.setText("\t " + distance + " m");
//                    mShow.setTextColor(Color.parseColor("#DC143C"));
//                } else {
//                    mMapView.setBackground(getDrawable(R.drawable.gpsoff));
//                    cityName = data.getStringExtra("cityName");
//                    location = data.getStringExtra("location");
//
//                    if (cityName != null || location != null) {
//                        boolean isPopular = data.getBooleanExtra("choosePopular", true);
//                        if (isPopular) {
//                            mShow.setText(cityName);
//                        } else {
//                            mShow.setText(location);
//                        }
//                    } else {
//                        mShow.setText("Location");
//                    }
//                    mShow.setTextColor(Color.BLACK);
//                }
//            }
//        }

//        ArrayList<Double> sortedRatingList = new ArrayList<>();
//        for (int i = 0; i < mTitleList.size(); i++) {
//            sortedRatingList.add(mRatingList.get(i));
//        }
//        Collections.sort(sortedRatingList);
//        Collections.reverse(sortedRatingList);
//        Log.d("searchResult", "Sorted Rating List: " + sortedRatingList);
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        MyListAdapter adapter = new MyListAdapter(mTitleList, sortedRatingList, mAddressList, inflater);
//        mListView.setAdapter(adapter);