package com.nandaiqbalh.tugaspbb.utils.checkout;

import com.nandaiqbalh.tugaspbb.model.Book;
import com.nandaiqbalh.tugaspbb.model.Transaction;

import java.util.ArrayList;

public class CheckoutResponse {
    private int success = 0;
    private String message;
    private ArrayList<Transaction> transaction = new ArrayList<>();


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

    public ArrayList<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(ArrayList<Transaction> transaction) {
        this.transaction = transaction;
    }
}
