package pro.sky.library.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.library.entity.Book;
import pro.sky.library.service.BookService;

import java.util.Collection;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBookInfo(@PathVariable long id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public Book create(Book book) {
        return bookService.createBook(book);
    }

    @PutMapping
    public ResponseEntity<Book> editBook(Book book) {
        Book bookFound = bookService.editBook(book);
        if (bookFound == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookFound);
    }

    @DeleteMapping("{id}")
    public Book deleteBook(@PathVariable long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping
    public ResponseEntity<Collection<Book>> getAllBook() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
