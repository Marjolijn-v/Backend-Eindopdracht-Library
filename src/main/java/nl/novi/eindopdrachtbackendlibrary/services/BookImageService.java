package nl.novi.eindopdrachtbackendlibrary.services;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackendlibrary.entities.BookEntity;
import nl.novi.eindopdrachtbackendlibrary.entities.BookImageEntity;
import nl.novi.eindopdrachtbackendlibrary.exeptions.RecordNotFoundException;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookImageRepository;
import nl.novi.eindopdrachtbackendlibrary.repositories.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookImageService {
    private final BookImageRepository bookImageRepository;
    private final BookRepository bookRepository;

    public BookImageService(BookImageRepository bookImageRepository, BookRepository bookRepository) {
        this.bookImageRepository = bookImageRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookImageEntity uploadImage(Long bookId, MultipartFile file, String imageType) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Er is geen afbeelding gevonden");
        }

        if (file.getContentType() == null || !file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("Alleen afbeeldingen zijn toegestaan");
        }

        BookEntity book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RecordNotFoundException("Boek " + bookId + " niet gevonden"));

        BookImageEntity image = new BookImageEntity();
        image.setFileName(file.getOriginalFilename());
        image.setContents(file.getBytes());
        image.setContentType(file.getContentType());
        image.setImageType(imageType);
        image.setBook(book);

        return bookImageRepository.save(image);
    }

    @Transactional
    public BookImageEntity getImage(Long bookId, Long bookImageId) {
        BookImageEntity image = bookImageRepository.findById(bookImageId)
                .orElseThrow(() -> new RecordNotFoundException("Afbeelding " + bookImageId + " niet gevonden"));
       return image;
    }

    @Transactional
    public List<BookImageEntity> getImagesForBook(Long bookId) {
        if(!bookRepository.existsById(bookId)) {
            throw new RecordNotFoundException("Boek " + bookId + " niet gevonden");
        }

        return bookImageRepository.findByBookId(bookId);
    }

    @Transactional
    public void deleteImage(Long bookId, Long bookImageId) {
        BookImageEntity image = getImage(bookId, bookImageId);
        bookImageRepository.delete(image);
    }
}
