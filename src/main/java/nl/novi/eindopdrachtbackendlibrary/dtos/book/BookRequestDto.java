package nl.novi.eindopdrachtbackendlibrary.dtos.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class BookRequestDto {
    @NotBlank(message = "Titel mag niet leeg zijn")
    @Size(min = 3, max = 200, message = "Titel bevat min 2 en max 3 karakters.")
    private String title;

    private int releaseYear;

    @NotNull
    private Set<Long> authorIds;
    @Size(max = 600, message = "Beschrijving mag maximaal 600 karakters bevatten.")
    private String description;
    private Long genreId;
    private int numberOfCopies;

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

    public Long getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}
