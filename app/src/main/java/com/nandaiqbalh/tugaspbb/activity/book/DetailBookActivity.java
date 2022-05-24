package com.nandaiqbalh.tugaspbb.activity.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.helper.DatabaseHelper;
import com.nandaiqbalh.tugaspbb.model.Book;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailBookActivity extends AppCompatActivity {


    ImageView ivGambarBuku;
    TextView tvJudulBuku, tvHargaBuku, tvAuthorBuku, tvKodeBuku, tvQuantityBuku, tvPageBuku, tvBahasaBuku;

    // intent getExtra
    Gson gson = new Gson();
    Book book;

    // Toolbar
    Toolbar toolbar;

    DatabaseHelper databaseHelper;
    ImageView btnAddToCart;

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

    }

    private void inisialisasi(){
        ivGambarBuku = (ImageView) findViewById(R.id.iv_gambar_buku_detail);
        tvJudulBuku = (TextView) findViewById(R.id.tv_judul_buku_detail);
        tvHargaBuku = (TextView) findViewById(R.id.tv_harga_book_detail);
        tvAuthorBuku = (TextView) findViewById(R.id.tv_book_author_detail);
        tvKodeBuku = (TextView) findViewById(R.id.tv_book_code_detail);
        tvQuantityBuku = (TextView) findViewById(R.id.tv_book_quantity_detail);
        tvPageBuku = (TextView) findViewById(R.id.tv_book_page_detail);
        tvBahasaBuku = (TextView) findViewById(R.id.tv_book_language_detail);

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        databaseHelper = new DatabaseHelper(this);
        btnAddToCart = (ImageView) findViewById(R.id.btn_add_to_cart_detail);

    }

    private void mainButton(){

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ambil
                ambilIntent();

                if (book != null){

                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    db.execSQL("insert into cart(judulbuku, hargabuku, authorbuku, kodebuku, qtybuku, halamanbuku, bahasabuku, gambarbuku) values('" +
                            book.getBook_name() + "','" +
                            book.getSelling_price() + "','" +
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
}