package com.example.drinktutorial.View;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.drinktutorial.Adapter.CustomAdapterCarousel;
import com.example.drinktutorial.Adapter.CustomAdapterHotDrink;
import com.example.drinktutorial.Adapter.CustomAdapterLDU;
import com.example.drinktutorial.Adapter.CutsomAdapterLNL;
import com.example.drinktutorial.Controller.DoUongController;
import com.example.drinktutorial.Controller.LoaiDoUongController;
import com.example.drinktutorial.Controller.LoaiNguyenLieuController;
import com.example.drinktutorial.Model.CustomItem;
import com.example.drinktutorial.Model.DoUong;
import com.example.drinktutorial.Model.LoaiDoUong;
import com.example.drinktutorial.Model.LoaiNguyenLieu;
import com.example.drinktutorial.R;
import com.google.firebase.database.FirebaseDatabase;

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
    TextView tvTitleNew;

    public int currentIndex=0;
    CustomAdapterHotDrink adapterHotDrink;
    CustomAdapterCarousel adapterCarousel;
    CustomAdapterLDU adapterLDU;
    CutsomAdapterLNL adapterLNL;
    GridView grdLNL;
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
        loadLDU();
        loadDoUong();
        loadDUTheoNgay();
        loadLNL();
        initData();
        addEvent();
        autoScroll();
        customView();

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
    }
    public void addEvent()
    {
        rycCarousel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if(layoutManager!=null)
                    {
                        int currentVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                        currentIndex = currentVisiblePosition + 1;
                    }
                }
            }
        });
    }
    public void customView()
    {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rycCarousel);
        int space = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_space);
        rycCarousel.addItemDecoration(new SpacesItemDecoration(space));
    }
    public void autoScroll()
    {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (adapterCarousel != null)
                {
                    if (currentIndex == adapterCarousel.getItemCount()) {
                        currentIndex = 0;
                    }

                }
                Log.d("autoScroll", "run: "+ currentIndex);
                rycCarousel.smoothScrollToPosition(currentIndex++);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable,3000);
    }
    public void initData()
    {
        rycCarousel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<CustomItem> itemList = new ArrayList<>();
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Thần dược chữa bệnh"));
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Thần dược chữa bệnh"));
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Thần dược chữa bệnh"));
        itemList.add(new CustomItem("https://images2.thanhnien.vn/zoom/448_280/528068263637045248/2024/10/30/p1-online-173030062963390729935-97-0-737-1024-crop-17303006661241912607037.jpg", "Thần dược chữa bệnh"));
        adapterCarousel = new CustomAdapterCarousel(itemList);
        rycCarousel.setAdapter(adapterCarousel);
    }
    public void loadDoUong()
    {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<DoUong> doUongs) {
                Collections.shuffle(doUongs);
                rycDoUong.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapterHotDrink = new CustomAdapterHotDrink(doUongs);
                rycDoUong.setAdapter(adapterHotDrink);


            }
        });
    }

    public void loadDUTheoNgay() {
        DoUongController doUongController = new DoUongController();
        doUongController.getListDU(new DoUongController.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<DoUong> doUongs) {

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


                rycDoUong1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapterHotDrink = new CustomAdapterHotDrink(sortedDoUongs);
                rycDoUong1.setAdapter(adapterHotDrink);
            }
        });
    }


    public void loadLDU()
    {
        LoaiDoUongController loaiDoUongController = new LoaiDoUongController();
        loaiDoUongController.getListLDU(new LoaiDoUongController.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<LoaiDoUong> loaiDoUongs) {
                Collections.shuffle(loaiDoUongs);
                rycLDU.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapterLDU = new CustomAdapterLDU(loaiDoUongs);
                rycLDU.setAdapter(adapterLDU);
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
}