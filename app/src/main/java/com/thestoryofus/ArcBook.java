package com.thestoryofus;

import java.time.LocalDateTime;

public class ArcBook {
    private int bookID;
    private Book book;
    private String addTime;

    public ArcBook(int bookID, Book book, String addTime) {
        this.bookID = bookID;
        this.book = book;
        this.addTime = addTime;
    }

    public Book getBook() {
        return book;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setBookID(int bookID) { this.bookID = bookID; }
}
