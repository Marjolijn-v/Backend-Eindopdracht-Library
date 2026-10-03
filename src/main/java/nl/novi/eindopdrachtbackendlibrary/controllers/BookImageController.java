package nl.novi.eindopdrachtbackendlibrary.controllers;

import nl.novi.eindopdrachtbackendlibrary.entities.BookImageEntity;
import nl.novi.eindopdrachtbackendlibrary.services.BookImageService;
import nl.novi.eindopdrachtbackendlibrary.dtos.bookImage.BookImageResponseDto;
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
    public ResponseEntity<BookImageResponseDto> uploadImage(
            @PathVariable Long bookId,
            @RequestParam("file")MultipartFile file,
            @RequestParam(value = "imageType", required = false, defaultValue = "COVER") String imageType
            ) throws IOException {

        BookImageEntity image = bookImageService.uploadImage(bookId, file, imageType);

        String imageUrl = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{bookImageId}")
                .buildAndExpand(image.getId())
                .toUriString();

        BookImageResponseDto response = new BookImageResponseDto();
        response.setId(image.getId());
        response.setFileName(image.getFileName());
        response.setContentType(image.getContentType());
        response.setImageType(image.getImageType());
        response.setBookId(bookId);
        response.setDownloadUrl(imageUrl);

        return ResponseEntity
                .created(URI.create(imageUrl))
                .body(response);

    }

    @GetMapping
    public ResponseEntity<List<BookImageResponseDto>> getImages(@PathVariable Long bookId) {
        List<BookImageResponseDto> images = bookImageService.getImagesForBook(bookId)
                .stream()
                .map(image -> {
                    String downloadUrl = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{bookImageId}")
                            .buildAndExpand(image.getId())
                            .toUriString();

                    BookImageResponseDto dto = new BookImageResponseDto();
                    dto.setId(image.getId());
                    dto.setFileName(image.getFileName());
                    dto.setContentType(image.getContentType());
                    dto.setImageType(image.getImageType());
                    dto.setBookId(bookId);
                    dto.setDownloadUrl(downloadUrl);
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(images);
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
