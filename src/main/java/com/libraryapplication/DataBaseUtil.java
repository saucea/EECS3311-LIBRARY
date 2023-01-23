package com.libraryapplication;


import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataBaseUtil {
    private static String db_url;
    private static String user;
    private static String pass;
    private static String ext;
    private static PGSimpleDataSource source;




    public static List<BookDTO> initializeLibrary() {
        List<BookDTO> rawBooks = new ArrayList<>();
        initDataSource();
        try(Connection connection = source.getConnection()) {
            Statement statement = connection.createStatement();
            //check that tables exist before trying to connect to them
            try{
                statement.executeQuery("select * from books"+ext);
            } catch(SQLException e) {
                createTablesWithRandomData();
            }
            //get book data with select statement
            ResultSet booksRaw = statement.executeQuery("select title,genre,description from books"+ext);
            //repeatedly get the next row in the result from the select statement and use the data to create a BookDto to be later made into a book
            while (booksRaw.next())
                rawBooks.add(new BookDTO(booksRaw.getString(1),
                                         booksRaw.getString(2),
                                         booksRaw.getString(3)));

            return rawBooks;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTablesWithRandomData() {
        try(Connection connection = source.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("create table if not exists books"+ext+"(" +
                    "id serial primary key," +
                    "title varchar(255)," +
                    "genre varchar(255)," +
                    "description varchar(10000));");
            //get all book data from static database just holding book data
            ResultSet rawBookData = statement.executeQuery("select * from RawBookData");

            String query = "insert into books"+ext+" (title,genre,description) values ";
            while(rawBookData.next()) {
                Random random = new Random();
                int num_copies = random.nextInt(1,6);
                String title = rawBookData.getString(1).replaceAll("'","");
                String genre = rawBookData.getString(2);
                String description = rawBookData.getString(3).replaceAll("'","");
                for (int i = 0; i < num_copies; i++)
                    query += "('" + title + "', '" + genre +"', '"+description + "'),\n";
            }
            query = query.substring(0,query.length()-2);
            query+=";";
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initDataSource() {
        source = new PGSimpleDataSource();
        source.setUser(user);
        source.setPassword(pass);
        source.setURL(db_url);
        source.setSslMode("require");

    }

    public static void setProperties(String db_url, String user, String pass, String ext) {
        DataBaseUtil.db_url = db_url;
        DataBaseUtil.user = user;
        DataBaseUtil.pass = pass;
        DataBaseUtil.ext = ext;
    }
}
