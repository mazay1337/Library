package pro.sky.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.library.entity.BookCover;

import java.util.Optional;

public interface BookCoverRepository extends JpaRepository<BookCover, Long> {

    Optional<BookCover> findByBookId(Long bookId);

}
