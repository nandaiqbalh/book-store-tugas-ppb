package com.nandaiqbalh.tugaspbb.activity.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.helper.DatabaseHelper;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailCartActivity extends AppCompatActivity {

    ImageView ivGambarBuku;
    TextView tvJudulBuku, tvHargaBuku, tvAuthorBuku, tvKodeBuku, tvQuantityBuku, tvPageBuku, tvBahasaBuku;

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
        tvHargaBuku = (TextView) findViewById(R.id.tv_harga_book_cart);
        tvAuthorBuku = (TextView) findViewById(R.id.tv_book_author_cart);
        tvKodeBuku = (TextView) findViewById(R.id.tv_book_code_cart);
        tvQuantityBuku = (TextView) findViewById(R.id.tv_book_quantity_cart);
        tvPageBuku = (TextView) findViewById(R.id.tv_book_page_cart);
        tvBahasaBuku = (TextView) findViewById(R.id.tv_book_language_cart);

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
            tvAuthorBuku.setText(cursor.getString(2).toString());
            tvHargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(cursor.getString(1))));
            tvKodeBuku.setText(cursor.getString(3).toString());
            tvQuantityBuku.setText(cursor.getString(4).toString());
            tvPageBuku.setText(cursor.getString(5).toString());
            tvBahasaBuku.setText(cursor.getString(6).toString());
            ivGambarBuku.setImageResource(Integer.parseInt(cursor.getString(7)));


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