package com.github.argajuvi.models;

public class BookProduct extends Product {

    private final int publishYear;
    private final String author;

    public BookProduct(String name, int price, int publishYear, String author) {
        super(name, price);
        this.publishYear = publishYear;
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public String getAuthor() {
        return author;
    }

}
