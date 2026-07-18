package nl.novi.eindopdrachtbackendlibrary.controllers;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackendlibrary.dtos.collection.CollectionRequestDto;
import nl.novi.eindopdrachtbackendlibrary.dtos.collection.CollectionResponseDto;
import nl.novi.eindopdrachtbackendlibrary.helpers.UrlHelper;
import nl.novi.eindopdrachtbackendlibrary.services.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionService collectionService;
    private final UrlHelper urlHelper;

    public CollectionController(CollectionService collectionService, UrlHelper urlHelper) {
        this.collectionService = collectionService;
        this.urlHelper = urlHelper;
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponseDto>> getAllCollections() {
        List<CollectionResponseDto> collections = collectionService.findAllCollections();
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponseDto> getCollectionById(@PathVariable Long id){
        CollectionResponseDto collection = collectionService.findCollectionById(id);
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CollectionResponseDto> createCollection(@RequestBody @Valid CollectionRequestDto collectionRequestDto) {
        CollectionResponseDto collection = collectionService.createCollection(collectionRequestDto);
        return ResponseEntity.created(urlHelper.getCurrentUrlWithId(collection.getId())).body(collection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionResponseDto> updateCollection(@PathVariable Long id, @RequestBody @Valid CollectionRequestDto collectionRequestDto) {
        CollectionResponseDto collection = collectionService.updateCollection(id, collectionRequestDto);
        return new ResponseEntity<>(collection, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/books")
    public ResponseEntity<CollectionResponseDto> addBooksToCollection(@PathVariable Long id, @RequestBody List<Long> bookIds) {
        CollectionResponseDto updatedCollection = collectionService.addBooksToCollection(id, bookIds);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

    @DeleteMapping("{id}/books")
    public ResponseEntity<CollectionResponseDto> removeBooksFromCollection(@PathVariable Long id, @RequestBody List<Long> bookIds) {
        CollectionResponseDto updatedCollection = collectionService.removeBooksFromCollection(id, bookIds);
        return new ResponseEntity<>(updatedCollection, HttpStatus.OK);
    }

}
