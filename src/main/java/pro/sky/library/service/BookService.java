package pro.sky.library.service;

import org.springframework.stereotype.Service;
import pro.sky.library.entity.Book;
import pro.sky.library.repository.BookRepository;

import java.util.Collection;
;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book findBook(long id) {
        return bookRepository.findById(id).get();
    }

    public Book editBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    public Collection<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
