package test.project.onlineshop.exception;

public class ProductCategoryNotFoundException extends RuntimeException{
    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}
