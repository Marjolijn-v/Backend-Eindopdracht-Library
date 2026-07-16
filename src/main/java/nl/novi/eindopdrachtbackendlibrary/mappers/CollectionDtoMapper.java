package nl.novi.eindopdrachtbackendlibrary.mappers;

import nl.novi.eindopdrachtbackendlibrary.dtos.book.BookSummaryDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.collection.CollectionRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.collection.CollectionResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.CollectionEntity;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionDtoMapper implements DtoMapper<CollectionResponseDto, CollectionRequestDto, CollectionEntity>{

    private final BookDtoMapper bookDtoMapper;
    private final BookRepository bookRepository;

    public CollectionDtoMapper(BookDtoMapper bookDtoMapper, BookRepository bookRepository) {
        this.bookDtoMapper = bookDtoMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public CollectionResponseDto mapToDto(CollectionEntity model) {
        var result = new CollectionResponseDto();
        result.setId(model.getId());
        result.setBooks(model.getBooks().stream().map(this::mapBookToSummary).collect(Collectors.toList()));
        return result;
    }

    private BookSummaryDto mapBookToSummary(BookEntity book) {
        var dto = new BookSummaryDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setReleaseYear(book.getReleaseYear());
        dto.setDescription(book.getDescription());
        dto.setNumberOfCopies(book.getNumberOfCopies());
        return dto;
    }

    @Override
    public List<CollectionResponseDto> mapToDto(List<CollectionEntity> models) {
        return models.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CollectionEntity mapToEntity(CollectionRequestDto collectionModel) {
        var result = new CollectionEntity();

        if(collectionModel.getBookIds() != null && !collectionModel.getBookIds().isEmpty()) {
            List<BookEntity> books = bookRepository.findAllById(collectionModel.getBookIds());
            result.setBooks(books);
        } else {
            result.setBooks(new ArrayList<>());
        }

        return result;

    }
}
