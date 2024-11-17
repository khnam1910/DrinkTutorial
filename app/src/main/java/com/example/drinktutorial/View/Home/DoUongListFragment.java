package com.example.drinktutorial.View.Home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterDoUongs;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class DoUongListFragment extends Fragment {

    CustomAdapterDoUongs customAdapterDoUongs;
    RecyclerView rycDoUongs;
    String idLDU, tenLDU;

    public DoUongListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_uong_list, container, false);
        if(getArguments() != null)
        {
            idLDU = getArguments().getString("LoaiDoUongID");
            tenLDU = getArguments().getString("tenLDU");
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateTitle(tenLDU);
            }

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


                customAdapterDoUongs.setOnItemClickListener(new CustomAdapterDoUongs.OnItemClickListener() {
                    @Override
                    public void onItemClick(DoUong doUong) {
                        Log.d("Test", "onItemClick: "+doUong.getKeyID());

                        Bundle bundle = new Bundle();
                        bundle.putString("idDoUong",doUong.getKeyID());
                        DoUongDetailFragment doUongDetailFragment = new DoUongDetailFragment();
                        doUongDetailFragment.setArguments(bundle);

                        if(getActivity() instanceof MainActivity)
                        {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.loadFragment(doUongDetailFragment);
                        }
                        AppCompatActivity activity = (AppCompatActivity) getActivity();
                        if (activity != null) {
                            Toolbar toolbar = activity.findViewById(R.id.toolbar);
                            activity.setSupportActionBar(toolbar);
                            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                            if (activity.getSupportActionBar() != null) {
                                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            }
                        }
                    }
                });
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


