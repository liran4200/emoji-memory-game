package com.example.liranyehudar.emojimemorygame;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FragmentMapRecords extends Fragment {

    private static final String TAG = "FragmentMapRecords";
    private DBHandler db;
    private GoogleMap map;
    private OnMapReadyCallback onMapReadyCallback;

    //AIzaSyD3TVdt5h3wd4lJzgh8V9EdCz0zWihGQ0s
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_records, container, false);
        db = new DBHandler(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().
                findFragmentById(R.id.map);

        onMapReadyCallback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                try {
                    displayResultOnMap();
                    //addAnnotation("liran",55,32.0747872,34.8682687);
                    map.setMyLocationEnabled(true);
                } catch (SecurityException e) {// already checked

                }
            }
        };

        mapFragment.getMapAsync(onMapReadyCallback);
        return view;
    }

    public void addAnnotation(String name,int result,double latitude, double longitude) {
        LatLng location = new LatLng(latitude,longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);

        InfoWindowData info = new InfoWindowData();
        info.setName("Name: "+ name);
        info.setResult("Result: "+result);
        info.setAddress(getAddress(location));

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
        map.setInfoWindowAdapter(customInfoWindow);
        Marker m = map.addMarker(markerOptions);
        m.setTag(info);
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    public void displayResultOnMap() {
        Cursor cursor = db.getAllData();
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(db.KEY_NAME));
            int result =  Integer.parseInt(cursor.getString(cursor.getColumnIndex(db.KEY_RESULT)));
            double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.KEY_LATITUDE)));
            double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(db.KEY_LONGITUDE)));
            addAnnotation(name, result, latitude, longitude);

        }
        cursor.close();
    }

    public String getAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);

        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses !=null) {
            String city = addresses.get(0).getLocality();
            String street = addresses.get(0).getThoroughfare();
            String streetNumber = addresses.get(0).getSubThoroughfare();
            return street+" "+streetNumber+", "+city;
        }
        return "";
    }

}
