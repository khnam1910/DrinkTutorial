package com.example.drinktutorial.View.Home;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterCarousel;
import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterHotDrink;
import com.example.drinktutorial.Adapter.HomeAdapter.CustomAdapterLDU;
import com.example.drinktutorial.Adapter.HomeAdapter.CutsomAdapterLNL;
import com.example.drinktutorial.Controller.BaiVietController;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Controller.LoaiDoUongController;
import com.example.drinktutorial.Controller.LoaiNguyenLieuController;
import com.example.drinktutorial.Model.BaiViet;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.Model.LoaiDoUong;
import com.example.drinktutorial.Model.LoaiNguyenLieu;
import com.example.drinktutorial.R;
import com.example.drinktutorial.View.MainActivity;
import com.example.drinktutorial.View.News.BaiVietDetailFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    public Handler handler= new Handler(Looper.getMainLooper());
    public Runnable runnable;
    TextView tvTitleNew, tvShowMore1, tvShowMore2;
    public int currentIndex=0;
    CustomAdapterHotDrink adapterHotDrink;
    CustomAdapterCarousel adapterCarousel;
    CustomAdapterLDU adapterLDU;
    CutsomAdapterLNL adapterLNL;
    GridView grdLNL;
    private LinearProgressIndicator progressBar;
    RecyclerView rycCarousel, rycDoUong,rycDoUong1, rycLDU;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        addControls(view);
        setUpRecyclerViews();
        initializeData();
        customView();
        autoScroll();
        clickShowMore();
        return view;
    }


    public void addControls(View view)
    {
        tvTitleNew = view.findViewById(R.id.tvTitleNews);
        rycCarousel = view.findViewById(R.id.rycCarousel);
        rycDoUong= view.findViewById(R.id.rycDoUong);
        rycDoUong1 = view.findViewById(R.id.rycDoUong1);
        rycLDU = view.findViewById(R.id.rycLDU);
        grdLNL = view.findViewById(R.id.grdLNL);
        progressBar = view.findViewById(R.id.progress_bar);
        tvShowMore1 = view.findViewById(R.id.tvShowmore);
        tvShowMore2 = view.findViewById(R.id.tvShowMore1);
    }
    private void initializeData() {
        loadLDU();
        loadDoUong();
        loadDUTheoNgay();
        loadLNL();
        loadBaiViet();
    }

    private void setUpRecyclerViews() {
        rycCarousel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rycDoUong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rycDoUong1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rycLDU.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }



    public void customView()
    {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rycCarousel);
        int space = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_space);
        rycCarousel.addItemDecoration(new SpacesItemDecoration(space));
    }
    private void autoScroll() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (adapterCarousel != null) {
                    if (currentIndex == adapterCarousel.getItemCount()) {
                        currentIndex = 0;
                    }
                }
                rycCarousel.smoothScrollToPosition(currentIndex++);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }


    private void addCarouselScrollListener() {
        adapterCarousel.setOnItemClickListener(new CustomAdapterCarousel.OnItemClickListener() {
            @Override
            public void onItemClick(BaiViet baiViet) {
                Bundle bundle = new Bundle();
                bundle.putString("BaiVietID", baiViet.getKeyID());
                BaiVietDetailFragment baiVietDetailFragment = new BaiVietDetailFragment();
                baiVietDetailFragment.setArguments(bundle);

                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(baiVietDetailFragment);

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
    }
    private void addItemClickListenerForLDU() {
        adapterLDU.setOnItemClickListener(new CustomAdapterLDU.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiDoUong loaiDoUong) {
                Bundle bundle = new Bundle();
                bundle.putString("LoaiDoUongID", loaiDoUong.getIdLDU());
                bundle.putString("tenLDU", loaiDoUong.getTenLoai());

                DoUongTheoLoaiFragment doUongTheoLoaiFragment = new DoUongTheoLoaiFragment();
                doUongTheoLoaiFragment.setArguments(bundle);

                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(doUongTheoLoaiFragment);

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
    }
    private void addItemClickListenerForDoUong() {
        if (adapterHotDrink != null) {
            adapterHotDrink.setOnItemClickListener(new CustomAdapterHotDrink.OnItemClickListener() {
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

    public void clickShowMore()
    {
        tvShowMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachDoUongFragment danhSachDoUongFragment = new DanhSachDoUongFragment();
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(danhSachDoUongFragment);

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
        tvShowMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachDoUongFragment danhSachDoUongFragment = new DanhSachDoUongFragment();
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.loadFragment(danhSachDoUongFragment);

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
    }
    public void loadBaiViet() {
        BaiVietController baiVietController = new BaiVietController();
        baiVietController.getListBV(new BaiVietController.DataStatus() {
            @Override
            public void getAllBaiViet(ArrayList<BaiViet> baiVietList) {

                int size = baiVietList.size();
                if (size > 5) {

                    Collections.shuffle(baiVietList);


                    ArrayList<BaiViet> randomBaiViet = new ArrayList<>(baiVietList.subList(0, 5));


                    adapterCarousel = new CustomAdapterCarousel(randomBaiViet);
                } else {

                    adapterCarousel = new CustomAdapterCarousel(baiVietList);
                }

                rycCarousel.setAdapter(adapterCarousel);
                addCarouselScrollListener();
            }
        });
    }

    public void loadDoUong()
    {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus()
        {
            @Override
            public void getALlDoUong(ArrayList<DoUong> doUongs) {
                Collections.shuffle(doUongs);

                if (doUongs.size() > 10) {
                    doUongs = new ArrayList<>(doUongs.subList(0, 10));
                }

                adapterHotDrink = new CustomAdapterHotDrink(doUongs);
                rycDoUong.setAdapter(adapterHotDrink);
                addItemClickListenerForDoUong();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void getFilteredDoUong(ArrayList<DoUong> doUongs) {

            }
        });
    }

    public void loadDUTheoNgay() {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus() {
            @Override
            public void getALlDoUong(ArrayList<DoUong> doUongs) {
                ArrayList<DoUong> sortedDoUongs = sortDoUongsByDate(doUongs);

                if (sortedDoUongs.size() > 10) {
                    sortedDoUongs = new ArrayList<>(sortedDoUongs.subList(0, 10));
                }
                adapterHotDrink = new CustomAdapterHotDrink(sortedDoUongs);
                rycDoUong1.setAdapter(adapterHotDrink);
                addItemClickListenerForDoUong();
            }

            @Override
            public void getFilteredDoUong(ArrayList<DoUong> doUongs) {

            }
        });
    }
    @NonNull
    private ArrayList<DoUong> sortDoUongsByDate(ArrayList<DoUong> doUongs) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = calendar.getTime();

        long[] daysDifferences = new long[doUongs.size()];

        // Tính toán sự khác biệt ngày
        for (int i = 0; i < doUongs.size(); i++) {
            DoUong doUong = doUongs.get(i);
            try {
                Date drinkDate = sdf.parse(doUong.getNgay());
                long diffInMillis = currentDate.getTime() - drinkDate.getTime();
                long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
                daysDifferences[i] = diffInDays;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Long[] sortedDaysDifferences = Arrays.stream(daysDifferences).boxed().toArray(Long[]::new);
        Arrays.sort(sortedDaysDifferences);

        ArrayList<DoUong> sortedDoUongs = new ArrayList<>();

        for (long dayDifference : sortedDaysDifferences) {
            for (int j = 0; j < daysDifferences.length; j++) {
                if (daysDifferences[j] == dayDifference) {
                    sortedDoUongs.add(doUongs.get(j));
                }
            }
        }

        return sortedDoUongs;
    }
    public void loadLDU() {
        LoaiDoUongController loaiDoUongController = new LoaiDoUongController();
        loaiDoUongController.getListLDU(new LoaiDoUongController.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<LoaiDoUong> loaiDoUongs) {
                Collections.shuffle(loaiDoUongs);

                adapterLDU = new CustomAdapterLDU(loaiDoUongs);
                rycLDU.setAdapter(adapterLDU);
                addItemClickListenerForLDU();
            }
        });
    }




    public void loadLNL()
    {
        LoaiNguyenLieuController loaiNguyenLieuController = new LoaiNguyenLieuController();
        loaiNguyenLieuController.getListLNL(new LoaiNguyenLieuController.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<LoaiNguyenLieu> loaiNguyenLieus) {
                adapterLNL = new CutsomAdapterLNL(getContext(),loaiNguyenLieus);
                grdLNL.setAdapter(adapterLNL);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
    }
    @Override
    public void onResume() {
        super.onResume();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity instanceof MainActivity) {
            BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigation);
            bottomNavigationView.setVisibility(View.VISIBLE); // Hiện BottomNavigationView
        }
    }
}
