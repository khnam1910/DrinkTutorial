package com.example.drinktutorial.Controller;

import android.util.Log;
import android.widget.Toast;

import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.View.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoUongController {
    public ArrayList<DoUong> doUongs;
    public interface DataStatus
    {
        void getALlDoUong(ArrayList<DoUong> doUongs);
    }

    public void getListDU(final DataStatus dataStatus) {
        doUongs = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DoUong");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                doUongs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DoUong du = snapshot.getValue(DoUong.class);
                    if (du != null) {
                        du.setKeyID(snapshot.getKey());
                        doUongs.add(du);
                    }
                }

                dataStatus.getALlDoUong(doUongs);
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
