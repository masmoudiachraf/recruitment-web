package fr.d2factory.libraryapp.book;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A simple representation of a book
 */

public class Book {
    String title;
    String author;
    ISBN isbn;

    @JsonCreator
    public Book(@JsonProperty("title") String title, @JsonProperty("author") String author, @JsonProperty("isbn") ISBN isbn){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    public void setIsbn(ISBN isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
         return 5698842;
    }

    @Override
    public boolean equals(Object obj) {
        Book book = (Book)obj;
        return book.isbn.equals(this.isbn);
    }
}
