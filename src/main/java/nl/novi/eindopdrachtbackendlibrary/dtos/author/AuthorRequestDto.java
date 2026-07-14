package nl.novi.eindopdrachtbackendlibrary.dtos.author;

import org.antlr.v4.runtime.misc.NotNull;

import java.lang.annotation.Native;

public class AuthorRequestDto {
    @NotNull
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
