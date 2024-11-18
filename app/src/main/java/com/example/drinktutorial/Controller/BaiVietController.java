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

public class BaiVietController {
    public ArrayList<BaiViet> Baiviet;

    public interface DataStatus{
        void getAllBaiViet(ArrayList<BaiViet> Baiviet);
    }

    public void getListBV(final DoUongController.DataStatus dataStatus) {
        Baiviet = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("BaiViet");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Baiviet.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BaiViet bv = snapshot.getValue(BaiViet.class);
                    if (bv != null) {
                        bv.setKeyID(snapshot.getKey());
                        Baiviet.add(bv);
                    }
                }

//                dataStatus.getAllBaiViet(Baiviet);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi
                if (databaseError != null) {
                    Log.e("Firebase", "Database error: " + databaseError.getMessage());
                }
            }
        });
    }
}
