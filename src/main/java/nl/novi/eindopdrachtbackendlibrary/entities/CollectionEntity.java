package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collections")
public class CollectionEntity extends BaseEntity{
    private List<BookEntity> listOfBooks = new ArrayList<>();

    public List<BookEntity> getListOfBooks() {
        return listOfBooks;
    }

    public void setListOfBooks(List<BookEntity> listOfBooks) {
        this.listOfBooks = listOfBooks;
    }
}
