package com.libraryapplication;

public class Book {
    private String title;
    private String genre;
    private String description;

    private int copies;


    public Book(BookDTO rawBook, int copies) {
        title = rawBook.title();
        genre = rawBook.genre();
        description = rawBook.description();
        this.copies = copies;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getGenre() {
        return genre;
    }
    public int getCopies() {
        return copies;
    }

}
