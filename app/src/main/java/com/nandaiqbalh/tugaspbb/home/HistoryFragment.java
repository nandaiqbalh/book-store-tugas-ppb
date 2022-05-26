package com.nandaiqbalh.tugaspbb.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.adapter.HistoryAdapter;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.model.Transaction;
import com.nandaiqbalh.tugaspbb.rest.ApiConfig;
import com.nandaiqbalh.tugaspbb.utils.checkout.CheckoutRequest;
import com.nandaiqbalh.tugaspbb.utils.checkout.CheckoutResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    RecyclerView rvHistoryCheckout;

    SharedPrefs sharedPrefs;

    CheckoutRequest checkoutRequest;
    CheckoutResponse checkoutResponse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // init
        inisialisasi(view);

        if (sharedPrefs.getStatusLogin()) {

            checkoutRequest.setUser_id(sharedPrefs.getUser().getId());
            getCheckoutHistory(checkoutRequest);
        }

        return view;

    }

    private void inisialisasi(View view) {
        rvHistoryCheckout = (RecyclerView) view.findViewById(R.id.rv_history_transaction);

        sharedPrefs = new SharedPrefs(getActivity());

        checkoutRequest = new CheckoutRequest();
        checkoutResponse = new CheckoutResponse();
    }

    private ArrayList<Transaction> checkoutHistoryArrayList = new ArrayList<>();

    private void getCheckoutHistory(CheckoutRequest checkoutRequest) {
        Call<CheckoutResponse> checkoutHistory = ApiConfig.getService().checkoutHistory(checkoutRequest);
        checkoutHistory.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {

                checkoutResponse = response.body();

                if (checkoutResponse.getSuccess() == 1) {
                    checkoutHistoryArrayList = checkoutResponse.getTransaction();
                    displayBook();
                }

            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {

            }
        });
    }

    private void displayBook() {

        LinearLayoutManager historyCheckouLinearLayoutManager = new LinearLayoutManager(getActivity());
        historyCheckouLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvHistoryCheckout.setLayoutManager(historyCheckouLinearLayoutManager);
        rvHistoryCheckout.setAdapter(new HistoryAdapter(requireActivity(), checkoutHistoryArrayList));
    }
}