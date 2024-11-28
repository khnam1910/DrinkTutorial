package com.example.drinktutorial.Controller;

import android.util.Log;

import com.example.drinktutorial.Model.DoUong;
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
        void getFilteredDoUong(ArrayList<DoUong> doUongs);
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


    public void searchDoUongByChar(final String query, final DataStatus dataStatus) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DoUong");
        Log.d("aaaaa", "searchDoUongByChar: " + query);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DoUong> filteredList = new ArrayList<>();
                String lowerCaseQuery = query.toLowerCase();  // Chuyển chuỗi tìm kiếm thành chữ thường

                // Duyệt qua tất cả dữ liệu trong Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DoUong du = snapshot.getValue(DoUong.class);
                    if (du != null) {
                        du.setKeyID(snapshot.getKey());

                        // Chuyển tên đồ uống thành chữ thường để so sánh
                        String drinkName = du.getName().toLowerCase();

                        // Kiểm tra nếu tên đồ uống chứa query (đã chuyển thành chữ thường)
                        if (drinkName.contains(lowerCaseQuery)) {
                            filteredList.add(du);
                        }
                    }
                }

                // Trả về danh sách đồ uống tìm được
                dataStatus.getFilteredDoUong(filteredList);
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
