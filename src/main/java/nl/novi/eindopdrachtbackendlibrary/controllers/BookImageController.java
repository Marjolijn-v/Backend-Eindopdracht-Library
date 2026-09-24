package nl.novi.eindopdrachtbackendlibrary.controllers;

import nl.novi.eindopdrachtbackendlibrary.entities.BookImageEntity;
import nl.novi.eindopdrachtbackendlibrary.services.BookImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books/{bookId}/images")
public class BookImageController {

    private final BookImageService bookImageService;

    public BookImageController(BookImageService bookImageService) {
        this.bookImageService = bookImageService;
    }

    @PostMapping
    public ResponseEntity<Long> uploadImage(
            @PathVariable Long bookId,
            @RequestParam("file")MultipartFile file
            ) throws IOException {

        BookImageEntity image = bookImageService.uploadImage(bookId, file);

        String imageUrl = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookImageId}")
                .buildAndExpand(image.getBook().getId())
                .toUriString();

        return ResponseEntity
                .created(URI.create(imageUrl))
                .body(image.getBook().getId());

    }

    @GetMapping
    public ResponseEntity<List<BookImageEntity>> getImages(@PathVariable Long bookId) {
        return ResponseEntity.ok(
                bookImageService.getImagesForBook(bookId)
        );
    }

    @GetMapping("/{bookImageId}")
    public ResponseEntity<byte[]> downloadImage(
            @PathVariable Long bookId,
            @PathVariable Long bookImageId
    ) {
        BookImageEntity image = bookImageService.getImage(bookId, bookImageId);

        MediaType mediaType;

        try {
            mediaType = MediaType.parseMediaType(image.getContentType());
        } catch (InvalidMediaTypeException ignore){
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" +image.getFileName())
                .body(image.getContents());
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long bookId,
            @PathVariable Long bookImageId
    ) {
        bookImageService.deleteImage(bookId, bookImageId);
        return ResponseEntity.noContent().build();
    }
}
