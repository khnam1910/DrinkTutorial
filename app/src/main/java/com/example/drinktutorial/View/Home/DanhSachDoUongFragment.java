package com.example.drinktutorial.View.Home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterDoUongs;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.example.drinktutorial.View.Profile.Fragment_History;

import java.util.ArrayList;


public class DanhSachDoUongFragment extends Fragment {


    RecyclerView rycListDoUong;
    CustomAdapterDoUongs adapterDoUongs;
    public DanhSachDoUongFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_do_uong, container, false);
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateTitle("Danh sách đồ uống");
        }
        addControls(view);
        loadDoUong();
        // Inflate the layout for this fragment
        return view;
    }

    public void addControls(View view)
    {
        rycListDoUong= view.findViewById(R.id.rycListDoUongs);
    }

    public void loadDoUong()
    {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus() {
            @Override
            public void getALlDoUong(ArrayList<DoUong> doUongs) {
                rycListDoUong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                adapterDoUongs = new CustomAdapterDoUongs(doUongs);
                rycListDoUong.setAdapter(adapterDoUongs);

                adapterDoUongs.setOnItemClickListener(new CustomAdapterDoUongs.OnItemClickListener() {
                    @Override
                    public void onItemClick(DoUong doUong) {
//                        Log.d("Test", "onItemClick: "+doUong.getKeyID());

                        Bundle bundle = new Bundle();
                        bundle.putString("idDoUong",doUong.getKeyID());
                        Fragment_History fragmentHistory = new Fragment_History();
                        fragmentHistory.setArguments(bundle);
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

            @Override
            public void getFilteredDoUong(ArrayList<DoUong> doUongs) {

            }
        });
    }
}