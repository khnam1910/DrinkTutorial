package com.example.drinktutorial.View.Search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinktutorial.Adapter.SearchAdapter.DoUongAdapter;
import com.example.drinktutorial.Controller.SearchController;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    private TextView txtNoResults;
    private EditText txtSearch;
    private ImageView btnSearch;
    private RecyclerView recyclerView;
    private DoUongAdapter adapter;
    private List<DoUong> doUongList;
    private SearchController searchController;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchController = new SearchController(new SearchController.SearchResultsCallback() {
            @Override
            public void onSearchResults(List<DoUong> results) {
                handleSearchResults(results);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        addControls(view);
        setupRecyclerView();
        addEvents();

        return view;
    }

    private void addControls(View view) {
        txtNoResults = view.findViewById(R.id.txtNoResults);
        txtSearch = view.findViewById(R.id.txtSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.search_recycler_view);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        doUongList = new ArrayList<>();
        adapter = new DoUongAdapter(getContext(), doUongList);
        recyclerView.setAdapter(adapter);
    }

    private void addEvents() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String query = txtSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(query)) {
            searchController.searchDoUong(query);
        } else {
            Toast.makeText(getContext(), "Vui lòng nhập tên đồ uống", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSearchResults(List<DoUong> results) {
        if (results.isEmpty()) {
            txtNoResults.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtNoResults.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            doUongList = results;
            adapter.updateList(doUongList);
        }
    }
}