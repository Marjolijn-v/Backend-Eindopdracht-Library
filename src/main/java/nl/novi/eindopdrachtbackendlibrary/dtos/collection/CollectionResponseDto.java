package nl.novi.eindopdrachtbackendlibrary.dtos.collection;

import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookSummaryDto;

import java.util.List;

public class CollectionResponseDto {
    private Long id;
    private List<BookSummaryDto> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookSummaryDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookSummaryDto> books) {
        this.books = books;
    }
}
