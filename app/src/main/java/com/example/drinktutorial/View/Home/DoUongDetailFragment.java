package com.example.drinktutorial.View.Home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterHotDrink;
import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterLDU;
import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterNguyenLieu;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Controller.LoaiNguyenLieuController;
import com.example.drinktutorial.Controller.NguyenLieuControler;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.Model.LoaiNguyenLieu;
import com.example.drinktutorial.Model.NguyenLieu;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;


public class DoUongDetailFragment extends Fragment {


    public DoUongDetailFragment() {
        // Required empty public constructor
    }

    RecyclerView rycNguyenLieu, rycLDUong;
    ImageView imgDoUongDT;
    ImageView imgYeuthich;
    TextView tvTenDoUong, tvMoTa, tvBuocPhaChe;
    String idDU;
    LinearLayout layout;
    CustomAdapterHotDrink adapterHotDrink;
    CustomAdapterNguyenLieu adapterNguyenLieu;
    boolean isFavorite = false; //Lưu trạng thái yêu thích

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
        imgYeuthich.setOnClickListener(v -> toggleFavorite());
        return view;
    }

    public void addControls(View view)
    {
        imgDoUongDT = (ImageView) view.findViewById(R.id.imgDoUongDT);
        tvTenDoUong = (TextView) view.findViewById(R.id.tvTenDoUong);
        tvMoTa =(TextView) view.findViewById(R.id.tvMoTa);
        layout =(LinearLayout) view.findViewById(R.id.layout);
        tvBuocPhaChe=(TextView) view.findViewById(R.id.tvBuocPhaChe);
        rycNguyenLieu = (RecyclerView) view.findViewById(R.id.rycNguyenLieu);
        rycLDUong = (RecyclerView) view.findViewById(R.id.rycLDUong);
        imgYeuthich = (ImageView) view.findViewById(R.id.imgYeuthich);
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
                    getDUTheoLoai(doUong.getLoai());
                }
            }
        }

        @Override
        public void getFilteredDoUong(ArrayList<DoUong> doUongs) {

        }
    });

}

    public void setDisplayNguyenLieuDT(Map<String, Map<String, Double>> nguyenLieuMap) {
        if (nguyenLieuMap != null && !nguyenLieuMap.isEmpty()) {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            ArrayList<String> listIdNL = new ArrayList<>();
            for (Map.Entry<String, Map<String, Double>> entry : nguyenLieuMap.entrySet()) {
                String idNL = entry.getKey();
                Double tyle = entry.getValue().values().iterator().next();
                listIdNL.add(idNL);
                String formattedTyle = decimalFormat.format(tyle);
                LinearLayout horizontalLayout = new LinearLayout(getActivity());
                horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                getNLInfo(idNL, formattedTyle, horizontalLayout);

                layout.addView(horizontalLayout);
            }
            getImageNL(listIdNL);
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

    public void getImageNL(ArrayList<String> listIdNL) {
        NguyenLieuControler nguyenLieuControler = new NguyenLieuControler();
        nguyenLieuControler.getListNL(new NguyenLieuControler.DataStatus() {
            @Override
            public void getAllNL(ArrayList<NguyenLieu> nguyenLieus) {
                ArrayList<NguyenLieu> filterNguyenLieu = new ArrayList<>();
                for(NguyenLieu nguyenLieu :nguyenLieus)
                {
                    for(String idnl: listIdNL)
                    {
                        if(nguyenLieu.getKeyID().equals(idnl))
                        {
                            filterNguyenLieu.add(nguyenLieu);
                            break;
                        }
                    }

                }
                rycNguyenLieu.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapterNguyenLieu = new CustomAdapterNguyenLieu(filterNguyenLieu);
                rycNguyenLieu.setAdapter(adapterNguyenLieu);
            }
        });
    }

    public void getDUTheoLoai(String id_LDU)
    {
       DoUongController doUongController = new DoUongController();
       doUongController.getListDU(new DoUongController.DataStatus() {
           @Override
           public void getALlDoUong(ArrayList<DoUong> doUongs) {
               ArrayList<DoUong> filterDoUong = new ArrayList<>();
               for(DoUong doUong: doUongs)
               {
                   if(doUong.getLoai().equals(id_LDU))
                       filterDoUong.add(doUong);
               }
               rycLDUong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
               adapterHotDrink = new CustomAdapterHotDrink(filterDoUong);
               rycLDUong.setAdapter(adapterHotDrink);
               addItemClickListenerForDoUong();

               checkIfFavorite(idDU);
           }

           @Override
           public void getFilteredDoUong(ArrayList<DoUong> doUongs) {

           }
       });
    }
    private void addItemClickListenerForDoUong() {
        if (adapterHotDrink != null) {
            adapterHotDrink.setOnItemClickListener(new CustomAdapterHotDrink.OnItemClickListener() {
                @Override
                public void onItemClick(DoUong doUong) {
                    Bundle bundle = new Bundle();
                    bundle.putString("idDoUong", doUong.getKeyID());

                    DoUongDetailFragment doUongDetailFragment = new DoUongDetailFragment();
                    doUongDetailFragment.setArguments(bundle);

                    if (getActivity() instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.loadFragment(doUongDetailFragment);
                    }

                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    if (activity != null) {
                        Toolbar toolbar = activity.findViewById(R.id.toolbar);
                        activity.setSupportActionBar(toolbar);
                        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                }
            });
        } else {
            Log.e("HomeFragment", "adapterHotDrink is null");
        }
    }

    private void toggleFavorite() {
        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(getContext(), "Bạn cần đăng nhập để thực hiện chức năng này!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("NguoiDung").child(userId);

        if (!isFavorite) {
            userRef.child("Yeuthich").child(idDU).setValue(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isFavorite = true;
                    imgYeuthich.setImageResource(R.drawable.heart);
                    Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            userRef.child("Yeuthich").child(idDU).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isFavorite = false;
                    imgYeuthich.setImageResource(R.drawable.heart_3_fill);
                    Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkIfFavorite(String idDU) {
        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        String currentUserId = sharedPreferences.getString("userId", null);

        if (currentUserId == null) {
            Toast.makeText(getContext(), "Không thể xác định người dùng!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("NguoiDung")
                .child(currentUserId)
                .child("Yeuthich");

        userRef.child(idDU).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                isFavorite = true;
                imgYeuthich.setImageResource(R.drawable.heart); // Icon đã yêu thích
            } else {
                isFavorite = false;
                imgYeuthich.setImageResource(R.drawable.heart_3_fill); // Icon chưa yêu thích
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Lỗi kiểm tra trạng thái yêu thích!", Toast.LENGTH_SHORT).show();
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