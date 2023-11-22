package com.scaler.notificationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.notificationservice.model.NotificationMessageRequest;
import com.scaler.notificationservice.model.UsersEntity;
import com.scaler.notificationservice.repository.UserRepository;
import com.scaler.notificationservice.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchEmailFromDbAndSend() throws JsonProcessingException {
        // Arrange
        NotificationMessageRequest message = new NotificationMessageRequest();
        message.setNotifiedUser("testUser");
        message.setNewState("NEW");
        message.setPrevState("OLD");
        message.setUpdatedBy("admin");

        UsersEntity mockUser = new UsersEntity();
        mockUser.setEmail("test@example.com");

        when(userRepository.findByUsername(any())).thenReturn(mockUser);

        // Act
        emailService.fetchEmailFromDbAndSend(message);

        // Assert
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));

    }
}
