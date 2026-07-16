package nl.novi.eindopdrachtbackendlibrary.repositories;

import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByCollectionItemNotEmpty();
    List<BookEntity> findByCollectionItemEmpty();
    List<AuthorEntity> findByGenre_Id(Long genreId);

    List<BookEntity> findByGenre(GenreEntity genre);
}
