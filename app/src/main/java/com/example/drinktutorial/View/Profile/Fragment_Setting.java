package com.example.drinktutorial.View.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drinktutorial.R;
import com.example.drinktutorial.View.Login;
import com.example.drinktutorial.View.UserFragment;


public class Fragment_Setting extends Fragment {
    private ImageView backSetting;
    private TextView logout;

    public Fragment_Setting() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Fragment_Setting newInstance() {
        return new Fragment_Setting();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank__setting, container, false);
        logout = view.findViewById(R.id.logout);
        backSetting = view.findViewById(R.id.backSetting);
        addEvents();
        return view;
    }

    private void addEvents() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // Redirect to the login screen
                Intent intent = new Intent(getActivity(), Login.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

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