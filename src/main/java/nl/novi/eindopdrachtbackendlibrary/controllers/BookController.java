package nl.novi.eindopdrachtbackendlibrary.controllers;

import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorResponseDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendlibrary.services.AuthorService;
import nl.novi.eindopdrachtbackendlibrary.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final UrlHelper urlHelper;

    public BookController(BookService bookService, AuthorService authorService, UrlHelper urlHelper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks(@RequestParam(required = false) Boolean collection) {
        List<BookResponseDto> books;
        if (collection == null) {
            books = bookService.findAllBooks();
        } else {
            books = bookService.getAvailableBooks(collection);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto book = bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto book = bookService.createBook(bookRequestDto);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(book.getId())).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto book = bookService.updateBook(id, bookRequestDto);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> linkAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookService.linkAuthor(bookId, authorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<Void> unlinkAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookService.unlinkAuthor(bookId, authorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/authors")
    public ResponseEntity<List<AuthorResponseDto>> linkAuthor(@PathVariable Long id) {
        List<AuthorResponseDto> authors = authorService.getAuthorForBook(id);
        return ResponseEntity.ok(authors);
    }

}
