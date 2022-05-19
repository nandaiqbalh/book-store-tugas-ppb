package com.nandaiqbalh.tugaspbb.model;

import java.io.Serializable;

public class Book implements Serializable {

    private String judulBuku;
    private String penulisBuku;
    private String hargaBuku;
    private int gambarBuku = 0;

    public Book(String judulBuku, String penulisBuku, String hargaBuku, int gambarBuku) {
        this.judulBuku = judulBuku;
        this.penulisBuku = penulisBuku;
        this.hargaBuku = hargaBuku;
        this.gambarBuku = gambarBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getPenulisBuku() {
        return penulisBuku;
    }

    public void setPenulisBuku(String penulisBuku) {
        this.penulisBuku = penulisBuku;
    }

    public String getHargaBuku() {
        return hargaBuku;
    }

    public void setHargaBuku(String hargaBuku) {
        this.hargaBuku = hargaBuku;
    }

    public int getGambarBuku() {
        return gambarBuku;
    }

    public void setGambarBuku(int gambarBuku) {
        this.gambarBuku = gambarBuku;
    }
}
