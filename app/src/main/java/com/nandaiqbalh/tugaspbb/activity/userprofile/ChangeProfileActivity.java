package com.nandaiqbalh.tugaspbb.activity.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;
import com.nandaiqbalh.tugaspbb.model.User;

public class ChangeProfileActivity extends AppCompatActivity {

    ImageButton btnBackFromEdit;

    Button btnSaveProfile;

    // Form
    EditText edtNameChange, edtEmailChange, edtPhoneChange, edtAddressChange;

    SharedPrefs sharedPrefs;

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


}