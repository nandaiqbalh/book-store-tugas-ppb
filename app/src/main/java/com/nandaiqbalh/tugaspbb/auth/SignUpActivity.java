package com.nandaiqbalh.tugaspbb.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nandaiqbalh.tugaspbb.R;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView tvSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // init
        inisialisasi();

        // button triggered
        mainButton();
    }

    private void inisialisasi(){

        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        tvSignInButton = (TextView) findViewById(R.id.tv_btn_sign_in);
    }

    private void mainButton(){

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // nanti jika sesuai validasi, akan..
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);

                finishAffinity();

            }
        });

        // pindah ke login
        tvSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


    }
}