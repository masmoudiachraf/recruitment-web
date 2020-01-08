package fr.d2factory.libraryapp.library;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import fr.d2factory.libraryapp.library.exceptions.BookNotFoundException;
import fr.d2factory.libraryapp.library.exceptions.HasLateBooksException;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Do not forget to consult the README.md :)
 */
public class LibraryTest {
    private Library library ;
    private BookRepository bookRepository;
    private static List<Book> books;


    @BeforeEach
    void setup() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        File booksJson = new File("src/test/resources/books.json");
        try{
            books = mapper.readValue(booksJson, new TypeReference<List<Book>>() {
            });
        }catch (Exception e){
            System.out.println("Error on reading books Json");
            System.out.println(e.getMessage());
        }

        bookRepository = new BookRepository();
        bookRepository.addBooks(books);
        library = new TownsvilleLibrary(bookRepository);
    }

    @Test
    void member_can_borrow_a_book_if_book_is_available(){
        Student member = new Student();
        library.borrowBook(46578964513L,member, LocalDate.now());
        library.borrowBook(465964513L,member, LocalDate.now());
    }

    @Test
    void borrowed_book_is_no_longer_available(){
        Student student = new Student();
        Resident resident = new Resident();
        library.borrowBook(46578964513L,student, LocalDate.now());
        library.borrowBook(46578964513L,resident, LocalDate.now());
    }

    @Test
    void residents_are_taxed_10cents_for_each_day_they_keep_a_book(){
        Resident resident = new Resident();
        library.borrowBook(46578964513L,resident, LocalDate.now().minusDays(50L));
        try {
            library.returnBook(bookRepository.findBorrowedBook(46578964513L),resident);
        } catch (BookNotFoundException e) {
            System.out.println("Book not borrowed");
        }
    }

    @Test
    void students_pay_10_cents_the_first_30days(){
        Student student = new Student();
        library.borrowBook(46578964513L,student, LocalDate.now().minusDays(40L));
        student.setLate(true);
        try {
            library.returnBook(bookRepository.findBorrowedBook(46578964513L),student);
        } catch (BookNotFoundException e) {
            System.out.println("Book not borrowed");
        }
    }

    @Test
    void students_in_1st_year_are_not_taxed_for_the_first_15days(){
        Student student = new Student();
        student.setFirstYearStudent(true);
        library.borrowBook(46578964513L,student, LocalDate.now().minusDays(40L));
        try {
            library.returnBook(bookRepository.findBorrowedBook(46578964513L),student);
        } catch (BookNotFoundException e) {
            System.out.println("Book not borrowed");
        }
    }
    
    @Test
    void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days(){
        Resident resident = new Resident();
        library.borrowBook(46578964513L,resident, LocalDate.now().minusDays(70L));
        resident.setLate(true);
        try {
            library.returnBook(bookRepository.findBorrowedBook(46578964513L),resident);
        } catch (BookNotFoundException e) {
            System.out.println("Book not borrowed");
        }
    }

    @Test
    void members_cannot_borrow_book_if_they_have_late_books(){
        Member member = new Student();
        member.setLate(true);
        try {
            library.borrowBook(46578964513L,member, LocalDate.now());
        } catch (HasLateBooksException e) {
            System.out.println("this member: cannot borrow any book , this member is Late");
        }
    }
}
