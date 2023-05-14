package test.project.onlineshop.service.jms;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}
