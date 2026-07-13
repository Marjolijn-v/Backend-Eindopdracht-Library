package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity{
    @Column(nullable = false)
    private String title;

    private int releaseYear;
    private AuthorEntity author;
    private String description;

//    picture en numberOfCopies toevoegen.




}
