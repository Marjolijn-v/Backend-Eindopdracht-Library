package nl.novi.eindopdrachtbackendlibrary.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookRequestDto;
import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import nl.novi.eindopdrachtbackendlibrary.repositories.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private AuthorEntity savedAuthor1;
    private AuthorEntity savedAuthor2;


    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();

        AuthorEntity author1 = new AuthorEntity();
        author1.setName("Jan Jansen");
        author1.setBiography("Biografie Jan Jansen");
        savedAuthor1 = authorRepository.save(author1);

        AuthorEntity author2 = new AuthorEntity();
        author2.setName("Anna de Vries");
        author2.setBiography("Biografie Anna de Vries");
        savedAuthor2 = authorRepository.save(author2);
    }

    @Test
    void getAllAuthors_shouldReturnListOfAuthors() throws Exception {
        mockMvc.perform(get("/authors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Jan Jansen"))
                .andExpect(jsonPath("$[1].name").value("Anna de Vries"));
    }

    @Test
    void getAuthorById_shouldReturnBook_whenBookExists() throws Exception {
        mockMvc.perform(get("/authors/{id}", savedAuthor1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedAuthor1.getId()))
                .andExpect(jsonPath("$.name").value("Jan Jansen"));
    }

    @Test
    void getAuthorById_shouldReturnBook_whenBookDoesNotExists() throws Exception {
        mockMvc.perform(get("/authors/{id}", 999L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAuthor_shouldCreateAuthor() throws Exception {
        AuthorRequestDto authorRequestDto = new AuthorRequestDto();
        authorRequestDto.setName("Piet Pietersen");
        authorRequestDto.setBiography("Biografie van Piet Pietersen");

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Piet Pietersen"))
                .andExpect(jsonPath("$.biography").value("Biografie van Piet Pietersen"));

    }

    @Test
    void updateAuthor_shouldUpdateAuthor_whenAuthorExists() throws Exception {
        AuthorRequestDto authorRequestDto = new AuthorRequestDto();
        authorRequestDto.setName("Jan Janssen");
        authorRequestDto.setBiography("Biografie van Jan Janssen");

        mockMvc.perform(put("/authors/{id}", savedAuthor1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jan Janssen"))
                .andExpect(jsonPath("$.biography").value("Biografie van Jan Janssen"));
    }

    @Test
    void updateAuthor_shouldReturnNotFound_whenAuthorDoesNotExists() throws Exception {
        AuthorRequestDto authorRequestDto = new AuthorRequestDto();
        authorRequestDto.setName("Niet bestaande auteur");
        authorRequestDto.setBiography("Biografie niet bestaande auteur");

        mockMvc.perform(put("/authors/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authorRequestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAuthor_shouldDeleteAuthor_whenAuthorExists() throws Exception {
        Long authorId = savedAuthor1.getId();

        mockMvc.perform(delete("/authors/{id}", authorId))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteAuthor_shouldReturnNotFound_whenAuthorDoesNotExists() throws Exception {
        mockMvc.perform(delete("/authors/{id}", 999L))
                .andExpect(status().isNoContent());
    }
}