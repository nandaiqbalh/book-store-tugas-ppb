package com.nandaiqbalh.tugaspbb.profilekelompok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nandaiqbalh.tugaspbb.R;

public class NandaDetailActivity extends AppCompatActivity {

    LinearLayout btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nanda_detail);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();

        mainButton();
    }

    private void init(){

        btnBack = (LinearLayout) findViewById(R.id.btn_back_from_nanda);
    }

    private void mainButton(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NandaDetailActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}