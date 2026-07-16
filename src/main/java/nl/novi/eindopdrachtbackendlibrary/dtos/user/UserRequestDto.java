package nl.novi.eindopdrachtbackendlibrary.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UserRequestDto {
    @NotBlank(message = "Naam mag niet leeg zijn.")
    @Size(max = 100, message = "Naam mag max 100 karakters bevatten.")
    private String name;

    @NotBlank(message = "E-mailadres mag niet leeg zijn.")
    @Email(message = "Voer een geldig e-mailadres in.")
    private String email;

    private String phoneNumber;
    private LocalDateTime dob;

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
}
