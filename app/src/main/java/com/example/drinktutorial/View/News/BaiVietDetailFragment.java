package com.example.drinktutorial.View.News;

import android.content.SharedPreferences;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.drinktutorial.Controller.BaiVietController;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


public class BaiVietDetailFragment extends Fragment {

    TextView tvTenBV, tvNoiDung;
    String idBaiViet;
    ImageView imgBaiViet, imgBVYeuThich;

    LinearLayout layoutNoiDung;
    boolean isFavorite = false; //Lưu trạng thái yêu thích

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
        imgBVYeuThich.setOnClickListener(v -> toggleFavorite());
        return view;
    }

    public void addControls(View view)
    {
        tvTenBV = view.findViewById(R.id.tvTenBV);
        tvNoiDung = view.findViewById(R.id.tvNoiDung);
        imgBaiViet = view.findViewById(R.id.imgBaiViet);
        layoutNoiDung = view.findViewById(R.id.layoutNoiDung);
        imgBVYeuThich = view.findViewById(R.id.imgBVYeuThich);
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
                        ArrayList<String> imageUrls = new ArrayList<>();
                        if (imgUrl != null && !imgUrl.isEmpty()) {
                            imageUrls.addAll(imgUrl.values());
                        }


                        if (!imageUrls.isEmpty()) {
                            Glide.with(getContext())
                                    .load(imageUrls.get(0))
                                    .into(imgBaiViet);
                        }

                        displayContentWithImages(layoutNoiDung, baiviet.getNoiDung(), imageUrls);
                        checkIfFavorite(idBaiViet);

                    }
                }
            }
        });
    }



    private void displayContentWithImages(LinearLayout layoutNoiDung, String content, ArrayList<String> imageUrls) {
        layoutNoiDung.removeAllViews();

        String[] words = content.split("\\s+");
        StringBuilder paragraphBuilder = new StringBuilder();
        int dotCount = 0;
        int imageIndex = 1;

        for (String word : words) {
            paragraphBuilder.append(word).append(" ");

            for (char c : word.toCharArray()) {
                if (c == '.') {
                    dotCount++;
                }
            }

            if (dotCount >= 3) {

                TextView textView = new TextView(layoutNoiDung.getContext());
                textView.setText(paragraphBuilder.toString().trim());


                textView.setTextSize(16);
                textView.setTextColor(ContextCompat.getColor(layoutNoiDung.getContext(), R.color.black));
                textView.setPadding(0, 16, 0, 16);
                textView.setLineSpacing(0, 1.5f);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
                }

                // Thêm vào layout
                layoutNoiDung.addView(textView);

                paragraphBuilder.setLength(0); // Reset đoạn văn
                dotCount = 0;

                // Thêm hình ảnh nếu có
                if (imageUrls != null && imageIndex < imageUrls.size()) {
                    ImageView imageView = new ImageView(layoutNoiDung.getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1000
                    );
                    layoutParams.setMargins(0, 16, 0, 16);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    Glide.with(layoutNoiDung.getContext())
                            .load(imageUrls.get(imageIndex)) // Ảnh thứ hai trở đi
                            .override(1000,1000)
                            .into(imageView);

                    layoutNoiDung.addView(imageView);
                    imageIndex++; // Chuyển sang ảnh tiếp theo
                }
            }
        }

        // Thêm đoạn văn cuối cùng nếu còn sót
        if (paragraphBuilder.length() > 0) {
            TextView textView = new TextView(layoutNoiDung.getContext());
            textView.setText(paragraphBuilder.toString().trim());

            // Áp dụng các thuộc tính giống như trong layout XML
            textView.setTextSize(16);
            textView.setTextColor(ContextCompat.getColor(layoutNoiDung.getContext(), R.color.black));
            textView.setPadding(0, 16, 0, 16);
            textView.setLineSpacing(0, 1.5f);

            // Căn chỉnh giống như trong XML
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            }

            layoutNoiDung.addView(textView);
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
            userRef.child("BaiVietYeuThich").child(idBaiViet).setValue(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isFavorite = true;
                    imgBVYeuThich.setImageResource(R.drawable.heart);
                    Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            userRef.child("BaiVietYeuThich").child(idBaiViet).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    isFavorite = false;
                    imgBVYeuThich.setImageResource(R.drawable.heart_3_fill);
                    Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkIfFavorite(String idBV) {
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
                .child("BaiVietYeuThich");

        userRef.child(idBV).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                isFavorite = true;
                imgBVYeuThich.setImageResource(R.drawable.heart); // Icon đã yêu thích
            } else {
                isFavorite = false;
                imgBVYeuThich.setImageResource(R.drawable.heart_3_fill); // Icon chưa yêu thích
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getContext(), "Lỗi kiểm tra trạng thái yêu thích!", Toast.LENGTH_SHORT).show();
        });
    }
}