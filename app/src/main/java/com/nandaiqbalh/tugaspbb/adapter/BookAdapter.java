package com.nandaiqbalh.tugaspbb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.model.Book;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myViewHolder> {

    Activity activity;
    ArrayList<Book> dataHolder;

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
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        holder.judulBuku.setText(dataHolder.get(position).getBook_name());
        holder.penulisBuku.setText(dataHolder.get(position).getBook_author());
        holder.hargaBuku.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(Integer.valueOf(dataHolder.get(position).getSelling_price())));

        int imageURL = dataHolder.get(position).getBook_image();
        Picasso.get()
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(holder.gambarBuku);
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView gambarBuku;
        TextView judulBuku, penulisBuku, hargaBuku;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarBuku = itemView.findViewById(R.id.img_gambar_buku);
            judulBuku = itemView.findViewById(R.id.tv_judul_buku);
            penulisBuku = itemView.findViewById(R.id.tv_penulis_buku);
            hargaBuku = itemView.findViewById(R.id.tv_harga_buku);

        }
    }
}
