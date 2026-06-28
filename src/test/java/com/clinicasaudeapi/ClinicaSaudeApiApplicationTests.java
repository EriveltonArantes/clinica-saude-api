package com.clinicasaudeapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ClinicaSaudeApiApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que o contexto Spring inicializa sem erros.
        // Roda com profile 'test' (H2 in-memory, sem PostgreSQL).
    }
}
