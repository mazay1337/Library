package pro.sky.library.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.result.internal.OutputsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.library.entity.Book;
import pro.sky.library.entity.BookCover;
import pro.sky.library.service.BookCoverService;
import pro.sky.library.service.BookService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookCoverService bookCoverService;
    private BookService bookService;

    public BookController(BookService bookService, BookCoverService bookCoverService) {
        this.bookService = bookService;
        this.bookCoverService = bookCoverService;
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

    @PostMapping(value = "{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загружаем обложку книги")
    public ResponseEntity<String> uploadCover(@PathVariable long id, @RequestParam MultipartFile cover) throws IOException {
        if (cover.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too biig");
        }

        bookCoverService.uploadCover(id, cover);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/cover/preview")
    @Operation(summary = "Возвращает превьюшку")
    public ResponseEntity<byte[]> downloadCover(@PathVariable long id) {
        BookCover bookCover = bookCoverService.findBookCover(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(bookCover.getMediaType()));
        headers.setContentLength(bookCover.getPreview().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bookCover.getPreview());
    }

    @GetMapping(value = "/{id}/cover")
    @Operation(summary = "Возвращает оригинал обложки")
    public void downloadCover(@PathVariable Long id, HttpServletResponse response) throws IOException {
        BookCover bookCover = bookCoverService.findBookCover(id);

        Path path = Path.of(bookCover.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setContentType(bookCover.getMediaType());
            response.setContentLength((int) bookCover.getFileSize());
            is.transferTo(os);
        }
    }
}