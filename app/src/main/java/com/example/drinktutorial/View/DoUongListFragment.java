package com.example.drinktutorial.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drinktutorial.Adapter.CustomAdapterDoUongs;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class DoUongListFragment extends Fragment {

    CustomAdapterDoUongs customAdapterDoUongs;
    RecyclerView rycDoUongs;
    String idLDU;

    public DoUongListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_uong_list, container, false);
        if(getArguments() != null)
        {
            idLDU = getArguments().getString("LoaiDoUongID");
        }
        addControls(view);
        loadDUTheoLoai(idLDU);
        return view;
    }
    public void addControls(View view)
    {
        rycDoUongs = (RecyclerView) view.findViewById(R.id.rycListDoUong);
    }

    public void loadDUTheoLoai(String loaiDoUongId) {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus() {
            @Override
            public void getALlDoUong(ArrayList<DoUong> doUongs) {
                ArrayList<DoUong> filteredDoUongs = new ArrayList<>();

                // Lọc đồ uống theo loại dựa trên ID
                for (DoUong doUong : doUongs) {
                    if (doUong.getLoai().equals(loaiDoUongId)) {
                        filteredDoUongs.add(doUong);
                    }
                }

                // Hiển thị danh sách đồ uống đã lọc
                rycDoUongs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                customAdapterDoUongs = new CustomAdapterDoUongs(filteredDoUongs);
                rycDoUongs.setAdapter(customAdapterDoUongs);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);
            bottomNavigationView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }
}


