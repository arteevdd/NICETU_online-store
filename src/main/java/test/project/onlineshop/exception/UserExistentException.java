package test.project.onlineshop.exception;

public class UserExistentException extends RuntimeException{
    public UserExistentException(String message) {
        super(message);
    }
}
