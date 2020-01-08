package fr.d2factory.libraryapp.book;

import fr.d2factory.libraryapp.library.exceptions.BookNotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {
    private Map<ISBN, Book> availableBooks ;
    private Map<Book, LocalDate> borrowedBooks;

    public BookRepository() {
        availableBooks = new HashMap<>();
        borrowedBooks = new HashMap<>();
    }

    public void addBooks(List<Book> books){
        books.forEach(book -> availableBooks.put(book.isbn,book));
    }

    public Book findBook(long isbnCode) throws BookNotFoundException {
        ISBN isbn = new ISBN(isbnCode);
        if (!availableBooks.containsKey(isbn)){
            throw new BookNotFoundException();
        }else{
            return availableBooks.get(isbn);
        }
    }

    public Book findBorrowedBook(long isbnCode) throws BookNotFoundException {
        ISBN isbn = new ISBN(isbnCode);
        Book book = new Book("title","author", isbn);
        Book BookToReturn = new Book();
        if(borrowedBooks.keySet().contains(book)){
            for (Book book1 : borrowedBooks.keySet()) {
                if (book1.equals(book))
                    BookToReturn = book1;
            }
            return BookToReturn;
        }else {
            throw new BookNotFoundException();
        }
    }
    public void saveBookBorrow(Book book, LocalDate borrowedAt) throws BookNotFoundException {
        if (!availableBooks.containsKey(book.isbn)){
            throw new BookNotFoundException();
        }else {
            borrowedBooks.put(book,borrowedAt);
            availableBooks.remove(book.isbn);
        }
    }

    public LocalDate findBorrowedBookDate(Book book) throws BookNotFoundException {
        if (!borrowedBooks.containsKey(book)){
            throw new BookNotFoundException();
        }else{
            return borrowedBooks.get(book);
        }
    }

    public void returnBookBorrow(Book book) throws BookNotFoundException {
        if (!borrowedBooks.containsKey(book)){
            throw new BookNotFoundException();
        }else {
            availableBooks.put(book.isbn,book);
            borrowedBooks.remove(book);
        }
    }
}
