package nl.novi.eindopdrachtbackendlibrary.repositories;

import nl.novi.eindopdrachtbackendlibrary.entities.BookImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookImageRepository extends JpaRepository<BookImageEntity, Long> {
    List<BookImageEntity> findByBookId(Long bookId);
    Optional<BookImageEntity> findByIdAndBookId(Long id, Long bookId);
}
