package nl.novi.eindopdrachtbackendlibrary.services;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackendlibrary.dtos.collection.CollectionRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.collection.CollectionResponseDto;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.CollectionEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.mappers.CollectionDtoMapper;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final CollectionDtoMapper collectionDtoMapper;
    private final BookRepository bookRepository;

    public CollectionService(CollectionRepository collectionRepository, CollectionDtoMapper collectionDtoMapper, BookRepository bookRepository) {
        this.collectionRepository = collectionRepository;
        this.collectionDtoMapper = collectionDtoMapper;
        this.bookRepository = bookRepository;
    }

    public List<CollectionResponseDto> findAllCollections() {
        return collectionDtoMapper.mapToDto(collectionRepository.findAll());
    }

    public CollectionResponseDto findCollectionById(Long id) {
        CollectionEntity collectionEntity = collectionRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Collectie " + id + " niet gevonden."));
        return collectionDtoMapper.mapToDto(collectionEntity);
    }

    public CollectionResponseDto createCollection(CollectionRequestDto collectionRequestDto) {
        CollectionEntity collectionEntity = collectionDtoMapper.mapToEntity(collectionRequestDto);
        collectionEntity = collectionRepository.save(collectionEntity);
        return collectionDtoMapper.mapToDto(collectionEntity);
    }

    public CollectionResponseDto updateCollection(Long id, CollectionRequestDto collectionRequestDto) {
        CollectionEntity existingCollection = getCollectionEntity(id);

        existingCollection.setName(collectionRequestDto.getName());
        existingCollection.setDescription(collectionRequestDto.getDescription());

        if (collectionRequestDto.getBookIds() != null && !collectionRequestDto.getBookIds().isEmpty()) {
            List<BookEntity> books = bookRepository.findAllById(collectionRequestDto.getBookIds());
            existingCollection.setBooks(books);
        }

        existingCollection = collectionRepository.save(existingCollection);
        return collectionDtoMapper.mapToDto(existingCollection);
    }

    @Transactional
    public void deleteCollection(Long id) {
        CollectionEntity collection = getCollectionEntity(id);

        List<BookEntity> books = collection.getBooks();
        books.forEach(book -> book.setCollection(null));
        bookRepository.saveAll(books);

        collectionRepository.deleteById(id);
    }

    public CollectionResponseDto addBooksToCollection(Long collectionId, List<Long> bookIds) {
        CollectionEntity collection = getCollectionEntity(collectionId);

        List<BookEntity> newBooks = bookRepository.findAllById(bookIds);

        if (newBooks.size() != bookIds.size()) {
            throw new RecordNotFoundException("Sommige boeken bestaan niet");
        }

        collection.getBooks().addAll(newBooks);
        newBooks.forEach(book -> book.setCollection(collection));

        collectionRepository.save(collection);
        return collectionDtoMapper.mapToDto(collection);
    }

    public CollectionResponseDto removeBooksFromCollection(Long collectionId, List<Long> bookIds) {
        CollectionEntity collection = getCollectionEntity(collectionId);

        List<BookEntity> booksToRemove = bookRepository.findAllById(bookIds);

        collection.getBooks().removeAll(booksToRemove);
        booksToRemove.forEach(book -> book.setCollection(null));

        bookRepository.saveAll(booksToRemove);
        collectionRepository.save(collection);
        return collectionDtoMapper.mapToDto(collection);
    }

    private CollectionEntity getCollectionEntity(Long id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Collectie " + id + " niet gevonden"));
    }


}
