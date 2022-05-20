package com.nandaiqbalh.tugaspbb.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;

public class SignInActivity extends AppCompatActivity {

    SharedPrefs sharedPrefs;

    Button signInButton;
    TextView tvSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inisialisasi();

        mainButton();

    }

    private void inisialisasi(){
        // inisiasi shared prefs
        sharedPrefs = new SharedPrefs(this);

        // inisiasi button
        signInButton = (Button) findViewById(R.id.btn_sign_in);
        tvSignUpButton = (TextView) findViewById(R.id.tv_btn_signup);
        
    }

    private void mainButton(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // jika credential sesuai validasi maka akan true, dan kembali ke home activity
                sharedPrefs.setStatusLogin(true);

                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();

            }
        });

        tvSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}