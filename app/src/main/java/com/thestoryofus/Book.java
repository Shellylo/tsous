package com.thestoryofus;

public class Book {

    enum b_langs {
        HEBREW,
        ENGLISH
    }

    enum b_genres {
        FANTASY,
        SCIENCE_FICTION,
        CRIME,
        DRAMA,
        HORROR,
        MYSTERY,
        POETRY,
        ROMANCE,
        THRILLER,
        YOUNG_ADULT,
        ADVENTURE,
        HISTORICAL_FICTION,
        REALISTIC,
        BIOGRAPHY,
        AUTOBIOGRAPHY
    }

    private String name;
    private String author;
    private int language;
    private int publishYear;
    private int genre;
    private boolean isAvailable;
    private boolean isRead;

    public Book(String name, String author, int language, int publishYear, int genre, boolean isAvailable, boolean isRead) {
        this.name = name;
        this.author = author;
        this.language = language;
        this.publishYear = publishYear;
        this.genre = genre;
        this.isAvailable = isAvailable;
        this.isRead = isRead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenere(int genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
