package com.nandaiqbalh.tugaspbb.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.activity.book.DetailBookActivity;
import com.nandaiqbalh.tugaspbb.model.Book;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myViewHolder> {

    Activity activity;
    ArrayList<Book> dataHolder;

    // putString
    Gson gson = new Gson();
    Book book;

    public BookAdapter(Activity activity, ArrayList<Book> dataHolder) {
        this.activity = activity;
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_home, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.judulBuku.setText(dataHolder.get(position).getBook_name());
        holder.penulisBuku.setText(dataHolder.get(position).getBook_author());
        holder.hargaAwalBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(dataHolder.get(position).getSelling_price())));

        if (dataHolder.get(position).getDiscount_price().equalsIgnoreCase("0")) {
            holder.diskonBuku.setVisibility(View.GONE);

            // harga dicoret
            holder.hargaAwalBuku.setVisibility(View.GONE);
            holder.hargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(dataHolder.get(position).getSelling_price())));

        } else {
            double diskonPrize, sellingPrize, diskonFinal;

            try {
                diskonPrize = Double.parseDouble(dataHolder.get(position).getDiscount_price());
            } catch (NumberFormatException e) {
                diskonPrize = 0;
            }

            try {
                sellingPrize = Double.parseDouble(dataHolder.get(position).getSelling_price());
            } catch (NumberFormatException e) {
                sellingPrize = 0;
            }


            diskonFinal = diskonPrize / sellingPrize * 100;
            int angkaSignifikan = 1;
            double tempDiskon = Math.pow(10, angkaSignifikan);
            double diskonTampil = (double) Math.round(diskonFinal*tempDiskon)/tempDiskon;

            holder.diskonBuku.setText(diskonTampil + "%");

            // harga dicoret
            holder.hargaAwalBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(dataHolder.get(position).getSelling_price())));
            holder.hargaAwalBuku.setPaintFlags(holder.hargaAwalBuku.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            double hargaTampil = sellingPrize - diskonPrize;
            // harga setelah diskon yang tampil
            holder.hargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Double.valueOf(hargaTampil)));
        }

        int imageURL = dataHolder.get(position).getBook_image();
        Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.gambarBuku);

        holder.layoutBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailBookActivity.class);

                String stringData = gson.toJson(dataHolder.get(position), Book.class); // cast data Buku ke dalam string
                intent.putExtra("extra", stringData);

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView gambarBuku;
        TextView judulBuku, penulisBuku, hargaBuku, hargaAwalBuku;
        CardView layoutBuku;
        TextView diskonBuku;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarBuku = itemView.findViewById(R.id.img_gambar_buku);
            judulBuku = itemView.findViewById(R.id.tv_judul_buku);
            penulisBuku = itemView.findViewById(R.id.tv_penulis_buku);
            hargaBuku = itemView.findViewById(R.id.tv_harga_buku);
            hargaAwalBuku = itemView.findViewById(R.id.tv_harga_awal);

            diskonBuku = itemView.findViewById(R.id.tv_diskon_buku);
            layoutBuku = itemView.findViewById(R.id.cv_layout_book);
        }
    }
}
