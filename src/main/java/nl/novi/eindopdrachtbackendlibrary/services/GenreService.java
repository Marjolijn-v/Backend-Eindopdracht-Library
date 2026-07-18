package nl.novi.eindopdrachtbackendlibrary.services;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.GenreDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final GenreDtoMapper genreDtoMapper;

    public GenreService(GenreRepository genreRepository, BookRepository bookRepository, GenreDtoMapper genreDtoMapper) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.genreDtoMapper = genreDtoMapper;
    }


    public List<GenreResponseDto> findAllGenres() {
        return genreDtoMapper.mapToDto(genreRepository.findAll());
    }

    public GenreResponseDto findGenreById(Long id)  {
        GenreEntity genreEntity = getGenreEntity(id);
        return genreDtoMapper.mapToDto(genreEntity);
    }

    public GenreResponseDto createGenre(GenreRequestDto genreModel) {
        GenreEntity genreEntity = genreDtoMapper.mapToEntity(genreModel);
        genreEntity = genreRepository.save(genreEntity);
        return genreDtoMapper.mapToDto(genreEntity);
    }

    public GenreResponseDto updateGenre(Long id, GenreRequestDto genreModel)  {
        GenreEntity existingGenreEntity = getGenreEntity(id);

        existingGenreEntity.setName(genreModel.getName());
        existingGenreEntity.setDescription(genreModel.getDescription());

        existingGenreEntity = genreRepository.save(existingGenreEntity);
        return genreDtoMapper.mapToDto(existingGenreEntity);
    }

    private GenreEntity getGenreEntity(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Genre " + id + " niet gevonden"));
    }


    @Transactional
    public void deleteGenre(Long id) {
        GenreEntity genre = getGenreEntity(id);

        List<BookEntity> booksWithGenre = bookRepository.findByGenre(genre);

        booksWithGenre.stream()
                .forEach(book -> book.setGenre(null));

        bookRepository.saveAll(booksWithGenre);
        genreRepository.deleteById(id);
    }

}
