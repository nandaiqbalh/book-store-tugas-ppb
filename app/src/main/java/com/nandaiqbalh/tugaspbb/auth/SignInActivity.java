package com.nandaiqbalh.tugaspbb.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;
import com.nandaiqbalh.tugaspbb.rest.ApiConfig;
import com.nandaiqbalh.tugaspbb.utils.login.LoginRequest;
import com.nandaiqbalh.tugaspbb.utils.login.LoginResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    SharedPrefs sharedPrefs;

    Button signInButton;
    TextView tvSignUpButton;

    EditText edtEmailLogin, edtPasswordLogin;
    TextView tvErrorTextLogin;

    ProgressBar progressBarSignIn;

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

        // inisiasi edittext
        edtEmailLogin = (EditText) findViewById(R.id.edt_email_login);
        edtPasswordLogin = (EditText) findViewById(R.id.edt_password_login);

        tvErrorTextLogin = (TextView) findViewById(R.id.tv_errortext_login);

        // progress bar
        progressBarSignIn = (ProgressBar) findViewById(R.id.pb_login);
        
    }

    private void mainButton(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(edtEmailLogin.getText().toString());
                loginRequest.setPassword(edtPasswordLogin.getText().toString());

                signIn(loginRequest);

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

    private void signIn(LoginRequest loginRequest){

        String emailInput = edtEmailLogin.getText().toString().trim(); // untuk validasi email
        String passwordInput = edtPasswordLogin.getText().toString().trim(); // untuk validasi password
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

        if (emailInput.isEmpty()) {
            tvErrorTextLogin.setText("Email field is required!");
            edtEmailLogin.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            tvErrorTextLogin.setText("The email address you entered is not valid!");
            edtEmailLogin.requestFocus();
            return;
        } else if (passwordInput.isEmpty()) {
            tvErrorTextLogin.setText("Password field is required!");
            edtPasswordLogin.requestFocus();
            return;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            tvErrorTextLogin.setText("The password must have at least 8 digits with number and char!");
            edtPasswordLogin.requestFocus();
            return;
        }

        progressBarSignIn.setVisibility(View.VISIBLE);

        Call<LoginResponse> loginResponseCall = ApiConfig.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                // set progress bar to gone
                progressBarSignIn.setVisibility(View.GONE);

                LoginResponse respon = response.body();

                if (respon.getSuccess() == 1) {
                    // berhasil
                    Toast.makeText(SignInActivity.this, "Welcome " + respon.getUser().getName() + "! Successfully Login!", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    finish();
                    startActivity(intent);

                    // set status login
                    sharedPrefs.setStatusLogin(true);

                } else {
                    // gagal
                    Toast.makeText(SignInActivity.this, "Error : " + respon.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                // set progress bar to gone
                progressBarSignIn.setVisibility(View.GONE);

                String message = t.getLocalizedMessage();
                Toast.makeText(SignInActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}