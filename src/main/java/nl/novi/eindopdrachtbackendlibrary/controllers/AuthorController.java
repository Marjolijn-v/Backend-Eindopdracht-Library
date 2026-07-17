package nl.novi.eindopdrachtbackendlibrary.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorResponseDto;
import nl.novi.eindopdrachtbackendlibrary.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendlibrary.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final UrlHelper urlHelper;

    public AuthorController(AuthorService authorService, UrlHelper urlHelper) {
        this.authorService = authorService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> getAllAuthors() {
        List<AuthorResponseDto> authors = authorService.findAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> getAuthorById(@PathVariable Long id) {
        AuthorResponseDto author = authorService.findAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody @Valid AuthorRequestDto authorRequestDto) {
        AuthorResponseDto author = authorService.createAuthor(authorRequestDto);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(author.getId())).body(author);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorRequestDto authorRequestDto) {
        AuthorResponseDto author = authorService.updateAuthor(id, authorRequestDto);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
