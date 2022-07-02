package com.nandaiqbalh.tugaspbb.activity.book;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.activity.checkout.CheckoutActivity;
import com.nandaiqbalh.tugaspbb.activity.userprofile.ChangeProfileActivity;
import com.nandaiqbalh.tugaspbb.auth.SignInActivity;
import com.nandaiqbalh.tugaspbb.helper.DatabaseHelper;
import com.nandaiqbalh.tugaspbb.helper.SharedPrefs;
import com.nandaiqbalh.tugaspbb.model.Book;
import com.nandaiqbalh.tugaspbb.rest.ApiConfig;
import com.nandaiqbalh.tugaspbb.utils.checkout.CheckoutRequest;
import com.nandaiqbalh.tugaspbb.utils.userprofile.UserProfileRequest;
import com.nandaiqbalh.tugaspbb.utils.userprofile.UserProfileResponse;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBookActivity extends AppCompatActivity {


    ImageView ivGambarBuku;
    TextView tvJudulBuku, tvHargaBuku, tvAuthorBuku, tvKodeBuku, tvQuantityBuku, tvPageBuku, tvBahasaBuku;
    TextView tvHargaAwalDetail;
    TextView tvDiskonBuku;

    Button btnCheckout;

    // intent getExtra
    Gson gson = new Gson();
    Book book;

    // Toolbar
    Toolbar toolbar;

    DatabaseHelper databaseHelper;
    ImageView btnAddToCart;

    SharedPrefs sharedPrefs;

    UserProfileRequest userProfileRequest;
    CheckoutRequest checkoutRequest;

    // alert dialog
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // inisialisasi variabel
        inisialisasi();

        // get info produk
        getInformasiProduk();

        // button triggered
        mainButton();

        // get user profile
        userProfileRequest.setId(sharedPrefs.getInt(sharedPrefs.getId()));
        getUserProfile(userProfileRequest);

    }

    private void inisialisasi(){
        ivGambarBuku = (ImageView) findViewById(R.id.iv_gambar_buku_detail);
        tvJudulBuku = (TextView) findViewById(R.id.tv_judul_buku_detail);
        tvHargaBuku = (TextView) findViewById(R.id.tv_harga_buku_detail);
        tvAuthorBuku = (TextView) findViewById(R.id.tv_book_author_detail);
        tvKodeBuku = (TextView) findViewById(R.id.tv_book_code_detail);
        tvQuantityBuku = (TextView) findViewById(R.id.tv_book_quantity_detail);
        tvPageBuku = (TextView) findViewById(R.id.tv_book_page_detail);
        tvBahasaBuku = (TextView) findViewById(R.id.tv_book_language_detail);

        tvDiskonBuku = (TextView) findViewById(R.id.tv_diskon_buku_detail);
        tvHargaAwalDetail = (TextView) findViewById(R.id.tv_harga_awal_detail);

        // button
        btnCheckout = (Button) findViewById(R.id.btn_checkout_detail);

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        databaseHelper = new DatabaseHelper(this);
        btnAddToCart = (ImageView) findViewById(R.id.btn_add_to_cart_detail);

        sharedPrefs = new SharedPrefs(this);

        userProfileRequest = new UserProfileRequest();

        checkoutRequest = new CheckoutRequest();

        // alert dialog
        builder = new AlertDialog.Builder(this);

    }

    private void mainButton(){

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("Confirmation")
                        .setMessage("Are you sure to add this book to your cart?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // ambil
                                ambilIntent();

                                if (book != null){
                                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                                    db.execSQL("insert into cart(judulbuku, hargabuku, diskonbuku, authorbuku, kodebuku, qtybuku, halamanbuku, bahasabuku, gambarbuku) values('" +
                                            book.getBook_name() + "','" +
                                            book.getSelling_price() + "','" +
                                            book.getDiscount_price() + "','" +
                                            book.getBook_author() + "','" +
                                            book.getBook_code() + "','" +
                                            book.getBook_quantity() + "','" +
                                            book.getBook_page() + "','" +
                                            book.getBook_language() + "','" +
                                            book.getBook_image()  +"')");
                                    Toast.makeText(getApplicationContext(), "Add to chart successfully!", Toast.LENGTH_LONG).show();
//                    CartActivity.cartActivity.RefreshList();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPrefs.getStatusLogin() == true){

                    // ke checkout form activity
                    Intent intent = new Intent(DetailBookActivity.this, CheckoutActivity.class);
                    String stringData = gson.toJson(book, Book.class); // cast data Buku ke dalam string
                    intent.putExtra("extra", stringData);
                    startActivity(intent);


                } else {

                    // ke login activity
                    Intent intent = new Intent(DetailBookActivity.this, SignInActivity.class);
                    Toast.makeText(getApplicationContext(), "You need to Sign In first!", Toast.LENGTH_LONG).show();
                    startActivity(intent);

                }
            }
        });

    }

    private void ambilIntent(){
        String dataBuku = getIntent().getStringExtra("extra"); // ambil value dari intent
        book = gson.fromJson(dataBuku, Book.class); // cast dari bentuk String ke bentuk Object Produk
    }

    private void getInformasiProduk(){

        ambilIntent();

        // set value
        if (book != null){
            tvJudulBuku.setText(book.getBook_name());
            tvHargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(book.getSelling_price())));
            tvAuthorBuku.setText(book.getBook_author());
            tvKodeBuku.setText(book.getBook_code());
            tvQuantityBuku.setText(book.getBook_quantity());
            tvPageBuku.setText(book.getBook_page());
            tvBahasaBuku.setText(book.getBook_language());

            // diskon amount
            if (book.getDiscount_price().equalsIgnoreCase("0")) {
                tvDiskonBuku.setVisibility(View.GONE);

                // harga dicoret
                tvHargaAwalDetail.setVisibility(View.GONE);
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

                tvDiskonBuku.setText(diskonTampil + "%");

                // harga dicoret
                tvHargaAwalDetail.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(book.getSelling_price())));
                tvHargaAwalDetail.setPaintFlags(tvHargaAwalDetail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

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
                    .into(ivGambarBuku);

            // set toolbar
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(book.getBook_name());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void getUserProfile(UserProfileRequest userProfileRequest){

        if (sharedPrefs.getStatusLogin() == true){

            // do some networking
            Call<UserProfileResponse> userProfileResponseCall = ApiConfig.getService().getProfile(userProfileRequest);
            userProfileResponseCall.enqueue(new Callback<UserProfileResponse>() {

                @Override
                public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {

                    if (response.isSuccessful()) {

                        UserProfileResponse respon = response.body();

                        // ambil user untuk menampilkan data user terbaru ke dalam interface
                        sharedPrefs.setUserUpdated(respon.getUser()); // manggil kembalian field user secara langsung

                        if ( respon.getSuccess() == 1) {

                            checkoutRequest.setUser_id(respon.getUser().getId());
                            checkoutRequest.setUser_email(respon.getUser().getEmail());
                            checkoutRequest.setUser_phone(respon.getUser().getPhone());
                            if (respon.getUser().getAddress()!= null){
                                checkoutRequest.setUser_address(respon.getUser().getAddress());
                            }
                            checkoutRequest.setBook_image(book.getBook_image());
                            checkoutRequest.setBook_name(book.getBook_name());
                            checkoutRequest.setBook_author(book.getBook_author());
                            checkoutRequest.setBook_code(book.getBook_code());
                            checkoutRequest.setBook_quantity(book.getBook_quantity());
                            checkoutRequest.setBook_language(book.getBook_language());
                            checkoutRequest.setSelling_price(book.getSelling_price());
                            checkoutRequest.setDiscount_price(book.getDiscount_price());

                            // total qty dan total price blom di set

                        } else {
                            // gagal

                        }

                    }

                }

                @Override
                public void onFailure(Call<UserProfileResponse> call, Throwable t) {

                    String message = t.getLocalizedMessage();
                    Toast.makeText(DetailBookActivity.this, message, Toast.LENGTH_LONG).show();
                }


            });

        } else {
            // yaudah gabisa checkout nanti
        }
    }

}