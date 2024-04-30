package pro.sky.library.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.library.entity.Book;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class BookService {

    private Map<Long, Book> books = new HashMap<>();
    private long lastId = 0;

    public Book createBook(Book book) {
        book.setId(++lastId);
        books.put(lastId, book);
        return book;
    }

    public Book findBook(long id) {
        return books.get(id);
    }

    public Book editBook(Book book) {
        if (books.containsKey(book.getId())) {
            books.put(book.getId(), book);
            return book;
        }
        return null;
    }

    public Book deleteBook(long id) {
        return books.remove(id);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }
}
