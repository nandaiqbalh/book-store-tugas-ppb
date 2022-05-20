package com.nandaiqbalh.tugaspbb.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;

public class OnBoardingOne extends AppCompatActivity {

    Button btnNext, btnSkip;

    SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_one);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init
        inisialisasi();

        // button
        mainButton();

        // onboarding helper
        onBoardingStatusHelper();
    }

    private void inisialisasi(){
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSkip = (Button) findViewById(R.id.btn_skip);

        sharedPrefs = new SharedPrefs(this);
    }

    private void onBoardingStatusHelper(){
        if (sharedPrefs.getValues("onboarding").equals("1")){
            Intent intent = new Intent(OnBoardingOne.this, HomeActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }

    private void mainButton(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardingOne.this, OnBoardingTwo.class);
                startActivity(intent);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // set status on boarding
                sharedPrefs.setValues("onboarding", "1");

                Intent intent = new Intent(OnBoardingOne.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }


}