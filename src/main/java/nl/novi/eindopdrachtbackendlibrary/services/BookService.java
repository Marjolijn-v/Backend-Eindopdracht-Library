package nl.novi.eindopdrachtbackendlibrary.services;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.BookDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.AuthorRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository,
                       BookDtoMapper bookDtoMapper,
                       AuthorRepository authorRepository,
                       GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.bookDtoMapper = bookDtoMapper;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    public List<BookResponseDto> findAllBooks() {
        return bookDtoMapper.mapToDto(bookRepository.findAll());
    }

    @Transactional
    public BookResponseDto findBookById(Long id) {
        BookEntity bookEntity = getBookEntity(id);
        return bookDtoMapper.mapToDto(bookEntity);
    }



    @Transactional
    public BookResponseDto createBook(BookRequestDto bookModel) {
        BookEntity bookEntity = bookDtoMapper.mapToEntity(bookModel);
        if (bookModel.getGenreId() != null) {
            bookEntity.setGenre(getGenreEntity(bookModel.getGenreId()));
        } else {
            bookEntity.setGenre(new GenreEntity());
        }

        if (bookModel.getAuthorIds() != null && !bookModel.getAuthorIds().isEmpty()) {
            Set<AuthorEntity> authors = bookModel.getAuthorIds().stream().map(this::getAuthorEntity).collect(Collectors.toSet());
        }

        bookEntity = bookRepository.save(bookEntity);
        return bookDtoMapper.mapToDto(bookEntity);

    }

    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto bookModel) {
        BookEntity existingBookEntity = getBookEntity(id);

        existingBookEntity.setTitle(bookModel.getTitle());
        existingBookEntity.setReleaseYear(bookModel.getReleaseYear());
        existingBookEntity.setDescription(bookModel.getDescription());
        existingBookEntity.setNumberOfCopies(bookModel.getNumberOfCopies());

        if(bookModel.getAuthorIds() != null && !bookModel.getAuthorIds().isEmpty()) {
            Set<AuthorEntity> authors = bookModel.getAuthorIds().stream().map(this::getAuthorEntity).collect(Collectors.toSet());
            existingBookEntity.setAuthors(authors);
        } else {
            existingBookEntity.setAuthors(new HashSet<>());
        }

        if(bookModel.getGenreId() != null) {
            existingBookEntity.setGenre(getGenreEntity(bookModel.getGenreId()));
        } else {
            existingBookEntity.setGenre(null);
        }

        existingBookEntity = bookRepository.save(existingBookEntity);
        return bookDtoMapper.mapToDto(existingBookEntity);

    }

    private BookEntity getBookEntity(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Boek " + id + " niet gevonden"));
    }

    private GenreEntity getGenreEntity(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new RecordNotFoundException("Genre " + genreId + " niet gevonden"));
    }

    private AuthorEntity getAuthorEntity(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(()-> new RecordNotFoundException("Auteur " + authorId + " niet gevonden"));
    }

    @Transactional
    public void deleteBook(Long id) {
        BookEntity book = getBookEntity(id);

        if(book.getCollection() == null) {
            bookRepository.deleteById(id);
        }
    }

    @Transactional
    public void linkAuthor(Long bookId, Long authorId) {
        BookEntity existingBookEntity = getBookEntity(bookId);
        AuthorEntity existingAuthorEntity = authorRepository.findById(authorId)
                .orElseThrow(()-> new RecordNotFoundException("Auteur " + authorId + " niet gevonden"));
        existingAuthorEntity.getBooks().add(existingBookEntity);
        existingBookEntity.getAuthors().add(existingAuthorEntity);
        bookRepository.save(existingBookEntity);
    }

    @Transactional
    public void unlinkAuthor(Long bookId, Long authorId) {
        BookEntity existingBookEntity = getBookEntity(bookId);
        AuthorEntity existingAuthorEntity = authorRepository.findById(authorId)
                .orElseThrow(()-> new RecordNotFoundException("Auteur " + authorId + " niet gevonden"));
        existingAuthorEntity.getBooks().remove(existingBookEntity);
        existingBookEntity.getAuthors().remove(existingAuthorEntity);
        bookRepository.save(existingBookEntity);
    }

    @Transactional
    public List<BookResponseDto> getAvailableBooks(Boolean collection) {
        if(collection) {
            return bookDtoMapper.mapToDto(bookRepository.findByCollectionNotEmpty());
        } else {
            return bookDtoMapper.mapToDto(bookRepository.findByCollectionEmpty());
        }
    }


}
