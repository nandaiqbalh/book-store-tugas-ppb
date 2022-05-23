package com.nandaiqbalh.tugaspbb.model;

import java.io.Serializable;

public class Book implements Serializable {

    private String book_name;
    private String book_author;
    private String book_code;
    private String book_quantity;
    private String book_page;
    private String book_language;
    private String selling_price;
    private String discount_price;
    private String description;
    private int book_image;
    private int hot_deals;
    private int featured;
    private int special_offer;
    private int special_deal;
    private int status;

    public Book(String book_name, String book_author, String book_code, String book_quantity, String book_page, String book_language, String selling_price, String discount_price, int book_image) {
        this.book_name = book_name;
        this.book_author = book_author;
        this.book_code = book_code;
        this.book_quantity = book_quantity;
        this.book_page = book_page;
        this.book_language = book_language;
        this.selling_price = selling_price;
        this.discount_price = discount_price;
        this.book_image = book_image;
    }

    public Book() {
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_code() {
        return book_code;
    }

    public void setBook_code(String book_code) {
        this.book_code = book_code;
    }

    public String getBook_quantity() {
        return book_quantity;
    }

    public void setBook_quantity(String book_quantity) {
        this.book_quantity = book_quantity;
    }

    public String getBook_page() {
        return book_page;
    }

    public void setBook_page(String book_page) {
        this.book_page = book_page;
    }

    public String getBook_language() {
        return book_language;
    }

    public void setBook_language(String book_language) {
        this.book_language = book_language;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBook_image() {
        return book_image;
    }

    public void setBook_image(int book_image) {
        this.book_image = book_image;
    }

    public int getHot_deals() {
        return hot_deals;
    }

    public void setHot_deals(int hot_deals) {
        this.hot_deals = hot_deals;
    }

    public int getFeatured() {
        return featured;
    }

    public void setFeatured(int featured) {
        this.featured = featured;
    }

    public int getSpecial_offer() {
        return special_offer;
    }

    public void setSpecial_offer(int special_offer) {
        this.special_offer = special_offer;
    }

    public int getSpecial_deal() {
        return special_deal;
    }

    public void setSpecial_deal(int special_deal) {
        this.special_deal = special_deal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
