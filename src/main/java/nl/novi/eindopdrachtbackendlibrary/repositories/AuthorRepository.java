package nl.novi.eindopdrachtbackendlibrary.repositories;

import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    List<AuthorEntity> findAuthorsByBooksId(@Param("bookId") Long bookId);
}
