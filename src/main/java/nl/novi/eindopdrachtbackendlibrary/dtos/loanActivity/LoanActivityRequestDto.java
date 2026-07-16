package nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class LoanActivityRequestDto {
    @NotNull
    private Long bookId;
    @NotNull
    private Long userId;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
