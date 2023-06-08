package test.project.onlineshop.exception;

public class RejectedTransactionException extends RuntimeException{
    public RejectedTransactionException(String message) {
        super(message);
    }
}
