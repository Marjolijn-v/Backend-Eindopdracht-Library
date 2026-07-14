package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.*;



@Entity
@Table(name = "collections")
public class CollectionEntity extends BaseEntity{

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }
}
