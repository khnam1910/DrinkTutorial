package com.example.drinktutorial.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.drinktutorial.Model.DoUong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchController {

    private FirebaseDatabase database;
    private SearchResultsCallback callback;

    public interface SearchResultsCallback {
        void onSearchResults(List<DoUong> results);
    }

    public SearchController(SearchResultsCallback callback) {
        this.database = FirebaseDatabase.getInstance();
        this.callback = callback;
    }

    public void searchDoUong(String query) {
        database.getReference("DoUong").orderByChild("Name").startAt(query).endAt(query + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 List<DoUong> results = new ArrayList<>();
                 for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                     DoUong doUong = dataSnapshot.getValue(DoUong.class);
                     results.add(doUong);
                 }
                 callback.onSearchResults(results);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                    Log.e("Firebase", "Database error: " + databaseError.getMessage());
                }
            }
        });
    }
}
