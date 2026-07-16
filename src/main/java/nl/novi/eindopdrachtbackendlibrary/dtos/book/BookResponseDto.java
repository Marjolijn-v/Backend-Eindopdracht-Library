package nl.novi.eindopdrachtbackendlibrary.dtos.book;

import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorResponseDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreResponseDto;

import java.util.Set;

public class BookResponseDto {
    private Long id;
    private String title;
    private int releaseYear;
    private Set<AuthorResponseDto> authors;
    private String description;
    private GenreResponseDto genre;
    private int numberOfCopies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Set<AuthorResponseDto> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorResponseDto> authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GenreResponseDto getGenre() {
        return genre;
    }

    public void setGenre(GenreResponseDto genre) {
        this.genre = genre;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}
