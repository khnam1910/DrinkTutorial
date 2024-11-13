package com.example.drinktutorial.View.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;

import java.util.ArrayList;


public class DoUongDetailFragment extends Fragment {


    public DoUongDetailFragment() {
        // Required empty public constructor
    }

    ImageView imgDoUongDT;
    TextView tvTenDoUong;
    String idDU, tenDU;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_uong_detail, container, false);;
        // Inflate the layout for this fragment
        if(getArguments() != null)
        {
            idDU = getArguments().getString("idDoUong");
            tenDU = getArguments().getString("tenDoUong");
            if(tenDU != null)
            {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).updateTitle(tenDU);
                }
            }
        }
        addControls(view);
        getDoUong(idDU);
        return view;
    }

    public void addControls(View view)
    {
        imgDoUongDT = (ImageView) view.findViewById(R.id.imgDoUongDT);
        tvTenDoUong = (TextView) view.findViewById(R.id.tvTenDoUong);
    }

    public void getDoUong(String idDU)
    {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus() {
            @Override
            public void getALlDoUong(ArrayList<DoUong> doUongs) {
//                ArrayList<DoUong> filteredDoUongs = new ArrayList<>();

                // Lọc đồ uống theo loại dựa trên ID
                for (DoUong doUong : doUongs) {
                    if (doUong.getKeyID().equals(idDU)) {
//                        filteredDoUongs.add(doUong);
                        Log.d("aaaaa", "getALlDoUong: "+doUong.getName());
                        tvTenDoUong.setText(doUong.getName());
                    }
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).restoreTitle();
        }
    }
}