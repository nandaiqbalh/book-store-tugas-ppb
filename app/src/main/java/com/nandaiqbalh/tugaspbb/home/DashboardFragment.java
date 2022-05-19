package com.nandaiqbalh.tugaspbb.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.slider.Slider;
import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.adapter.BookAdapter;
import com.nandaiqbalh.tugaspbb.adapter.SliderAdapter;
import com.nandaiqbalh.tugaspbb.model.Book;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    ViewPager vpSlider;
    SliderAdapter sliderAdapter;

    // recycler view
    RecyclerView recyclerView;
    ArrayList<Book> dataHolder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // init
        inisialisasi(view);

        // slider
        vpSlider.setAdapter(sliderAdapter);

        // latest book
        setLatestBook(view);

        return view;

    }

    private void inisialisasi(View view){
        vpSlider = view.findViewById(R.id.vp_slider);

        sliderAdapter = new SliderAdapter(getContext());
    }

    private void setLatestBook(View view){
        // produk recycler view
        recyclerView = view.findViewById(R.id.rv_latest_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // menambahkan produk ke holder -> Featured Product
        dataHolder = new ArrayList<>();
        Book produk1 = new Book("Geez & Ann #1", "Rintik Sedu", "Rp. 99.000" ,R.drawable.buku_geez1);
        dataHolder.add(produk1);
        Book produk2 = new Book("Konspirasi Alam Semesta", "Fiersa Besari", "Rp. 75.000" ,R.drawable.buku_kolase);
        dataHolder.add(produk2);
        Book produk3 = new Book("Bumi Manusia", "Pramudya Ananta Toer", "Rp. 124.000" ,R.drawable.buku_bumi_manusia);
        dataHolder.add(produk3);
        Book produk4 = new Book("Geez & Ann #2", "Rintik Sedu", "Rp. 99.000" ,R.drawable.buku_geez2);
        dataHolder.add(produk4);
        Book produk5 = new Book("Geez & Ann #3", "Rintik Sedu", "Rp. 99.000" ,R.drawable.buku_geez3);
        dataHolder.add(produk5);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new BookAdapter(dataHolder));
    }

}