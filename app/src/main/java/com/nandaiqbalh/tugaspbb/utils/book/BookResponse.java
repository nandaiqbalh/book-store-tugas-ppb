package com.nandaiqbalh.tugaspbb.utils.book;

import com.nandaiqbalh.tugaspbb.model.Book;

import java.util.ArrayList;

public class BookResponse {

    private int success = 0;
    private String message;
    private ArrayList<Book> books = new ArrayList<>();

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
