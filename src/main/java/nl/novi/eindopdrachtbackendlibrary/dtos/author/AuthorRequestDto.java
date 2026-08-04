package nl.novi.eindopdrachtbackendlibrary.dtos.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class AuthorRequestDto {
    @NotBlank(message = "Naam mag niet leeg zijn")
    private String name;

    @Size(max = 500, message = "Biografie mag maximaal 500 tekens bevatten")
    private String biography;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
