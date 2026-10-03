package nl.novi.eindopdrachtbackendlibrary.exeptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
