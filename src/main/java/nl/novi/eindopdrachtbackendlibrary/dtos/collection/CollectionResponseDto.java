package nl.novi.eindopdrachtbackendlibrary.dtos.collection;

import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;

import java.util.List;

public class CollectionResponseDto {
    private Long id;
    private List<BookResponseDto> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookResponseDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponseDto> books) {
        this.books = books;
    }
}
