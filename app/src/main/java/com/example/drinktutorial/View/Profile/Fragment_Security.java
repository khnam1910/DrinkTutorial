package com.example.drinktutorial.View.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.drinktutorial.R;
import com.example.drinktutorial.View.UserFragment;

public class Fragment_Security extends Fragment {

    private ImageView backSetting;

    public Fragment_Security() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__security, container, false);
        backSetting = view.findViewById(R.id.backSetting);
        addEvents();
        return view;
    }

    private void addEvents() {
        backSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment userFragment = new UserFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLoad, userFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}