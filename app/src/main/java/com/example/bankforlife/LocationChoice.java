package com.example.bankforlife;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LocationChoice extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private Switch mSwitch;
    private Spinner mSpinner1, mSpinner2;
    private SeekBar mSeekBar;
    private TextView mPopularTitle, mDropdownTitle, mDistanceTitle, mDistance;
    private Chip mChip;
    private ChipGroup mPopularCity;
    private LinearLayout mDistanceLayout, mDropdownLayout, mMapLayout;
    private boolean status, choosePopular;
    private String cityName, location, result;     //cityName for Popular City, location for dropdown list
    private int districtPos = 0, cityPos = 0;
    private int flag = 0;

    ArrayList<String> areaList = new ArrayList<>();
    HashMap<Integer, ArrayList<String>> cityList = new LinkedHashMap<>();

    private SupportMapFragment mapFragment;
    private LocationManager mLocationManager;
    private GoogleMap googleMap;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private Marker locationMarker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationchoice);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initData();

        mSwitch = findViewById(R.id.switch1);
        mSeekBar = findViewById(R.id.seekBar);

        mPopularTitle = findViewById(R.id.popularTitle);
        mPopularCity = findViewById(R.id.popularCity);
        mDropdownTitle = findViewById(R.id.location);
        mDropdownLayout = findViewById(R.id.dropdownLayout);

        mDistanceTitle = findViewById(R.id.range);
        mDistance = findViewById(R.id.distance);
        mDistanceLayout = findViewById(R.id.seekBarLayout);
        mMapLayout = findViewById(R.id.showMap);

        mSpinner1 = findViewById(R.id.spinner1);          //Dropdown list 1
        mSpinner2 = findViewById(R.id.spinner2);          //Dropdown list 2

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        ---------------------------------------------------------------------------------------------------------
        SharedPreferences pref = getSharedPreferences("locationData", MODE_PRIVATE);
        status = pref.getBoolean("status", false);
        result = pref.getString("result", "");
        choosePopular = pref.getBoolean("isPopular", true);
//        set = pref.getStringSet("chosenList", new HashSet<String>());
        districtPos = pref.getInt("districtPos", 0);
        cityPos = pref.getInt("cityPos", 0);

        System.out.println("Status got from main: " + status);
        System.out.println("Previous Result: " + result);
        System.out.println("isPopular: " + choosePopular);
        System.out.println("Previous districtPos: " + districtPos);

        // Preparation for last view from user
        if (status) {
            mSwitch.setChecked(true);
            mPopularTitle.setVisibility(View.GONE);
            mPopularCity.setVisibility(View.GONE);
            mDropdownTitle.setVisibility(View.GONE);
            mDropdownLayout.setVisibility(View.GONE);

            mDistanceLayout.setVisibility(View.VISIBLE);
            mDistance.setVisibility(View.VISIBLE);
            mDistanceTitle.setVisibility(View.VISIBLE);
            mMapLayout.setVisibility(View.VISIBLE);

            mSeekBar.setProgress(Integer.parseInt(result));
            mDistance.setText(result + " m");

        } else {
            mSwitch.setChecked(false);
            mPopularTitle.setVisibility(View.VISIBLE);
            mPopularCity.setVisibility(View.VISIBLE);
            mDropdownTitle.setVisibility(View.VISIBLE);
            mDropdownLayout.setVisibility(View.VISIBLE);

            if (choosePopular) {                // User selected Popular city
                switch (result) {
                    case "Taipei":
                        mChip = findViewById(R.id.taipei);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Keelung":
                        mChip = findViewById(R.id.keelung);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Yilan":
                        mChip = findViewById(R.id.yilan);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Taoyuan":
                        mChip = findViewById(R.id.taoyuan);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Taichung":
                        mChip = findViewById(R.id.taichung);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Hsinchu":
                        mChip = findViewById(R.id.hsinchu);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Changhua":
                        mChip = findViewById(R.id.changhua);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Chiayi":
                        mChip = findViewById(R.id.chiayi);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                    case "Kaohsiung":
                        mChip = findViewById(R.id.kaohsiung);
                        mChip.setChecked(true);
                        mChip.setChipStrokeColor(getResources().getColorStateList(R.color.chip_location_stroke_color));
                        System.out.println("Last Chosen Popular City: " +mChip.getText());
                        break;
                }
            }
        }
