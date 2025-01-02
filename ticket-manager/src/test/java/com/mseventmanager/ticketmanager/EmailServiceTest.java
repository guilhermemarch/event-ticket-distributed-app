package com.mseventmanager.ticketmanager;



import com.mseventmanager.ticketmanager.services.EmailService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    @Test
    void sendEmail_ShouldSendEmailSuccessfully() {
        String to = "c0mbedforn1ght@gmail.com";
        String subject = "Testando";
        String body = "Teste123...";

        assertDoesNotThrow(() -> EmailService.sendEmail(to, subject, body));
    }

    @Test
    void sendEmail_ShouldThrowRuntimeExceptionWhenEmailFails() {
        String invalidTo = "emailfalso-email";
        String subject = "Test sdsd";
        String body = "Test sds";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> EmailService.sendEmail(invalidTo, subject, body));
        assertEquals("Erro ao enviar e-mail.", exception.getMessage());
    }
}