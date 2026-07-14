package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_activities")
public class LoanActivityEntity extends BaseEntity {

    @OneToMany
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @OneToMany
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDateTime loanDate;
    // Helper maken voor returnDate


    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }
}
