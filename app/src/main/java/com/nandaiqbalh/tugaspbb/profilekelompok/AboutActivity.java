package com.nandaiqbalh.tugaspbb.profilekelompok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.nandaiqbalh.tugaspbb.R;

public class AboutActivity extends AppCompatActivity {

    CardView cardViewSandra, cardViewNanda, cardViewAldo, cardViewFauzan, cardViewAnam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // panggil fungsinya disini
        inisialisasiVariable();

        // panggil lagi
        mainButton();

    }

    // buat fungsinya disini, biar di onCreate, kita tinggal manggil2 doang, jadinya codenya rapi
    // dan best practice
    private void inisialisasiVariable(){

        // sesuaikan nama id dengan yang dibuat tadi
        cardViewSandra = (CardView) findViewById(R.id.cardview_sandra);
        cardViewNanda = (CardView) findViewById(R.id.cardview_nanda);
        cardViewAldo = (CardView) findViewById(R.id.cardview_aldo);
        cardViewFauzan = (CardView) findViewById(R.id.cardview_fauzan);
        cardViewAnam = (CardView) findViewById(R.id.cardview_anam);
    }

    // bikin function button, biar tinggal manggi dan jadi lebih rapi
    private void mainButton(){

        cardViewSandra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // intent untuk pindah activity
                Intent intent = new Intent(AboutActivity.this, SandraDetailActiviy.class);
                startActivity(intent);

                // opsional (kalo mau finish, nanti setelah ke halaman detailm gabisa balik ke halaman main acticity)
                 finish();
            }
        });

        cardViewNanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, NandaDetailActivity.class);
                startActivity(intent);
                finish();

            }
        });

        cardViewAldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, AldoDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardViewAnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, AnamDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cardViewFauzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, FauzanDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
