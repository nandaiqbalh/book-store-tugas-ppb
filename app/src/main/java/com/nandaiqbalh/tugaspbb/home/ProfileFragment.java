package com.nandaiqbalh.tugaspbb.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.activity.userprofile.ChangeProfileActivity;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.model.User;
import com.nandaiqbalh.tugaspbb.profilekelompok.AboutActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    SharedPrefs sharedPrefs;
    Button logoutButton, btnChangeProfile;

    ImageButton ibInfo;

    TextView tvName, tvEmail, tvPhone, tvAddress;

    User user, userUpdated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // init
        inisialisasi(view);

        // button triggered
        mainButton(view);

        // atur value
        aturValueText();


        return view;

    }

    private void inisialisasi(View view){
        logoutButton = (Button) view.findViewById(R.id.btn_log_out);

        sharedPrefs = new SharedPrefs(getActivity());

        ibInfo = (ImageButton) view.findViewById(R.id.ib_info);

        btnChangeProfile = (Button) view.findViewById(R.id.btn_change_profile);

        tvName = (TextView) view.findViewById(R.id.tv_name_profile);
        tvEmail = (TextView) view.findViewById(R.id.tv_email_profile);
        tvPhone = (TextView) view.findViewById(R.id.tv_phone_profile);
        tvAddress = (TextView) view.findViewById(R.id.tv_address_profile);

        user = sharedPrefs.getUser();
        userUpdated = sharedPrefs.getUserUpdated();

    }

    private void aturValueText(){

        if (sharedPrefs.getUser() == null){
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        if (userUpdated != null){
            // name
            if (userUpdated.getName().equals("")){
                tvName.setText("Not set.");
            } else {
                tvName.setText(userUpdated.getName());
            }

            // email
            if (userUpdated.getEmail().equals("")){
                tvEmail.setText("Not set.");
            } else {
                tvEmail.setText(userUpdated.getEmail());
            }

            // phone
            if (userUpdated.getPhone() == ""){
                tvPhone.setText("Not set.");
            } else {
                tvPhone.setText(userUpdated.getPhone());
            }

            // address
            if (userUpdated.getAddress() == null){
                tvAddress.setText("Not set.");
            } else {
                tvAddress.setText(userUpdated.getAddress());
            }

            return;
        } else {

            // hanya akan dieksekusi apabila blm update profile
            // name
            if (user.getName().equals("")){
                tvName.setText("Not set.");
            } else {
                tvName.setText(user.getName());
            }

            // email
            if (user.getEmail().equals("")){
                tvEmail.setText("Not set.");
            } else {
                tvEmail.setText(user.getEmail());
            }

            // phone
            if (user.getPhone() == ""){
                tvPhone.setText("Not set.");
            } else {
                tvPhone.setText(user.getPhone());
            }

            // address
            if (user.getAddress() == null){
                tvAddress.setText("Not set.");
            } else {
                tvAddress.setText(user.getAddress());
            }
        }

    }

    private void mainButton(View view){
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefs.setStatusLogin(false);

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);

                Toast.makeText(getActivity(), "Sucessfully to sign you out!", Toast.LENGTH_LONG).show();

                getActivity().finishAffinity();

            }
        });

        ibInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}