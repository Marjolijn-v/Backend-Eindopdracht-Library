package nl.novi.eindopdrachtbackendlibrary.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity.LoanActivityRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity.LoanActivityResponseDto;
import nl.novi.eindopdrachtbackendlibrary.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendlibrary.services.LoanActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan-activities")
public class LoanActivityController {

    private final LoanActivityService loanActivityService;
    private final UrlHelper urlHelper;

    public LoanActivityController(LoanActivityService loanActivityService, UrlHelper urlHelper) {
        this.loanActivityService = loanActivityService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<LoanActivityResponseDto>> getAllLoanActivities() {
        List<LoanActivityResponseDto> loanActivities = loanActivityService.findAllLoanActivities();
        return new ResponseEntity<>(loanActivities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanActivityResponseDto> getLoanActivityById(@PathVariable Long id) {
        LoanActivityResponseDto loanActivity = loanActivityService.findLoanActivityById(id);
        return new ResponseEntity<>(loanActivity, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanActivityResponseDto>> getLoanActivitiesByUser(@PathVariable Long userId) {
        List<LoanActivityResponseDto> loanActivities = loanActivityService.findLoanActivitiesByUser(userId);
        return new ResponseEntity<>(loanActivities, HttpStatus.OK);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<LoanActivityResponseDto>> getLoanActivitiesByBook(@PathVariable Long bookId) {
        List<LoanActivityResponseDto> loanActivities = loanActivityService.findLoanActivitiesByBook(bookId);
        return new ResponseEntity<>(loanActivities, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<LoanActivityResponseDto>> getActiveLoanActivities() {
        List<LoanActivityResponseDto> loanActivities = loanActivityService.findActiveLoanActivities();
        return new ResponseEntity<>(loanActivities, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoanActivityResponseDto> createLoanActivity(@RequestBody @Valid  LoanActivityRequestDto loanActivityRequestDto) {
        LoanActivityResponseDto createdLoanActivity = loanActivityService.createLoanActivity(loanActivityRequestDto);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(createdLoanActivity.getId())).body(createdLoanActivity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanActivityResponseDto> updateLoanActivity(@PathVariable Long id, @RequestBody @Valid  LoanActivityRequestDto loanActivityRequestDto) {
        LoanActivityResponseDto updatedLoanActivity = loanActivityService.updateLoanActivity(id, loanActivityRequestDto);
        return new ResponseEntity<>(updatedLoanActivity, HttpStatus.OK);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanActivityResponseDto> returnBook(@PathVariable Long id) {
        LoanActivityResponseDto returnedLoanActivity = loanActivityService.returnBook(id);
        return new ResponseEntity<>(returnedLoanActivity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanActivity(@PathVariable Long id) {
        loanActivityService.deleteLoanActivity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
