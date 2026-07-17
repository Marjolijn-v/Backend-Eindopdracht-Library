package nl.novi.eindopdrachtbackendlibrary.dtos.collection;

import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookSummaryDto;

import java.util.List;

public class CollectionResponseDto {
    private Long id;
    private String name;
    private String description;
    private List<BookSummaryDto> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BookSummaryDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookSummaryDto> books) {
        this.books = books;
    }
}
