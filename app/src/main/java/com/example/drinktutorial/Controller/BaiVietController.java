package com.example.drinktutorial.Controller;

import android.util.Log;

import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.Model.DoUong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class BaiVietController {
    public ArrayList<BaiViet> baiViets;

    public interface DataStatus{
        void getAllBaiViet(ArrayList<BaiViet> Baiviet);
    }

    public void getListBV(final DataStatus dataStatus) {
        baiViets = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BaiViet");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                baiViets.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    try {
                        String keyID = data.getKey();
                        String tieuDe = data.child("TieuDe").getValue(String.class);
                        String noiDung = data.child("NoiDung").getValue(String.class);
                        String date = data.child("NgayDang").getValue(String.class);

                        // Chuyển đổi HashMap
                        HashMap<String, String> imgUrl = (HashMap<String, String>) data.child("HinhAnh").getValue();

                        BaiViet baiViet = new BaiViet(keyID, tieuDe, imgUrl, noiDung, date);
                        baiViets.add(baiViet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                dataStatus.getAllBaiViet(baiViets);
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Xử lý lỗi
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

}
