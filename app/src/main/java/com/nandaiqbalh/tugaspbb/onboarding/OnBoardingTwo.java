package com.nandaiqbalh.tugaspbb.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;

public class OnBoardingTwo extends AppCompatActivity {

    Button btnNext, btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_two);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init
        inisialisasi();

        // button
        mainButton();

    }

    private void mainButton(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardingTwo.this, OnBoardingThree.class);
                startActivity(intent);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OnBoardingTwo.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    private void inisialisasi(){
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSkip = (Button) findViewById(R.id.btn_skip);
    }
}