package com.example.drinktutorial.Controller;

import android.util.Log;

import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.Model.LoaiDoUong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoaiDoUongController {
    public ArrayList<LoaiDoUong> loaiDoUongs;

    public interface DataStatus{
        void DataIsLoaded(ArrayList<LoaiDoUong> loaiDoUongs);
    }


    public void getListLDU(final DataStatus dataStatus) {
        loaiDoUongs = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("LoaiDoUong");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loaiDoUongs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LoaiDoUong ldu = snapshot.getValue(LoaiDoUong.class);
                    String key = snapshot.getKey();

                    if (ldu != null && key != null) {
                        ldu.setIdLDU(key);
                        loaiDoUongs.add(ldu);
                    }
                }


                dataStatus.DataIsLoaded(loaiDoUongs);
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
