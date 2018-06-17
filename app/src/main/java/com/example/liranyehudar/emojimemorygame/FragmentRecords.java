package com.example.liranyehudar.emojimemorygame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRecords extends Fragment {

    private static final String TAG = "FragmentRecords";
    DBHandler db ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records,container,false);
        db = new DBHandler(getActivity());
        
        return view;
    }
}
