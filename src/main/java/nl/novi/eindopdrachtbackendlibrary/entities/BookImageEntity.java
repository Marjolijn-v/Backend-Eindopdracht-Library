package nl.novi.eindopdrachtbackendlibrary.entities;

import jakarta.persistence.*;

@Entity
public class BookImageEntity {
    private String fileName;
    private String url;
    private String contentType;

    @Lob
    private byte[] contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    public BookImageEntity(String fileName, String url, String contentType, byte[] contents, BookEntity book) {
        this.fileName = fileName;
        this.url = url;
        this.contentType = contentType;
        this.contents = contents;
        this.book = book;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }
}
