package com.nandaiqbalh.tugaspbb.activity.checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.activity.book.DetailBookActivity;
import com.nandaiqbalh.tugaspbb.activity.userprofile.ChangeProfileActivity;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;
import com.nandaiqbalh.tugaspbb.model.User;

public class CheckoutActivity extends AppCompatActivity {

    ImageButton ibBack;

    // shipping address
    EditText edtNameCheckout, edtEmailCheckout, edtPhoneCheckout, edtAddressCheckout;

    // buku
    ImageView imgBukuCheckout;
    TextView tvDiskonBukuCheckout, tvJudulBuku, tvPenulisBuku, tvHargaAwal, tvHargaBuku;

    // shared preferences
    SharedPrefs sharedPrefs;

    // User Data
    User user, userUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // init
        inisialisasi();

        // btn triggered
        mainButton();

        // setvalue for shipping address
        setValueShippingAddress();

    }

    private void inisialisasi(){

        // img button
        ibBack = (ImageButton) findViewById(R.id.btn_back_from_chekout);

        // shipping address
        edtNameCheckout = (EditText) findViewById(R.id.tv_name_checkout);
        edtEmailCheckout = (EditText) findViewById(R.id.tv_email_checkout);
        edtPhoneCheckout = (EditText) findViewById(R.id.tv_phone_checkout);
        edtAddressCheckout = (EditText) findViewById(R.id.tv_address_checkout);

        // buku
        imgBukuCheckout = (ImageView) findViewById(R.id.img_buku_checkout);
        tvJudulBuku = (TextView) findViewById(R.id.tv_judul_buku_checkout);
        tvPenulisBuku = (TextView) findViewById(R.id.tv_penulis_buku_checkout);
        tvDiskonBukuCheckout = (TextView) findViewById(R.id.tv_diskon_buku_checkout);
        tvHargaAwal = (TextView) findViewById(R.id.tv_harga_awal_checkout);
        tvHargaBuku = (TextView) findViewById(R.id.tv_harga_buku_checkout);

        // shared prefs
        sharedPrefs = new SharedPrefs(this);

        // user data
        user = sharedPrefs.getUser();
        userUpdated = sharedPrefs.getUserUpdated();

    }

    private void mainButton(){

        // back
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setValueShippingAddress(){
        if (sharedPrefs.getUser() == null){
            Intent intent = new Intent(CheckoutActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }

        if(userUpdated != null){
            edtNameCheckout.setText(userUpdated.getName());
            edtPhoneCheckout.setText(userUpdated.getPhone());
            edtEmailCheckout.setText(userUpdated.getEmail());

            if (userUpdated.getAddress() == null){
                edtAddressCheckout.setHint("Not set.");
            } else {
                edtAddressCheckout.setText(userUpdated.getAddress());
            }

            // returnkan boss, biar bawahnya gausah dieksekusi
            return;

        } else {
            edtNameCheckout.setText(user.getName());
            edtPhoneCheckout.setText(user.getPhone());
            edtEmailCheckout.setText(user.getEmail());

            if (user.getAddress() == null){
                edtAddressCheckout.setHint("Not set.");
            } else {
                edtAddressCheckout.setText(user.getAddress());
            }
        }
    }
}