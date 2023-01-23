package com.libraryapplication;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UI extends Application {
    transient VBox displayedBooks;
    static UI ui;
    @Override
    public void start(Stage stage) {
        displayedBooks = new VBox();

        Scene scene = new Scene(displayedBooks ,500, 500);


        stage.setTitle("Library Application");

        stage.setScene(scene);
        stage.show();

        ui = this;
        Main.writeBooksToUI();
    }

    public void addBook(String title, String genre, int copies) {
        GridPane bookLayout = new GridPane();
        bookLayout.add(new Text("title: "+title),0,0);
        bookLayout.add(new Text("genre: "+genre),0,1);
        bookLayout.add(new Text("copies: "+copies),1,1);

        Platform.runLater(()->displayedBooks.getChildren().add(bookLayout));
    }

    public static UI getInstance() {
        return ui;
    }
    public static void main(String args[]) throws Exception {throw new Exception("this application must be run from the main method in class: 'Main'");}

}
