package nl.novi.eindopdrachtbackendlibrary.services;

import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.CollectionEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.BookDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.AuthorRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookDtoMapper bookDtoMapper;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private BookService bookService;

    private BookEntity bookEntity;
    private AuthorEntity authorEntity;
    private GenreEntity genreEntity;
    private BookRequestDto requestDto;
    private BookResponseDto responseDto;

    @BeforeEach
    void setUp() {
        bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setTitle("Old Title");
        bookEntity.setReleaseYear(1999);
        bookEntity.setDescription("Old desc");
        bookEntity.setNumberOfCopies(1);
        bookEntity.setAuthors(new HashSet<>());

        authorEntity = new AuthorEntity();
        authorEntity.setId(10L);
        authorEntity.setBooks(new HashSet<>());

        genreEntity = new GenreEntity();
        genreEntity.setId(20L);

        requestDto = new BookRequestDto();
        requestDto.setTitle("New Title");
        requestDto.setReleaseYear(2024);
        requestDto.setDescription("New desc");
        requestDto.setNumberOfCopies(5);
        requestDto.setAuthorIds(Set.of(10L));
        requestDto.setGenreId(20L);

        responseDto = new BookResponseDto();
    }

    @Test
    void findAllBooks_returnsMappedDtos() {
        List<BookEntity> entities = List.of(bookEntity);
        List<BookResponseDto> mapped = List.of(responseDto);

        when(bookRepository.findAll()).thenReturn(entities);
        when(bookDtoMapper.mapToDto(entities)).thenReturn(mapped);

        List<BookResponseDto> result = bookService.findAllBooks();

        assertEquals(1, result.size());
        assertSame(mapped, result);
        verify(bookRepository).findAll();
        verify(bookDtoMapper).mapToDto(entities);
    }

    @Test
    void findBookById_returnsMappedDto_whenFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(bookDtoMapper.mapToDto(bookEntity)).thenReturn(responseDto);

        BookResponseDto result = bookService.findBookById(1L);

        assertSame(responseDto, result);
        verify(bookRepository).findById(1L);
        verify(bookDtoMapper).mapToDto(bookEntity);
    }

    @Test
    void findBookById_throwsRecordNotFoundException_whenNotFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> bookService.findBookById(999L));

        verify(bookRepository).findById(999L);
        verifyNoInteractions(bookDtoMapper);
    }

    @Test
    void createBook_setsGenreAndAuthors_whenProvided() {
        BookEntity mappedEntity = new BookEntity();
        mappedEntity.setAuthors(new HashSet<>());

        when(bookDtoMapper.mapToEntity(requestDto)).thenReturn(mappedEntity);
        when(genreRepository.findById(20L)).thenReturn(Optional.of(genreEntity));
        when(authorRepository.findById(10L)).thenReturn(Optional.of(authorEntity));
        when(bookRepository.save(mappedEntity)).thenReturn(mappedEntity);
        when(bookDtoMapper.mapToDto(mappedEntity)).thenReturn(responseDto);

        BookResponseDto result = bookService.createBook(requestDto);

        assertSame(responseDto, result);
        assertSame(genreEntity, mappedEntity.getGenre());
        assertTrue(mappedEntity.getAuthors().contains(authorEntity));
        verify(bookRepository).save(mappedEntity);
    }

    @Test
    void createBook_setsNewGenreEntity_whenGenreIdNull() {
        requestDto.setGenreId(null);
        requestDto.setAuthorIds(null);

        BookEntity mappedEntity = new BookEntity();
        mappedEntity.setAuthors(new HashSet<>());

        when(bookDtoMapper.mapToEntity(requestDto)).thenReturn(mappedEntity);
        when(bookRepository.save(mappedEntity)).thenReturn(mappedEntity);
        when(bookDtoMapper.mapToDto(mappedEntity)).thenReturn(responseDto);

        bookService.createBook(requestDto);

        assertNotNull(mappedEntity.getGenre());
        verify(genreRepository, never()).findById(anyLong());
        verify(authorRepository, never()).findById(anyLong());
    }

    void createBook_throws_whenAuthorNotFound() {
        BookEntity mappedEntity = new BookEntity();
        mappedEntity.setAuthors(new HashSet<>());
        when(bookDtoMapper.mapToEntity(requestDto)).thenReturn(mappedEntity);
        when(genreRepository.findById(20L)).thenReturn(Optional.of(genreEntity));
        when(authorRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> bookService.createBook(requestDto));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void updateBook_updatesAllFields_andReturnsDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(authorRepository.findById(10L)).thenReturn(Optional.of(authorEntity));
        when(genreRepository.findById(20L)).thenReturn(Optional.of(genreEntity));
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookDtoMapper.mapToDto(bookEntity)).thenReturn(responseDto);

        BookResponseDto result = bookService.updateBook(1L, requestDto);

        assertSame(responseDto, result);
        assertEquals("New Title", bookEntity.getTitle());
        assertEquals(2024, bookEntity.getReleaseYear());
        assertEquals("New desc", bookEntity.getDescription());
        assertEquals(5, bookEntity.getNumberOfCopies());
        assertSame(genreEntity, bookEntity.getGenre());
        assertTrue(bookEntity.getAuthors().contains(authorEntity));
    }

    @Test
    void updateBook_clearsAuthorsAndGenre_whenEmptyAndNull() {
        requestDto.setAuthorIds(Collections.emptySet());
        requestDto.setGenreId(null);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookDtoMapper.mapToDto(bookEntity)).thenReturn(responseDto);

        bookService.updateBook(1L, requestDto);

        assertNotNull(bookEntity.getAuthors());
        assertTrue(bookEntity.getAuthors().isEmpty());
        assertNull(bookEntity.getGenre());
        verify(authorRepository, never()).findById(anyLong());
        verify(genreRepository, never()).findById(anyLong());
    }

    @Test
    void deleteBook_deletes_whenCollectionIsNull() {
        bookEntity.setCollection(null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void deleteBook_doesNotDelete_whenCollectionIsNotNull() {
        bookEntity.setCollection(new CollectionEntity());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        bookService.deleteBook(1L);

        verify(bookRepository, never()).deleteById(anyLong());
    }

    @Test
    void linkAuthor_linksBothSides_andSavesBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(authorRepository.findById(10L)).thenReturn(Optional.of(authorEntity));

        bookService.linkAuthor(1L, 10L);

        assertTrue(bookEntity.getAuthors().contains(authorEntity));
        assertTrue(authorEntity.getBooks().contains(bookEntity));
        verify(bookRepository).save(bookEntity);
    }

    @Test
    void unlinkAuthor_unlinksBothSides_andSavesBook() {
        bookEntity.getAuthors().add(authorEntity);
        authorEntity.getBooks().add(bookEntity);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(authorRepository.findById(10L)).thenReturn(Optional.of(authorEntity));

        bookService.unlinkAuthor(1L, 10L);

        assertFalse(bookEntity.getAuthors().contains(authorEntity));
        assertFalse(authorEntity.getBooks().contains(bookEntity));
        verify(bookRepository).save(bookEntity);
    }

    @Test
    void getAvailableBooks_true_usesFindByCollectionNotEmpty() {
        List<BookEntity> entities = List.of(bookEntity);
        List<BookResponseDto> dtos = List.of(responseDto);

        when(bookRepository.findByCollectionNotEmpty()).thenReturn(entities);
        when(bookDtoMapper.mapToDto(entities)).thenReturn(dtos);

        List<BookResponseDto> result = bookService.getAvailableBooks(true);

        assertSame(dtos, result);
        verify(bookRepository).findByCollectionNotEmpty();
    }

    @Test
    void getAvailableBooks_false_usesFindByCollectionEmpty() {
        List<BookEntity> entities = List.of(bookEntity);
        List<BookResponseDto> dtos = List.of(responseDto);

        when(bookRepository.findByCollectionEmpty()).thenReturn(entities);
        when(bookDtoMapper.mapToDto(entities)).thenReturn(dtos);

        List<BookResponseDto> result = bookService.getAvailableBooks(false);

        assertSame(dtos, result);
        verify(bookRepository).findByCollectionEmpty();
    }





}