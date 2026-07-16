package nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity;

import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookSummaryDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.user.UserResponseDto;

import java.time.LocalDateTime;

public class LoanActivityResponseDto {
    private Long id;
    private BookSummaryDto book;
    private UserResponseDto user;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookSummaryDto getBook() {
        return book;
    }

    public void setBook(BookSummaryDto book) {
        this.book = book;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
