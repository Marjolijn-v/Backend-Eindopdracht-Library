package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "loan_activities")
public class LoanActivityEntity extends BaseEntity {
    private BookEntity book;
    private MemberEntity member;
    private LocalDateTime loanDate;
    // Helper maken voor returnDate


    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }
}
