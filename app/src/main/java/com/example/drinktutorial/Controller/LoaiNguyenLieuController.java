package com.example.drinktutorial.Controller;

import android.util.Log;

import com.example.drinktutorial.Model.LoaiDoUong;
import com.example.drinktutorial.Model.LoaiNguyenLieu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoaiNguyenLieuController {

    public ArrayList<LoaiNguyenLieu> loaiNguyenLieus;

    public interface DataStatus{
        void DataIsLoaded(ArrayList<LoaiNguyenLieu> loaiNguyenLieus);
    }

    public void getListLNL(final DataStatus dataStatus) {
        loaiNguyenLieus = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LoaiNguyenLieu");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loaiNguyenLieus.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LoaiNguyenLieu lnl = snapshot.getValue(LoaiNguyenLieu.class);
                    String key = snapshot.getKey();

                    if (lnl != null && key != null) {
                        lnl.setKeyID(key);
                        loaiNguyenLieus.add(lnl);
                    }
                }


                dataStatus.DataIsLoaded(loaiNguyenLieus);
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
