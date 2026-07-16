package nl.novi.eindopdrachtbackendlibrary.dtos.author;

import jakarta.validation.constraints.NotBlank;


public class AuthorRequestDto {
    @NotBlank(message = "Naam mag niet leeg zijn")
    private String name;

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
