package com.libraryapplication;

import javafx.application.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String args[]) {
        initConfigurations();

        Library.getInstance().initialize();

        Application.launch(UI.class,args);
    }

    public static void writeBooksToUI() {
        List<Book> books = Library.getInstance().getBooks();
        for(Book book : books) {
            UI.getInstance().addBook(book.getTitle(),book.getGenre(),book.getCopies());
        }
    }

    private static void initConfigurations() {
        try(FileInputStream stream = new FileInputStream("src/config.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            DataBaseUtil.setProperties(properties.get("db_url").toString(),
                                       properties.get("user").toString(),
                                       properties.get("pass").toString(),
                                       properties.get("ext").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}