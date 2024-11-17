package com.example.drinktutorial.View.Home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Controller.NguyenLieuControler;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.Model.NguyenLieu;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;


public class DoUongDetailFragment extends Fragment {


    public DoUongDetailFragment() {
        // Required empty public constructor
    }

    ImageView imgDoUongDT;
    TextView tvTenDoUong, tvMoTa, tvBuocPhaChe;
    String idDU, tenDU;
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_uong_detail, container, false);;
        // Inflate the layout for this fragment
        if(getArguments() != null)
        {
            idDU = getArguments().getString("idDoUong");

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).updateTitle("Chi tiết đồ uống");
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
        tvMoTa =(TextView) view.findViewById(R.id.tvMoTa);
        layout =(LinearLayout) view.findViewById(R.id.layout);
        tvBuocPhaChe=(TextView) view.findViewById(R.id.tvBuocPhaChe);
    }


public void getDoUong(String idDU) {
    DoUongController doUongController = new DoUongController();
    doUongController.getListDU(new DoUongController.DataStatus() {
        @Override
        public void getALlDoUong(ArrayList<DoUong> doUongs) {
            for (DoUong doUong : doUongs) {
                if (doUong.getKeyID().equals(idDU)) {
                    Glide.with(getContext())
                            .load(doUong.getHinhAnh())
                            .into(imgDoUongDT);
                    String buocPhaCheFormatted = doUong.getBuocPhaChe().replace("@", "\n");
                    tvBuocPhaChe.setText(buocPhaCheFormatted);
                    setDisplayNguyenLieuDT(doUong.getNguyenLieu());
                    tvMoTa.setText(doUong.getMoTa());
                    tvTenDoUong.setText(doUong.getName());
                }
            }
        }
    });
}

    public void setDisplayNguyenLieuDT(Map<String, Map<String, Double>> nguyenLieuMap) {
        if (nguyenLieuMap != null && !nguyenLieuMap.isEmpty()) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            for (Map.Entry<String, Map<String, Double>> entry : nguyenLieuMap.entrySet()) {
                String idNL = entry.getKey();
                Double tyle = entry.getValue().values().iterator().next(); // Lấy tỷ lệ
                String formattedTyle = decimalFormat.format(tyle);
                LinearLayout horizontalLayout = new LinearLayout(getActivity());
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                getNLInfo(idNL, formattedTyle, horizontalLayout);

                layout.addView(horizontalLayout);
            }
        }
    }
    public void getNLInfo(String idNL, String tyle, LinearLayout horizontalLayout) {
        NguyenLieuControler nguyenLieuControler = new NguyenLieuControler();
        nguyenLieuControler.getListNL(new NguyenLieuControler.DataStatus() {
            @Override
            public void getAllNL(ArrayList<NguyenLieu> nguyenLieus) {
                for (NguyenLieu nguyenLieu : nguyenLieus) {
                    if (nguyenLieu.getKeyID().equals(idNL)) {

                        TextView nameView = new TextView(getActivity());
                        nameView.setText(nguyenLieu.getTen());
                        nameView.setTextSize(18);
                        nameView.setTextColor(Color.BLACK);
                        nameView.setPadding(10, 10, 10, 10);

                        LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                        nameView.setLayoutParams(nameParams);



                        TextView quantityView = new TextView(getActivity());
                        quantityView.setText(" " + tyle + " " + nguyenLieu.getDonViDo());
                        quantityView.setTextSize(18);
                        quantityView.setTextColor(Color.BLUE);
                        quantityView.setPadding(10, 10, 10, 10);

                        LinearLayout.LayoutParams quantityParams = new LinearLayout.LayoutParams(
                                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                        quantityParams.gravity = Gravity.END; // Căn bên phải
                        quantityView.setLayoutParams(quantityParams);

                        horizontalLayout.addView(nameView);
                        horizontalLayout.addView(quantityView);
                        break;
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