package com.nandaiqbalh.tugaspbb.activity.book;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.helper.DatabaseHelper;
import com.nandaiqbalh.tugaspbb.home.HomeActivity;

public class CartActivity extends AppCompatActivity {

    public static CartActivity cartActivity;

    String[] daftar;
    ListView listViewCart;
    Menu menu;
    protected Cursor cursor;
    DatabaseHelper dbcenter;

    ImageView ivBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inisialisasi();

        cartActivity = this;
        dbcenter = new DatabaseHelper(this);
        RefreshList();

        mainButton();
    }

    private void inisialisasi(){
        listViewCart = (ListView) findViewById(R.id.cartListview);

        ivBackButton = (ImageView) findViewById(R.id.img_back);

    }

    private void mainButton(){

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM cart", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(0).toString();
        }

        listViewCart.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        listViewCart.setSelected(true);
        listViewCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; //.getItemAtPosition(arg2).toString();

                final CharSequence[] dialogitem = {"View Detail", "Delete Cart"};
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Option");

                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent i = new Intent(getApplicationContext(), DetailCartActivity.class);
                                i.putExtra("judulbuku", selection);
                                startActivity(i);
                                break;
                            case 1:
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from cart where judulbuku = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) listViewCart.getAdapter()).notifyDataSetInvalidated();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}