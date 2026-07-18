package nl.novi.eindopdrachtbackendlibrary.dtos.collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CollectionRequestDto {

    @NotBlank(message = "Naam mag niet leeg zijn")
    @Size(min = 2, max = 100, message = "Naam moet tussen 2 en 200 karakters zijn")
    private String name;

    @Size(max = 500, message = "Beschrijving mag maximaal 500 karakters zijn")
    private String description;

    private List<Long> bookIds;

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

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
