package nl.novi.eindopdrachtbackendlibrary.exeptions;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String message) {
        super(message);
    }
}
