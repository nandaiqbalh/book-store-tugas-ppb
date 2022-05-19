package com.nandaiqbalh.tugaspbb.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nandaiqbalh.tugaspbb.R;
import com.nandaiqbalh.tugaspbb.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.myViewHolder> {

    ArrayList<Book> dataHolder;

    public BookAdapter(ArrayList<Book> dataHolder) {
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

        holder.gambarBuku.setImageResource(dataHolder.get(position).getGambarBuku());
        holder.judulBuku.setText(dataHolder.get(position).getJudulBuku());
        holder.penulisBuku.setText(dataHolder.get(position).getPenulisBuku());
        holder.hargaBuku.setText(dataHolder.get(position).getHargaBuku());
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
