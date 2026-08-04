package nl.novi.eindopdrachtbackendlibrary.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookRequestDto;
import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import nl.novi.eindopdrachtbackendlibrary.repositories.AuthorRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();
    private BookEntity savedBook1;
    private BookEntity savedBook2;
    private AuthorEntity savedAuthor;
    private GenreEntity savedGenre;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();

        GenreEntity genre = new GenreEntity();
        genre.setName("Roman");
        savedGenre = genreRepository.save(genre);

        BookEntity book1 = new BookEntity();
        book1.setTitle("Boek 1");
        book1.setReleaseYear(2020);
        book1.setGenre(savedGenre);
        book1.setDescription("Test beschrijving boek 1");
        book1.setNumberOfCopies(3);
        savedBook1 = bookRepository.save(book1);

        BookEntity book2 = new BookEntity();
        book2.setTitle("Boek 2");
        book2.setReleaseYear(2010);
        book2.setGenre(savedGenre);
        book2.setDescription("Test beschrijving boek 2");
        book2.setNumberOfCopies(2);
        savedBook2 = bookRepository.save(book2);

        AuthorEntity author = new AuthorEntity();
        author.setName("Jan Jansen");
        author.setBiography("Nederlandse testauteur");
        savedAuthor = authorRepository.save(author);


    }

    @Test
    void getAllBooks_shouldReturnListOfBooks() throws Exception {
        mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Boek 1"))
                .andExpect(jsonPath("$[1].title").value("Boek 2"));
    }

    @Test
    void getBookById_shouldReturnBook_whenBookExists() throws Exception {
        mockMvc.perform(get("/books/{id}", savedBook1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedBook1.getId()))
                .andExpect(jsonPath("$.title").value("Boek 1"));
    }

    @Test
    void getBookById_shouldReturnNotFound_whenBookDoesNotExist() throws Exception {
        mockMvc.perform(get("/books/{id}", 999L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook_shouldCreateBook() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Nieuw Boek");
        bookRequestDto.setReleaseYear(2024);
        bookRequestDto.setDescription("Nieuw testboek");
        bookRequestDto.setGenreId(savedGenre.getId());
        bookRequestDto.setNumberOfCopies(4);


        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Nieuw Boek"))
                .andExpect(jsonPath("$.releaseYear").value(2024))
                .andExpect(jsonPath("$.description").value("Nieuw testboek"))
                .andExpect(jsonPath("$.numberOfCopies").value(4));
    }

    @Test
    void updateBook_shouldUpdateAndReturnBook_whenBookExists() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Boek 1 aangepast");
        bookRequestDto.setReleaseYear(2021);
        bookRequestDto.setDescription("Nieuwe beschrijving");
        bookRequestDto.setGenreId(savedGenre.getId());
        bookRequestDto.setNumberOfCopies(5);

        mockMvc.perform(put("/books/{id}", savedBook1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Boek 1 aangepast"))
                .andExpect(jsonPath("$.releaseYear").value(2021))
                .andExpect(jsonPath("$.description").value("Nieuwe beschrijving"))
                .andExpect(jsonPath("$.numberOfCopies").value(5));

    }

    @Test
    void updateBook_shouldReturnNotFound_whenBookDoesNotExist() throws Exception {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Niet bestaand boek");
        bookRequestDto.setReleaseYear(2021);
        bookRequestDto.setDescription("Niet bestaand boek");
        bookRequestDto.setNumberOfCopies(5);

        mockMvc.perform(put("/books/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBook_shouldDeleteBook_whenBookExists() throws Exception {
        Long bookId = savedBook1.getId();

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());

        assertFalse(bookRepository.findById(bookId).isPresent());
    }

    @Test
    void deleteBook_shouldReturnNotFound_whenBookDoesNotExist() throws Exception {
        mockMvc.perform(delete("/books/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void linkAuthor_shouldLinkBookWithAuthor() throws Exception {
        mockMvc.perform(post("/books/{bookId}/authors/{authorId}", savedBook1.getId(), savedAuthor.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/books/{id}/authors", savedBook1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(savedAuthor.getId()))
                .andExpect(jsonPath("$[0].name").value("Jan Jansen"));
    }

    @Test
    void unlinkAuthor_shouldUnlinkBookAndAuthor() throws Exception {
        mockMvc.perform(post("/books/{bookId}/authors/{authorId}", savedBook1.getId(), savedAuthor.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/books/{bookId}/authors/{authorId}", savedBook1.getId(), savedAuthor.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/books/{id}/authors", savedBook1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


}