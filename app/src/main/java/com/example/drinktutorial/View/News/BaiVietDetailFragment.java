package com.example.drinktutorial.View.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Controller.BaiVietController;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class BaiVietDetailFragment extends Fragment {

    TextView tvTenBV, tvNoiDung;
    String idBaiViet;
    ImageView imgBaiViet;

    public BaiVietDetailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bai_viet_detail, container, false);

        addControls(view);

        if(getArguments() != null)
        {
            idBaiViet = getArguments().getString("BaiVietID");

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateTitle("Chi tiết bài viết");
            }
        }
        getBaiViet(idBaiViet);
        return view;
    }

    public void addControls(View view)
    {
        tvTenBV = view.findViewById(R.id.tvTenBV);
        tvNoiDung = view.findViewById(R.id.tvNoiDung);
        imgBaiViet = view.findViewById(R.id.imgBaiViet);
    }

    public void getBaiViet(String idBaiViet) {
        BaiVietController baiVietController = new BaiVietController();
        baiVietController.getListBV(new BaiVietController.DataStatus() {
            @Override
            public void getAllBaiViet(ArrayList<BaiViet> baiViets) {
                for(BaiViet baiviet: baiViets)
                {
                    if(baiviet.getKeyID().equals(idBaiViet))
                    {
                        tvTenBV.setText(baiviet.getTieuDe());
                        HashMap<String, String> imgUrl = baiviet.getHinhAnh();

                        if (imgUrl != null && !imgUrl.isEmpty()) {
                            // Duyệt qua các entry trong HashMap
                            for (HashMap.Entry<String, String> entry : imgUrl.entrySet()) {
                                String value = entry.getValue();
                                Glide.with(getContext())
                                        .load(value)
                                        .into(imgBaiViet);
                                break;
                            }
                        } else {
                            Log.e("HashMapContent", "HashMap is empty or null.");
                            return;
                        }
                        tvNoiDung.setText(baiviet.getNoiDung());

                    }

                }
            }
        });
    }

}