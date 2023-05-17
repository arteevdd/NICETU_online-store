package test.project.onlineshop.exception;

public class ProducerNotFoundException extends RuntimeException{
    public ProducerNotFoundException(String message) {
        super(message);
    }
}
