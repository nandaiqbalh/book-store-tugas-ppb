package com.nandaiqbalh.tugaspbb.activity.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.auth.SignUpActivity;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;
import com.nandaiqbalh.tugaspbb.model.User;
import com.nandaiqbalh.tugaspbb.rest.ApiConfig;
import com.nandaiqbalh.tugaspbb.utils.register.RegisterRequest;
import com.nandaiqbalh.tugaspbb.utils.register.RegisterResponse;
import com.nandaiqbalh.tugaspbb.utils.userprofile.UserProfileRequest;
import com.nandaiqbalh.tugaspbb.utils.userprofile.UserProfileResponse;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileActivity extends AppCompatActivity {

    ImageButton btnBackFromEdit;

    Button btnSaveProfile;

    // Form
    EditText edtNameChange, edtEmailChange, edtPhoneChange, edtAddressChange;

    SharedPrefs sharedPrefs;

    UserProfileRequest userProfileRequest;

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

        // value form edit
        aturValueForm();

        // get user profile
        userProfileRequest.setId(sharedPrefs.getInt(sharedPrefs.getId()));
        getUserProfile(userProfileRequest);
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

        edtNameChange = (EditText) findViewById(R.id.tv_name_change);
        edtEmailChange = (EditText) findViewById(R.id.tv_email_change);
        edtPhoneChange = (EditText) findViewById(R.id.tv_phone_change);
        edtAddressChange = (EditText) findViewById(R.id.tv_address_change);

        sharedPrefs = new SharedPrefs(this);

        userProfileRequest = new UserProfileRequest();
    }

    private void aturValueForm(){
        if (sharedPrefs.getUser() == null){
            Intent intent = new Intent(ChangeProfileActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        User user = sharedPrefs.getUser();

        edtNameChange.setText(user.getName());
        edtPhoneChange.setText(user.getPhone());
        edtEmailChange.setText(user.getEmail());



        if (user.getAddress() == null){
            edtAddressChange.setText("Not set.");
        } else {
            edtAddressChange.setText(user.getAddress());
        }
    }


    private void getUserProfile(UserProfileRequest userProfileRequest) {

        if (sharedPrefs.getUser() == null){
            Intent intent = new Intent(ChangeProfileActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        Call<UserProfileResponse> userProfileResponseCall = ApiConfig.getService().getProfile(userProfileRequest);
        userProfileResponseCall.enqueue(new Callback<UserProfileResponse>() {

            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                if (response.isSuccessful()) {

                    UserProfileResponse respon = response.body();

                    if (respon.getSuccess() == 1) {
                        // berhasil

                    } else {
                        // gagal
                        Toast.makeText(ChangeProfileActivity.this, "Error : " + respon.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(ChangeProfileActivity.this, message, Toast.LENGTH_LONG).show();
            }


        });
    }


}