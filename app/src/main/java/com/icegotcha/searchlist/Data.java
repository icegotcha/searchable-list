package com.icegotcha.searchlist;

/**
 * Created by icegotcha on 3/9/2560.
 */

public class Data {
    private int id;
    private String bookname,
            author,
            publisher,
            translator,
            date;
    private float price;

    public Data(int id, String bookname, String author, String publisher, String translator, String date, float price) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.publisher = publisher;
        this.translator = translator;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTranslator() {
        return translator;
    }

    public String getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }
}
