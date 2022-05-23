package com.nandaiqbalh.tugaspbb.activity.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.auth.SignUpActivity;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;
import com.nandaiqbalh.tugaspbb.home.ProfileFragment;
import com.nandaiqbalh.tugaspbb.model.User;
import com.nandaiqbalh.tugaspbb.rest.ApiConfig;
import com.nandaiqbalh.tugaspbb.utils.login.LoginRequest;
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

    TextView errorChangeProfile;

    SharedPrefs sharedPrefs;

    UserProfileRequest userProfileRequest;

    User user, userUpdated;

    ProgressBar pbChangeProfile;

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

    }

    private void inisialisasi(){

        btnBackFromEdit = (ImageButton) findViewById(R.id.btn_back_from_edit_profile);

        btnSaveProfile = (Button) findViewById(R.id.btn_save_profile);

        edtNameChange = (EditText) findViewById(R.id.tv_name_change);
        edtEmailChange = (EditText) findViewById(R.id.tv_email_change);
        edtPhoneChange = (EditText) findViewById(R.id.tv_phone_change);
        edtAddressChange = (EditText) findViewById(R.id.tv_address_change);

        errorChangeProfile = (TextView) findViewById(R.id.tv_errortext_change_profile);

        sharedPrefs = new SharedPrefs(this);

        user = sharedPrefs.getUser();

        userUpdated = sharedPrefs.getUserUpdated();

        userProfileRequest = new UserProfileRequest();

        pbChangeProfile = (ProgressBar) findViewById(R.id.pb_change_profile);

    }

    private void aturValueForm(){
        if (sharedPrefs.getUser() == null){
            Intent intent = new Intent(ChangeProfileActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        if(userUpdated != null){
            edtNameChange.setText(userUpdated.getName());
            edtPhoneChange.setText(userUpdated.getPhone());
            edtEmailChange.setText(userUpdated.getEmail());

            if (userUpdated.getAddress() == null){
                edtAddressChange.setHint("Not set.");
            } else {
                edtAddressChange.setText(userUpdated.getAddress());
            }

            // returnkan boss, biar bawahnya gausah dieksekusi
            return;

        } else {
            edtNameChange.setText(user.getName());
            edtPhoneChange.setText(user.getPhone());
            edtEmailChange.setText(user.getEmail());

            if (user.getAddress() == null){
                edtAddressChange.setHint("Not set.");
            } else {
                edtAddressChange.setText(user.getAddress());
            }
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

                    // ambil user untuk menampilkan data user terbaru ke dalam interface
                    sharedPrefs.setUserUpdated(respon.getUser()); // manggil kembalian field user secara langsung

                    if (respon.getSuccess() == 1) {

                        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // networking untuk update profile
                                UserProfileRequest userProfileRequestUpdate = new UserProfileRequest();

                                String oldEmail = user.getEmail();
                                String oldPhone = user.getPhone();

                                if (oldEmail.equals(edtEmailChange.getText().toString().trim())){
                                    // ga usah kirim email
                                } else {
                                    userProfileRequestUpdate.setEmail(edtEmailChange.getText().toString());
                                }

                                if (oldPhone.equals(edtPhoneChange.getText().toString().trim())){
                                    // gausah kirim phone
                                } else {
                                    userProfileRequestUpdate.setPhone(edtPhoneChange.getText().toString());
                                }

                                userProfileRequestUpdate.setId(user.getId());
                                userProfileRequestUpdate.setName(edtNameChange.getText().toString());
                                userProfileRequestUpdate.setAddress(edtAddressChange.getText().toString());

                                updateUserProfile(userProfileRequestUpdate);
                            }
                        });


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

    private void updateUserProfile(UserProfileRequest userProfileRequest) {

        if (sharedPrefs.getUser() == null){
            Intent intent = new Intent(ChangeProfileActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        String nameInput = edtNameChange.getText().toString().trim(); // untuk validasi name
        String emailInput = edtEmailChange.getText().toString().trim(); // untuk validasi email
        String phoneInput = edtPhoneChange.getText().toString().trim(); // untuk validasi phone

        if (emailInput.isEmpty()) {
            errorChangeProfile.setText("Email field is required!");
            edtEmailChange.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            errorChangeProfile.setText("The email address you entered is not valid!");
            edtEmailChange.requestFocus();
            return;
        } else if (nameInput.isEmpty()) {
            errorChangeProfile.setText("Name field is required!");
            edtNameChange.requestFocus();
            return;
        } else if (phoneInput.isEmpty()) {
            errorChangeProfile.setText("Phone field is required!");
            edtPhoneChange.requestFocus();
            return;
        }

        pbChangeProfile.setVisibility(View.VISIBLE);

        Call<UserProfileResponse> userUpdateProfileCall = ApiConfig.getService().updateUserProfile(userProfileRequest);
        userUpdateProfileCall.enqueue(new Callback<UserProfileResponse>() {

            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                pbChangeProfile.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    UserProfileResponse respon = response.body();

                    if (respon.getSuccess() == 1) {

                        Toast.makeText(ChangeProfileActivity.this, "Your profile has been updated!", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ChangeProfileActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } else {
                        // gagal
                        Toast.makeText(ChangeProfileActivity.this, "Error : " + respon.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {

                pbChangeProfile.setVisibility(View.GONE);

                String message = t.getLocalizedMessage();
                Toast.makeText(ChangeProfileActivity.this, message, Toast.LENGTH_LONG).show();
            }


        });
    }



}