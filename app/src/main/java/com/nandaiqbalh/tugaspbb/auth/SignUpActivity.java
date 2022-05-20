package com.nandaiqbalh.tugaspbb.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.rest.ApiConfig;
import com.nandaiqbalh.tugaspbb.utils.register.RegisterRequest;
import com.nandaiqbalh.tugaspbb.utils.register.RegisterResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView tvSignInButton;

    EditText edtName, edtEmail, edtPhone, edtPassword;

    TextView tvErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // init
        inisialisasi();

        // button triggered
        mainButton();
    }

    private void inisialisasi(){

        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        tvSignInButton = (TextView) findViewById(R.id.tv_btn_sign_in);

        edtName = (EditText) findViewById(R.id.edt_name_register);
        edtEmail = (EditText) findViewById(R.id.edt_email_register);
        edtPhone = (EditText) findViewById(R.id.edt_phone_register);
        edtPassword = (EditText) findViewById(R.id.edt_password_register);

        tvErrorText = (TextView) findViewById(R.id.tv_errortext);
    }

    private void mainButton(){

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // nanti jika sesuai validasi, akan..

                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setName(edtName.getText().toString());
                registerRequest.setEmail(edtEmail.getText().toString());
                registerRequest.setPhone(edtPhone.getText().toString());
                registerRequest.setPassword(edtPassword.getText().toString());

                register(registerRequest);

//                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
//                startActivity(intent);
//
//                finishAffinity();

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


    private void register(RegisterRequest registerRequest) {

        String emailInput = edtEmail.getText().toString().trim(); // untuk validasi email
        int phoneInput = edtPhone.getText().length(); // untuk validasi nomor telepon
        String passwordInput = edtPassword.getText().toString().trim(); // untuk validasi password
        final Pattern PASSWORD_PATTERN =
                Pattern.compile("^" +
                        "(?=.*[0-9])" +         //at least 1 digit
                        //"(?=.*[a-z])" +         //at least 1 lower case letter
                        //"(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        // "(?=\\S+$)" +           //no white spaces
                        ".{8,}" +               //at least 8 characters
                        "$");

        if (edtName.getText().toString().isEmpty()) {
            tvErrorText.setText("Name field is required!");
            edtName.requestFocus();
            return;
        } else if (emailInput.isEmpty()) {
            tvErrorText.setText("Email field is required!");
            edtEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            tvErrorText.setText("The email address you entered is not valid!");
            edtEmail.requestFocus();
            return;
        } else if (edtPhone.getText().toString().isEmpty()) {
            tvErrorText.setText("Phone number field is required!");
            edtPhone.requestFocus();
            return;
        } else if (phoneInput < 10 || phoneInput > 13) {
            tvErrorText.setText("The phone number you entered is not valid!");
            edtPhone.requestFocus();
            return;
        } else if (passwordInput.isEmpty()) {
            tvErrorText.setText("Password field is required!");
            edtPassword.requestFocus();
            return;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            tvErrorText.setText("The password must have at least 8 digits with number and char!");
            edtPassword.requestFocus();
            return;
        }

        Call<RegisterResponse> registerResponseCall = ApiConfig.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {

                    RegisterResponse respon = response.body();

                    if (respon.getSuccess() == 1) {
                        // berhasil
                        Toast.makeText(SignUpActivity.this, "Welcome " + respon.getUser().getName() + "! Successfully Register!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        finish();
                        startActivity(intent);

                    } else {
                        // gagal
                        Toast.makeText(SignUpActivity.this, "Error : " + respon.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }


            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}