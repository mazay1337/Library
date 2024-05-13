package pro.sky.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.library.entity.Book;

import java.util.Collection;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByNameIgnoreCase(String name);

    Collection<Book> findBooksByAuthorContainsIgnoreCase(String author);

    Collection<Book> findBooksByNameContainsIgnoreCase(String part);

}