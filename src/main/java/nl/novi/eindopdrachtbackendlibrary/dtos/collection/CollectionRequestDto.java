package nl.novi.eindopdrachtbackendlibrary.dtos.collection;

import java.util.List;

public class CollectionRequestDto {
    private List<Long> bookIds;

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
