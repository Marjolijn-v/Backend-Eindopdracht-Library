package nl.novi.eindopdrachtbackendlibrary.mappers;

import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookDtoMapper implements DtoMapper<BookResponseDto, BookRequestDto, BookEntity>

{
    @Override
    public BookResponseDto mapToDto(BookEntity model) {
        var result = new BookResponseDto();
        result.setId(model.getId());
        result.setTitle(model.getTitle());
        result.setDescription(model.getDescription());
        result.setReleaseYear(model.getReleaseYear());
        result.setNumberOfCopies(model.getNumberOfCopies());
        return result;
    }

    @Override
    public List<BookResponseDto> mapToDto(List<BookEntity> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BookEntity mapToEntity(BookRequestDto bookModel) {
        var result = new BookEntity();
        result.setTitle(bookModel.getTitle());
        result.setDescription(bookModel.getDescription());
        result.setReleaseYear(bookModel.getReleaseYear());
        result.setNumberOfCopies(bookModel.getNumberOfCopies());
        return result;
    }
}
