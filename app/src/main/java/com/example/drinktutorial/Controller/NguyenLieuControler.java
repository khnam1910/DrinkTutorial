package com.example.drinktutorial.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.drinktutorial.Model.NguyenLieu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NguyenLieuControler {

    public ArrayList<NguyenLieu> nguyenLieus;
    public interface DataStatus{
        void getAllNL(ArrayList<NguyenLieu> nguyenLieus);
    }

    public void getListNL(final DataStatus dataStatus)
    {
        nguyenLieus = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("NguyenLieu");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nguyenLieus.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NguyenLieu nl = snapshot.getValue(NguyenLieu.class);
                    if (nl != null) {
                        nl.setKeyID(snapshot.getKey());
                        nguyenLieus.add(nl);
                    }
                }
                dataStatus.getAllNL(nguyenLieus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (error != null) {
                    Log.e("Firebase", "Database error: " + error.getMessage());
                }
            }
        });
    }
}
