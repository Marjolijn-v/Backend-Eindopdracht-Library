package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime dob;
    private List<LoanActivityEntity> loanActivities = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getDob() {
        return dob;
    }

    public void setDob(LocalDateTime dob) {
        this.dob = dob;
    }

    public List<LoanActivityEntity> getLoanActivities() {
        return loanActivities;
    }

    public void setLoanActivities(List<LoanActivityEntity> loanActivities) {
        this.loanActivities = loanActivities;
    }
}
