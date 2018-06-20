package com.example.liranyehudar.emojimemorygame;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter{

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_marker_detail, null);

        TextView name = view.findViewById(R.id.name);
        TextView result = view.findViewById(R.id.result);
        TextView address = view.findViewById(R.id.address);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        name.setText(infoWindowData.getName());
        result.setText(infoWindowData.getResult());
        address.setText(infoWindowData.getAddress());

        return view;
    }
}
