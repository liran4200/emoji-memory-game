package com.example.liranyehudar.emojimemorygame;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements UpdateData {

    private SectionPageAdapter mSectionsPageAdapter;
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    private ViewPager mViewPager;
    private double latitude;
    private double longitude;

    private Player player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Intent intent = getIntent();
            player = (Player) intent.getSerializableExtra("Player");

            final TextView details = findViewById(R.id.text_view_details);
            String detailsPlayer = player.getName() + ", " + player.getAge();
            details.setText(detailsPlayer);

            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.v("Location Changed", location.getLatitude() + " and " + location.getLongitude());
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        mLocationManager.removeUpdates(this);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { }
                @Override
                public void onProviderEnabled(String provider) { }
                @Override
                public void onProviderDisabled(String provider) { }
            };


            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else {
                Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                // As the time is measured in milliseconds, multiplying by 1000 gives you seconds, multiplying again by 60 gives you 1 minute, and multiplying by 2 again gives you the total time of 2 minutes.
                // The if statement therefore checks if the location was received more than 2 minutes ago
                if (location != null && location.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
                    // Do something with the recent location fix
                    //  otherwise wait for the update below
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } else {
                    mLocationManager.requestLocationUpdates("gps", 0, 0, mLocationListener);
                }
            }//checkpremission


            mViewPager = (ViewPager) findViewById(R.id.container);
            setupViewAdapter(mViewPager);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        }
        catch (Exception e){
            Log.e("boom",e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        data.putExtra("latitude",latitude);
        data.putExtra("longitude",longitude);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void setupViewAdapter(ViewPager viewPager) {
        FragmentGameMenu fragInfo = new FragmentGameMenu();
        FragmentMapRecords fragMap = new FragmentMapRecords();
        mSectionsPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mSectionsPageAdapter.addFragment(fragInfo,"Game Menu");
        mSectionsPageAdapter.addFragment(new FragmentRecords(),"Table Of Records");
        mSectionsPageAdapter.addFragment(fragMap,"Map Records");

        Bundle bundleMenu = new Bundle();
        bundleMenu.putSerializable("Player",player);
        fragInfo.setArguments(bundleMenu);

        viewPager.setAdapter(mSectionsPageAdapter);


    }

    @Override
    public void onUpdateData() {
        mSectionsPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                mLocationManager.requestLocationUpdates("gps", 0,0,mLocationListener);
            }
        }
    }
}
