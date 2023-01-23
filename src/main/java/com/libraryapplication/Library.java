package com.libraryapplication;

import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private static final Library LIBRARY = new Library();
    private Library(){/*singleton class*/}
    private List<Book> books;

    public static Library getInstance() {
        return LIBRARY;
    }

    public void initialize() {
                // get list of BookDtos from the database utils that include multiple copies of the same book
        books = DataBaseUtil.initializeLibrary().stream()
                //create map from above of book titles to books in the above list that have that title.
                .collect(Collectors.groupingBy(BookDTO::title))
                .values().stream()
                //create a list of books based on the above map making a single book and using the amount of duplicates
                //as the count of these books in the library
                .map(value->new Book(value.get(0),value.size())).collect(Collectors.toList());
    }

    public List<Book> getBooks() {
        return books;
    }
}
