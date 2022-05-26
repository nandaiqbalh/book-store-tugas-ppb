package com.nandaiqbalh.tugaspbb.activity.checkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.activity.book.DetailBookActivity;
import com.nandaiqbalh.tugaspbb.activity.userprofile.ChangeProfileActivity;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.auth.SignUpActivity;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;
import com.nandaiqbalh.tugaspbb.model.Book;
import com.nandaiqbalh.tugaspbb.model.User;
import com.nandaiqbalh.tugaspbb.rest.ApiConfig;
import com.nandaiqbalh.tugaspbb.utils.checkout.CheckoutRequest;
import com.nandaiqbalh.tugaspbb.utils.checkout.CheckoutResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    ImageButton ibBack;

    // shipping address
    EditText edtNameCheckout, edtEmailCheckout, edtPhoneCheckout, edtAddressCheckout;

    // button
    Button btnCheckoutNow;

    // buku
    ImageView imgBukuCheckout;
    TextView tvDiskonBukuCheckout, tvJudulBuku, tvPenulisBuku, tvHargaAwal, tvHargaBuku, tvErrorCheckout;

    // shared preferences
    SharedPrefs sharedPrefs;

    // User Data
    User user, userUpdated;

    // intent getExtra
    Gson gson = new Gson();
    Book book;

    // Checkout Request and Response
    CheckoutRequest checkoutRequest;
    CheckoutResponse checkoutResponse;

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

        // setvalue for book
        setValueBook();

    }

    private void inisialisasi(){

        // img button
        ibBack = (ImageButton) findViewById(R.id.btn_back_from_chekout);

        // shipping address
        edtNameCheckout = (EditText) findViewById(R.id.tv_name_checkout);
        edtEmailCheckout = (EditText) findViewById(R.id.tv_email_checkout);
        edtPhoneCheckout = (EditText) findViewById(R.id.tv_phone_checkout);
        edtAddressCheckout = (EditText) findViewById(R.id.tv_address_checkout);
        tvErrorCheckout = (TextView) findViewById(R.id.tv_errortext_checkout);

        // button
        btnCheckoutNow = (Button) findViewById(R.id.btn_checkout_now);

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

        // checkout request and response
        checkoutRequest = new CheckoutRequest();
        checkoutResponse = new CheckoutResponse();

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

        btnCheckoutNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // do networking for checkout
                setValueForCheckout();

                checkoutNow(checkoutRequest);
            }
        });
    }

    private void checkoutNow(CheckoutRequest checkoutRequest){

        Call<CheckoutResponse> checkoutResponseCall = ApiConfig.getService().checkoutBook(checkoutRequest);
        checkoutResponseCall.enqueue(new Callback<CheckoutResponse>() {
            @Override
            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {

                checkoutResponse = response.body();

                if (checkoutResponse.getSuccess() == 1){

                    // pindah ke home activity + menampilkan toast
                    Toast.makeText(CheckoutActivity.this, "Successfully to checkout your book! ", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(CheckoutActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finishAffinity();

                }

            }

            @Override
            public void onFailure(Call<CheckoutResponse> call, Throwable t) {

            }
        });

    }

    private void setValueForCheckout(){

        // validasi form shipping address
        validasiFormShippingAddress();

        // atur value shiping address
        if(userUpdated != null){
            checkoutRequest.setUser_id(userUpdated.getId());
        } else {
            checkoutRequest.setUser_id(user.getId());
        }
        checkoutRequest.setUser_name(edtNameCheckout.getText().toString().trim());
        checkoutRequest.setUser_email(edtEmailCheckout.getText().toString().trim());
        checkoutRequest.setUser_phone(edtPhoneCheckout.getText().toString().trim());
        checkoutRequest.setUser_address(edtAddressCheckout.getText().toString().trim());

        // kirim value buku
        checkoutRequest.setBook_image(book.getBook_image());
        checkoutRequest.setBook_name(book.getBook_name());
        checkoutRequest.setBook_author(book.getBook_author());
        checkoutRequest.setBook_code(book.getBook_code());
        checkoutRequest.setBook_page(book.getBook_page());
        checkoutRequest.setBook_language(book.getBook_language());
        checkoutRequest.setSelling_price(book.getSelling_price());
        checkoutRequest.setDiscount_price(book.getDiscount_price());

    }

    private void validasiFormShippingAddress(){

        String emailInput = edtEmailCheckout.getText().toString().trim(); // untuk validasi email
        int phoneInput = edtPhoneCheckout.getText().length(); // untuk validasi nomor telepon

        if (edtNameCheckout.getText().toString().isEmpty()) {
            tvErrorCheckout.setText("Name field is required!");
            edtNameCheckout.requestFocus();
            return;
        } else if (emailInput.isEmpty()) {
            tvErrorCheckout.setText("Email field is required!");
            edtEmailCheckout.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            tvErrorCheckout.setText("The email address you entered is not valid!");
            edtEmailCheckout.requestFocus();
            return;
        } else if (edtPhoneCheckout.getText().toString().isEmpty()) {
            tvErrorCheckout.setText("Phone number field is required!");
            edtPhoneCheckout.requestFocus();
            return;
        } else if (phoneInput < 10 || phoneInput > 13) {
            tvErrorCheckout.setText("The phone number you entered is not valid!");
            edtPhoneCheckout.requestFocus();
            return;
        } else if (edtAddressCheckout.getText().toString().isEmpty()){
            tvErrorCheckout.setText("Address field is required!");
            edtAddressCheckout.requestFocus();
            return;
        }
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

    private void ambilIntent(){
        String dataBuku = getIntent().getStringExtra("extra"); // ambil value dari intent
        book = gson.fromJson(dataBuku, Book.class); // cast dari bentuk String ke bentuk Object Produk
    }

    private void setValueBook(){
        ambilIntent();

        // set value
        if (book != null){
            tvJudulBuku.setText(book.getBook_name());
            tvHargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(book.getSelling_price())));
            tvPenulisBuku.setText(book.getBook_author());

            // diskon amount
            if (book.getDiscount_price().equalsIgnoreCase("0")) {
                tvDiskonBukuCheckout.setVisibility(View.GONE);

                // harga dicoret
                tvHargaAwal.setVisibility(View.GONE);
                tvHargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(book.getSelling_price())));

            } else {
                double diskonPrize, sellingPrize, diskonFinal;

                try {
                    diskonPrize = Double.parseDouble(book.getDiscount_price());
                } catch (NumberFormatException e) {
                    diskonPrize = 0;
                }

                try {
                    sellingPrize = Double.parseDouble(book.getSelling_price());
                } catch (NumberFormatException e) {
                    sellingPrize = 0;
                }


                diskonFinal = diskonPrize / sellingPrize * 100;
                int angkaSignifikan = 1;
                double tempDiskon = Math.pow(10, angkaSignifikan);
                double diskonTampil = (double) Math.round(diskonFinal*tempDiskon)/tempDiskon;

                tvDiskonBukuCheckout.setText(diskonTampil + "%");

                // harga dicoret
                tvHargaAwal.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(book.getSelling_price())));
                tvHargaAwal.setPaintFlags(tvHargaAwal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                double hargaTampil = sellingPrize - diskonPrize;
                // harga setelah diskon yang tampil
                tvHargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Double.valueOf(hargaTampil)));

            }

            // Gambar
            int imageURL =  book.getBook_image();
            Picasso.get()
                    .load(imageURL)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(imgBukuCheckout);

        }
    }
}