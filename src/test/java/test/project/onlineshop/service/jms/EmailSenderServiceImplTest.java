package test.project.onlineshop.service.jms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Service layer: Email Sender")
class EmailSenderServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailSenderServiceImpl emailSenderService;

    @Test
    @DisplayName("When email address existent")
    void sendEmail_WasSuccessful() {
        String email = "arteic4@yandex.ru";
        String subject = "nothing";
        String message = "nothing";
        emailSenderService.sendEmail(email, subject, message);
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("When email address not existent")
    void sendEmail_WasFailed() {
        doThrow(new MailSendException("Failed to send email!")).when(mailSender).send(any(SimpleMailMessage.class));
        assertThrows(MailException.class, () -> emailSenderService.sendEmail("invalid@example.com", "nothing", "nothing"));
    }
}