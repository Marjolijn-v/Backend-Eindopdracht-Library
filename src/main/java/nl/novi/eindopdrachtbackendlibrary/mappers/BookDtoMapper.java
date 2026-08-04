package nl.novi.eindopdrachtbackendlibrary.mappers;

import nl.novi.eindopdrachtbackendlibrary.dtos.author.AuthorSummaryDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookResponseDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.genre.GenreSummaryDto;
import nl.novi.eindopdrachtbackendlibrary.entities.AuthorEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.GenreEntity;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookDtoMapper implements DtoMapper<BookResponseDto, BookRequestDto, BookEntity> {

    @Override
    public BookResponseDto mapToDto(BookEntity model) {
        var result = new BookResponseDto();
        result.setId(model.getId());
        result.setTitle(model.getTitle());
        result.setDescription(model.getDescription());
        result.setReleaseYear(model.getReleaseYear());
        result.setGenre(mapGenreToSummary(model.getGenre()));
        result.setNumberOfCopies(model.getNumberOfCopies());


        if (model.getAuthors() != null && !model.getAuthors().isEmpty()) {
            result.setAuthors(model.getAuthors().stream().map(this::mapAuthorToSummary).collect(Collectors.toSet()));
        }

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

    private AuthorSummaryDto mapAuthorToSummary(AuthorEntity author) {
        var dto = new AuthorSummaryDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        return dto;
    }

    private GenreSummaryDto mapGenreToSummary(GenreEntity genre) {
        var dto = new GenreSummaryDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

}
