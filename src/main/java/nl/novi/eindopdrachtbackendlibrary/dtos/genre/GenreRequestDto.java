package nl.novi.eindopdrachtbackendlibrary.dtos.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GenreRequestDto {
    @NotBlank(message = "Naam mag niet leeg zijn.")
    @Size(min = 2, max = 100, message = "Naam bevat min 2 en max 100 karakters.")
    private String name;

    @Size(max = 300, message = "Beschrijving mag niet langer zijn dan 300 karakters.")
    private String description;

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
}
