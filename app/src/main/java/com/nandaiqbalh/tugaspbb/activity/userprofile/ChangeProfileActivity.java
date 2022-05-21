package com.nandaiqbalh.tugaspbb.activity.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;

public class ChangeProfileActivity extends AppCompatActivity {

    ImageButton btnBackFromEdit;

    Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init
        inisialisasi();

        // button triggered
        mainButton();
    }

    private void mainButton() {

        btnBackFromEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void inisialisasi(){

        btnBackFromEdit = (ImageButton) findViewById(R.id.btn_back_from_edit_profile);

        btnSaveProfile = (Button) findViewById(R.id.btn_save_profile);

    }


}