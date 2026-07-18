package nl.novi.eindopdrachtbackendlibrary.services;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity.LoanActivityRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity.LoanActivityResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.LoanActivityEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.UserEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.LoanActivityDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.LoanActivityRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanActivityService {

    private final LoanActivityRepository loanActivityRepository;
    private final LoanActivityDtoMapper loanActivityDtoMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanActivityService(LoanActivityRepository loanActivityRepository,
                               LoanActivityDtoMapper loanActivityDtoMapper,
                               BookRepository bookRepository,
                               UserRepository userRepository) {
        this.loanActivityRepository = loanActivityRepository;
        this.loanActivityDtoMapper = loanActivityDtoMapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<LoanActivityResponseDto> findAllLoanActivities() {
        return loanActivityDtoMapper.mapToDto(loanActivityRepository.findAll());
    }

    public LoanActivityResponseDto findLoanActivityById(Long id) {
        LoanActivityEntity loanActivityEntity = getLoanActivityEntity(id);
        return loanActivityDtoMapper.mapToDto(loanActivityEntity);
    }

    public List<LoanActivityResponseDto> findLoanActivitiesByUser(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()-> new RecordNotFoundException("Gebruiker " + userId + " niet gevonden"));

        List<LoanActivityEntity> loanActivities = loanActivityRepository.findByUser(user);
        return loanActivityDtoMapper.mapToDto(loanActivities);
    }

    public List<LoanActivityResponseDto> findLoanActivitiesByBook(Long bookId) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow(() -> new RecordNotFoundException("Boek " + bookId + " niet gevonden"));

        List<LoanActivityEntity> loanActivities = loanActivityRepository.findByBook(book);
        return loanActivityDtoMapper.mapToDto(loanActivities);
    }

    public List<LoanActivityResponseDto> findActiveLoanActivities() {
        List<LoanActivityEntity> loanActivities = loanActivityRepository.findByReturnDateNull();
        return loanActivityDtoMapper.mapToDto(loanActivities);

    }

    @Transactional
    public LoanActivityResponseDto createLoanActivity(LoanActivityRequestDto loanActivityRequestDto){
        BookEntity book = bookRepository.findById(loanActivityRequestDto.getBookId()).orElseThrow(() -> new RecordNotFoundException("Boek niet gevonden"));

        UserEntity user = userRepository.findById(loanActivityRequestDto.getUserId()).orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden"));

        if (book.getNumberOfCopies() <= 0) {
            throw new IllegalArgumentException("Geen exemplaren van dit boek beschikbaar");
        }

        LoanActivityEntity loanActivity = new LoanActivityEntity();
        loanActivity.setBook(book);
        loanActivity.setUser(user);
        loanActivity.setLoanDate(LoanActivityRequestDto.getLoanDate() != null ? loanActivityRequestDto.getLoanDate() : LocalDateTime.now());
        loanActivity.setReturnDate(loanActivityRequestDto.getReturnDate());

        book.setNumberOfCopies(book.getNumberOfCopies() -1);
        bookRepository.save(book);

        loanActivity = loanActivityRepository.save(loanActivity);
        return loanActivityDtoMapper.mapToDto(loanActivity);
    }

    @Transactional
    public LoanActivityResponseDto updateLoanActivity(Long id, LoanActivityRequestDto loanActivityRequestDto) {
        LoanActivityEntity existingLoanActivity = getLoanActivityEntity(id);

        if(loanActivityRequestDto.getReturnDate() != null && existingLoanActivity.getReturnDate() == null) {
            BookEntity book = existingLoanActivity.getBook();
            book.setNumberOfCopies(book.getNumberOfCopies() +1);
            bookRepository.save(book);
        }

        existingLoanActivity.setLoanDate(loanActivityRequestDto.getLoanDate());
        existingLoanActivity.setReturnDate(loanActivityRequestDto.getReturnDate());

        existingLoanActivity = loanActivityRepository.save(existingLoanActivity);
        return loanActivityDtoMapper.mapToDto(existingLoanActivity);
    }

    @Transactional
    public LoanActivityResponseDto returnBook(Long id) {
        LoanActivityEntity loanActivity = getLoanActivityEntity(id);

        if (loanActivity.getReturnDate() != null) {
            throw new IllegalArgumentException("Dit boek is al terggebracht");
        }

        loanActivity.setReturnDate(LocalDateTime.now());

        BookEntity book = loanActivity.getBook();
        book.setNumberOfCopies(book.getNumberOfCopies() + 1);
        bookRepository.save(book);

        loanActivity = loanActivityRepository.save(loanActivity);
        return loanActivityDtoMapper.mapToDto(loanActivity);
    }

    @Transactional
    public void deleteLoanActivity(Long id) {
        LoanActivityEntity loanActivity = getLoanActivityEntity(id);

        if (loanActivity.getReturnDate() == null) {
            BookEntity book = loanActivity.getBook();
            book.setNumberOfCopies(book.getNumberOfCopies() + 1);
            bookRepository.save(book);
        }

        loanActivityRepository.deleteById(id);
    }


    private LoanActivityEntity getLoanActivityEntity(Long id) {
        return loanActivityRepository.findById(id).orElseThrow(()-> new RecordNotFoundException("Uitleenactie " + id + " niet gevonden."));
    }
}
