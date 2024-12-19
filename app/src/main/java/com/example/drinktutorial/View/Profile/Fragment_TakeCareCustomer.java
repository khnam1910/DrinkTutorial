package com.example.drinktutorial.View.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.drinktutorial.R;

public class Fragment_TakeCareCustomer extends Fragment {

    private ImageView backSetting;

    public Fragment_TakeCareCustomer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__take_care_customer, container, false);

        return view;
    }


}