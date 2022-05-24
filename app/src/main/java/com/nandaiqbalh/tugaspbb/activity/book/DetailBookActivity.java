package com.nandaiqbalh.tugaspbb.activity.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nandaiqbalh.tugaspbb.R;
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
    }

    private void getInformasiProduk(){

        String dataBuku = getIntent().getStringExtra("extra"); // ambil value dari intent
        book = gson.fromJson(dataBuku, Book.class); // cast dari bentuk String ke bentuk Object Produk

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