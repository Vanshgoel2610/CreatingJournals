package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
//    @InjectMocks
    private EmailService emailService;

//    @Mock
//    private JavaMailSender javaMailSender;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void testSendMail() {
        emailService.sendEmail("goelv2610@gmail.com",
                "Testing SpringBoot Mails sender",
                "Hi, I send this mail using SpringBoot");

//        // Assert
//        Mockito.verify(javaMailSender).send(Mockito.any(SimpleMailMessage.class));
    }
}