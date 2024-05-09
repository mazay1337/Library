package pro.sky.library.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.library.entity.Book;
import pro.sky.library.service.BookService;

import java.util.Collection;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение информации о книге")
    public ResponseEntity<Book> getBookInfo(@PathVariable long id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @Operation(summary = "Создание книги")
    public Book create(Book book) {
        return bookService.createBook(book);
    }

    @PutMapping
    @Operation(summary = "Изменение книги")
    public ResponseEntity<Book> editBook(Book book) {
        Book bookFound = bookService.editBook(book);
        if (bookFound == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookFound);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление книги")
    public ResponseEntity<Book> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Нахождение книги")
    public ResponseEntity findBooks(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String author,
                                    @RequestParam(required = false) String namePart) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(bookService.findByName(name));
        }
        if (author != null && !author.isBlank()) {
            return ResponseEntity.ok(bookService.findByAuthor(author));
        }
        if (namePart != null && !namePart.isBlank()) {
            return ResponseEntity.ok(bookService.findBooksByNameContains(namePart));
        }
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
