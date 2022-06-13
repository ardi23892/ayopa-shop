package com.github.argajuvi.models.product;

public class BookProduct extends Product {

    private final int publishYear;
    private final String author;

    public BookProduct(int ID, String name, int price, int publishYear, String author) {
        super(ID, name, price);
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
