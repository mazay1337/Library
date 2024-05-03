package pro.sky.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
