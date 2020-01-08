package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.library.exceptions.BookNotFoundException;
import fr.d2factory.libraryapp.library.exceptions.HasLateBooksException;
import fr.d2factory.libraryapp.member.Member;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

public class TownsvilleLibrary implements Library{

    private BookRepository bookRepository;

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public TownsvilleLibrary(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException {
        if (member.isLate())
            throw new HasLateBooksException();
        else {
            Book borrowedBook = new Book();
            try {
                borrowedBook = bookRepository.findBook(isbnCode);
                bookRepository.saveBookBorrow(borrowedBook,borrowedAt);
            } catch (BookNotFoundException e) {
                System.out.println("Book not available");
            }
            return borrowedBook;
        }
    }

    @Override
    public void returnBook(Book book, Member member) {
        LocalDate today = LocalDate.now();
        try {
            long daysBetween = DAYS.between(bookRepository.findBorrowedBookDate(book), today);
            member.payBook(new Long(daysBetween).intValue());
            bookRepository.returnBookBorrow(book);
        } catch (BookNotFoundException e) {
            System.out.println("Book not borrowed");
        }
    }
}
