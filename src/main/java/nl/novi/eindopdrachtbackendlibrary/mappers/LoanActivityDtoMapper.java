package nl.novi.eindopdrachtbackendlibrary.mappers;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity.LoanActivityRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.loanActivity.LoanActivityResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.LoanActivityEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.UserEntity;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanActivityDtoMapper implements DtoMapper<LoanActivityResponseDto, LoanActivityRequestDto, LoanActivityEntity> {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookDtoMapper bookDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public LoanActivityDtoMapper(BookRepository bookRepository, UserRepository userRepository, BookDtoMapper bookDtoMapper, UserDtoMapper userDtoMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookDtoMapper = bookDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }


    @Override
    public LoanActivityResponseDto mapToDto(LoanActivityEntity model) {
        var result = new LoanActivityResponseDto();
        result.setId(model.getId());
        result.setBook(bookDtoMapper.mapToDto(model.getBook()));
        result.setUser(userDtoMapper.mapToDto(model.getUser()));
        result.setLoanDate(model.getLoanDate());
        result.setReturnDate(model.getReturnDate());
        return result;
    }

    @Override
    public List<LoanActivityResponseDto> mapToDto(List<LoanActivityEntity> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public LoanActivityEntity mapToEntity(LoanActivityRequestDto loanActivityModel) {
        var result = new LoanActivityEntity();

        BookEntity book = bookRepository.findById(loanActivityModel.getBookId()).orElseThrow(() -> new EntityNotFoundException("Boek niet gevonden"));
        result.setBook(book);

        UserEntity user = userRepository.findById(loanActivityModel.getUserId()).orElseThrow(()-> new EntityNotFoundException("Gebruiker niet gevonden"));
        result.setUser(user);

        result.setLoanDate(loanActivityModel.getLoanDate());
        result.setReturnDate(loanActivityModel.getReturnDate());

        return result;
    }
}
