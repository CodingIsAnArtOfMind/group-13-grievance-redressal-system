package com.scaler.notificationservice;

import com.scaler.notificationservice.model.NotificationMessageRequest;
import com.scaler.notificationservice.rabbitMqConsumer.RabbitMQConsumer;
import com.scaler.notificationservice.service.EmailService;
import com.scaler.notificationservice.service.NotificationService;
import com.scaler.notificationservice.service.SMSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class RabbitMQConsumerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private EmailService emailService;

    @Mock
    private SMSService smsService;

    @InjectMocks
    private RabbitMQConsumer rabbitMQConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testReceiveMessage() {
        // Given
        NotificationMessageRequest message = new NotificationMessageRequest(/* construct your message here */);

        // When
        rabbitMQConsumer.receiveMessage(message);

        // Then
        verify(notificationService, times(1)).sendNotification(message);
        assertThrows(IllegalArgumentException.class, () -> rabbitMQConsumer.receiveMessage(null));
    }

    @Test
    void testGetLatch() {
        // When
        CountDownLatch latch = rabbitMQConsumer.getLatch();

        // Then
        Objects.requireNonNull(latch);
    }
}
