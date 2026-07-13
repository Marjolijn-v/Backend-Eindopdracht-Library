package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity{
    private String title;
    private int releaseYear;
    private AuthorEntity author;
    private String description;

//    picture en numberOfCopies toevoegen.




}
