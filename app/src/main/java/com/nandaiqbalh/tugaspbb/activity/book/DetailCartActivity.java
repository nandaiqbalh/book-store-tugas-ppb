package com.nandaiqbalh.tugaspbb.activity.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.helper.DatabaseHelper;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailCartActivity extends AppCompatActivity {

    ImageView ivGambarBuku;
    TextView tvJudulBuku, tvHargaBuku, tvDiskonBuku, tvAuthorBuku, tvKodeBuku, tvQuantityBuku, tvPageBuku, tvBahasaBuku;

    TextView tvHargaAwalCart;

    protected Cursor cursor;
    DatabaseHelper databaseHelper;

    // Toolbar
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cart);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inisialisasi();
        setValue();
    }

    private void inisialisasi(){
        ivGambarBuku = (ImageView) findViewById(R.id.iv_gambar_buku_cart);
        tvJudulBuku = (TextView) findViewById(R.id.tv_judul_buku_cart);
        tvHargaBuku = (TextView) findViewById(R.id.tv_harga_buku_cart);
        tvDiskonBuku = (TextView) findViewById(R.id.tv_diskon_buku_cart);
        tvAuthorBuku = (TextView) findViewById(R.id.tv_book_author_cart);
        tvKodeBuku = (TextView) findViewById(R.id.tv_book_code_cart);
        tvQuantityBuku = (TextView) findViewById(R.id.tv_book_quantity_cart);
        tvPageBuku = (TextView) findViewById(R.id.tv_book_page_cart);
        tvBahasaBuku = (TextView) findViewById(R.id.tv_book_language_cart);

        tvHargaAwalCart = (TextView) findViewById(R.id.tv_harga_awal_cart);

        databaseHelper = new DatabaseHelper(this);

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }

    private void setValue(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM cart WHERE judulbuku = '" +
                getIntent().getStringExtra("judulbuku") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);

            tvJudulBuku.setText(cursor.getString(0).toString());
            tvAuthorBuku.setText(cursor.getString(3).toString());
            tvKodeBuku.setText(cursor.getString(4).toString());
            tvQuantityBuku.setText(cursor.getString(5).toString());
            tvPageBuku.setText(cursor.getString(6).toString());
            tvBahasaBuku.setText(cursor.getString(7).toString());
            ivGambarBuku.setImageResource(Integer.parseInt(cursor.getString(8)));


            // diskon amount
            if (cursor.getString(2).equalsIgnoreCase("0")) {
                tvDiskonBuku.setVisibility(View.GONE);

                // harga dicoret
                tvHargaAwalCart.setVisibility(View.GONE);
                tvHargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(cursor.getString(1))));

            } else {
                double diskonPrize, sellingPrize, diskonFinal;

                try {
                    diskonPrize = Double.parseDouble(cursor.getString(2));
                } catch (NumberFormatException e) {
                    diskonPrize = 0;
                }

                try {
                    sellingPrize = Double.parseDouble(cursor.getString(1));
                } catch (NumberFormatException e) {
                    sellingPrize = 0;
                }


                diskonFinal = diskonPrize / sellingPrize * 100;
                int angkaSignifikan = 1;
                double tempDiskon = Math.pow(10, angkaSignifikan);
                double diskonTampil = (double) Math.round(diskonFinal*tempDiskon)/tempDiskon;

                tvDiskonBuku.setText(diskonTampil + "%");

                // harga dicoret
                tvHargaAwalCart.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(cursor.getString(1))));
                tvHargaAwalCart.setPaintFlags(tvHargaAwalCart.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                double hargaTampil = sellingPrize - diskonPrize;
                // harga setelah diskon yang tampil
                tvHargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Double.valueOf(hargaTampil)));

            }

            // set toolbar
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(cursor.getString(0));
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