//        ---------------------------------------------------------------------------------------------------------

        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!status) {
                    status = true;
                } else {
                    status = false;
                }
                System.out.println("Status: " + status);
                if (status) {                  //gps on , status == true
                    result = "0";
                    mPopularTitle.setVisibility(View.GONE);
                    mPopularCity.setVisibility(View.GONE);
                    mDropdownTitle.setVisibility(View.GONE);
                    mDropdownLayout.setVisibility(View.GONE);

                    mDistanceLayout.setVisibility(View.VISIBLE);
                    mDistance.setVisibility(View.VISIBLE);
                    mDistanceTitle.setVisibility(View.VISIBLE);
                    mMapLayout.setVisibility(View.VISIBLE);

                    mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(LocationChoice.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationChoice.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // here to request the missing permissions, and then overriding
                        ActivityCompat.requestPermissions(LocationChoice.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
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
                        Toast.makeText(LocationChoice.this, "Longtitude: " + location.getLongitude() + " Latitude: " + location.getLatitude(), Toast.LENGTH_SHORT).show();
                        System.out.println("Longtitude: " + location.getLongitude() + "\nLatitude: " + location.getLatitude());
                    } else {
                        System.out.println("Locating.....");
                    }

                } else {                        // gps off... Status == false
                    if (choosePopular) {
                        result = cityName;
                    } else {
                        if(cityPos == 0 && districtPos == 0){               // if user didnt choose any location <after switching off the gps>, set "location" as default...
                            result = "location";
                        }else{
                            result = location;
                        }
                    }

                    System.out.println("Current result ===========>" + result);
                    mSwitch.setChecked(false);
                    mPopularTitle.setVisibility(View.VISIBLE);
                    mPopularCity.setVisibility(View.VISIBLE );
                    mDropdownTitle.setVisibility(View.VISIBLE);
                    mDropdownLayout.setVisibility(View.VISIBLE);

                    mDistanceLayout.setVisibility(View.GONE);
                    mDistance.setVisibility(View.GONE);
                    mDistanceTitle.setVisibility(View.GONE);
                    mMapLayout.setVisibility(View.GONE);
                }
            }
        });


        mPopularCity.setOnCheckedChangeListener(mPopularCityListener);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(LocationChoice.this, android.R.layout.simple_dropdown_item_1line, areaList);
        System.out.println("READY districtPos: " + districtPos);
        mSpinner1.setAdapter(adapter1);
        mSpinner1.setSelection(districtPos);
        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position) {
                    case 0:
                        Toast.makeText(LocationChoice.this, "Default", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(LocationChoice.this, "北部", Toast.LENGTH_SHORT).show();
                        choosePopular = false;
                        break;
                    case 2:
                        Toast.makeText(LocationChoice.this, "中部", Toast.LENGTH_SHORT).show();
                        choosePopular = false;
                        break;
                    case 3:
                        Toast.makeText(LocationChoice.this, "南部", Toast.LENGTH_SHORT).show();
                        choosePopular = false;
                        break;
                    case 4:
                        Toast.makeText(LocationChoice.this, "東部", Toast.LENGTH_SHORT).show();
                        choosePopular = false;
                        break;
                }

                if (choosePopular) {                   // User chose Popular city ...
                    System.out.println("Resetting spinner 1 to default ....");
                } else {
                    System.out.println("User Ready to manually choose area..");
//                    checkedChipid = mPopularCity.getCheckedChipId();
                    mPopularCity.setOnCheckedChangeListener(null);
                    mPopularCity.clearCheck();
                    mPopularCity.setOnCheckedChangeListener(mPopularCityListener);
                }

                ArrayList<String> city = cityList.get(position);
                districtPos = position;
                ArrayAdapter<String> adapter2 = new ArrayAdapter<>(LocationChoice.this, android.R.layout.simple_dropdown_item_1line, city);
                mSpinner2.setAdapter(adapter2);
                mSpinner2.setSelection(cityPos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (choosePopular) {
                    System.out.println("Resetting Spinner 2 to default...");
                    System.out.println("Current Result : " + result);
                    if(result==null){
                        result = "location";
                    }
                } else {
                    location = cityList.get(districtPos).get(position);
                    cityPos = position;
                    System.out.println("User chose "+location + " as city...");
                    if(cityPos == 0 && districtPos == 0){
                        result = "location";
                    }else{
                        result = location;
                    }
                    System.out.println("Result from spinner two : " + result);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(LocationChoice.this, "Please select one type!", Toast.LENGTH_SHORT).show();
            }
        });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDistance.setText(progress + " m");
                System.out.println("Chosen distance: " + progress);
                result = progress + "";
                System.out.println("Result got from seekbar: " + result);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void initData() {

        // Area dropdown list
        areaList.add(0, "地區");
        areaList.add(1, "北部");
        areaList.add(2, "中部");
        areaList.add(3, "南部");
        areaList.add(4, "東部");

        // City dropdown list
        ArrayList<String> arr0 = new ArrayList<>();
        arr0.add(0, "縣市");
        cityList.put(0, arr0);
        arr0 = new ArrayList<>();
        arr0.add(0, "台北市");
        arr0.add(1, "新北市");
        arr0.add(2, "基隆市");
        arr0.add(3, "桃園市");
        arr0.add(4, "宜蘭縣");
        arr0.add(5, "新竹縣");
        cityList.put(1, arr0);
        arr0 = new ArrayList<>();
        arr0.add(0, "台中市");
        arr0.add(1, "彰化市");
        arr0.add(2, "苗栗縣");
        arr0.add(3, "南投縣");
        arr0.add(4, "雲林縣");
        cityList.put(2, arr0);
        arr0 = new ArrayList<>();
        arr0.add(0, "嘉義縣");
        arr0.add(1, "嘉義市");
        arr0.add(2, "台南市");
        arr0.add(3, "高雄市");
        arr0.add(4, "屏東縣");
        cityList.put(3, arr0);
        arr0 = new ArrayList<>();
        arr0.add(0, "花蓮縣");
        arr0.add(1, "台東縣");
        cityList.put(4, arr0);
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

//        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                System.out.println("Marker Clicked !!!!!!!!!");
//                Toast.makeText(LocationChoice.this, "Marker Clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                System.out.println("Map Clicked.......");
                MarkerOptions marker = new MarkerOptions().position(latLng);
                Toast.makeText(LocationChoice.this, latLng.toString(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(LocationChoice.this, "Clickinggggg", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(locationMarker)) {
            System.out.println("Marker Clicked !!!!!!!!!");
            Toast.makeText(LocationChoice.this, "Marker Clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    // Back //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SharedPreferences pref = getSharedPreferences("locationData", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("status", status);
                editor.putString("result", result);
                editor.putBoolean("isPopular", choosePopular);
                editor.putInt("districtPos", districtPos);
                editor.putInt("cityPos", cityPos);
//                set = new HashSet<>(tmp);
//                editor.putStringSet("chosenList", set);
//                System.out.println("chosen set: " + set);
                editor.commit();

                System.out.println("Sending Result :" + result);
                System.out.println("Sending chosen District :" + districtPos);
                System.out.println("Sending chosen city :" + cityPos);

                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ChipGroup.OnCheckedChangeListener mPopularCityListener = new ChipGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(ChipGroup chipGroup, int id) {
            switch (id) {
                case R.id.taipei:
                    cityName = "Taipei";
                    flag = -1;
                    break;
                case R.id.keelung:
                    cityName = "Keelung";
                    flag = -1;
                    break;
                case R.id.yilan:
                    cityName = "Yilan";
                    flag = -1;
                    break;
                case R.id.taoyuan:
                    cityName = "Taoyuan";
                    flag = -1;
                    break;
                case R.id.taichung:
                    cityName = "Taichung";
                    flag = -1;
                    break;
                case R.id.hsinchu:
                    cityName = "Hsinchu";
                    flag = -1;
                    break;
                case R.id.changhua:
                    cityName = "Changhua";
                    flag = -1;
                    break;
                case R.id.chiayi:
                    cityName = "Chiayi";
                    flag = -1;
                    break;
                case R.id.kaohsiung:
                    cityName = "Kaohsiung";
                    flag = -1;
                    break;
            }
            if(flag != -1){
                System.out.println("User didnt choose any Popular city...");
                cityName = "location";
                choosePopular = false;
            }else{
                choosePopular = true;
            }
            result = cityName;
            districtPos = 0;
            cityPos = 0;
            mSpinner1.setSelection(districtPos);
            mSpinner2.setSelection(cityPos);
            System.out.println("Result after choosing popular city : " + result);
            System.out.println("isPopular: " + choosePopular + "<from popular city>");
        }
    };
}
