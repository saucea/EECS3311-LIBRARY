package com.libraryapplication;

//data transfer object for class book. This is to be able to use information from a book without having access to the functionalities of a book
public record BookDTO(String title, String genre, String description) {}
