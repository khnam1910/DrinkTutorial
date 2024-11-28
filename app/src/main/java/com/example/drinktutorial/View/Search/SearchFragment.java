package com.example.drinktutorial.View.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterDoUongs;
import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterHotDrink;
import com.example.drinktutorial.Adapter.SearchAdapter.DoUongAdapter;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.Home.DoUongDetailFragment;
import com.example.drinktutorial.View.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchFragment extends Fragment {

    private TextView txtNoResults;
    private EditText txtSearch;
    private RecyclerView recyclerView;
    private CustomAdapterDoUongs adapter;
    private ArrayList<DoUong> filterdoUongList;

    public SearchFragment() {
        // Constructor mặc định
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        addControls(view);


        setupRecyclerView();
        loadDoUong();

        addTextChangeListener();

        return view;
    }

    private void addControls(View view) {
        txtNoResults = view.findViewById(R.id.txtNoResults);
        txtSearch = view.findViewById(R.id.txtSearch);
        recyclerView = view.findViewById(R.id.search_recycler_view);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void addTextChangeListener() {
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().toLowerCase().trim();
                searchDoUong(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void searchDoUong(String query) {
        DoUongController doUongController = new DoUongController();
        doUongController.searchDoUongByChar(query, new DoUongController.DataStatus() {
            @Override
            public void getALlDoUong(ArrayList<DoUong> doUongs) {
                adapter = new CustomAdapterDoUongs(doUongs);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void getFilteredDoUong(ArrayList<DoUong> doUongs) {
                filterdoUongList = doUongs;
                handleSearchResults(filterdoUongList); // Hiển thị kết quả tìm kiếm
            }
        });
    }

    public void loadDoUong()
    {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus() {
            @Override
            public void getALlDoUong(ArrayList<DoUong> doUongs) {
                Collections.shuffle(doUongs);
                adapter = new CustomAdapterDoUongs(doUongs);
                recyclerView.setAdapter(adapter);
                addItemClickListenerForDoUong();
            }

            @Override
            public void getFilteredDoUong(ArrayList<DoUong> doUongs) {

            }
        });
    }

    private void addItemClickListenerForDoUong() {
        if (adapter != null) {
            adapter.setOnItemClickListener(new CustomAdapterDoUongs.OnItemClickListener() {
                @Override
                public void onItemClick(DoUong doUong) {
                    Bundle bundle = new Bundle();
                    bundle.putString("idDoUong", doUong.getKeyID());

                    DoUongDetailFragment doUongDetailFragment = new DoUongDetailFragment();
                    doUongDetailFragment.setArguments(bundle);

                    if (getActivity() instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.loadFragment(doUongDetailFragment);
                    }

                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                    if (activity != null) {
                        Toolbar toolbar = activity.findViewById(R.id.toolbar);
                        activity.setSupportActionBar(toolbar);
                        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }
                }
            });
        } else {
            Log.e("HomeFragment", "adapterHotDrink is null");
        }
    }
    private void handleSearchResults(ArrayList<DoUong> results) {
        if (results.isEmpty()) {
            txtNoResults.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtNoResults.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.updateList(results);

        }
    }
}