package com.napfernandes.chat.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.napfernandes.chat.crypto.exception.RandomValueNumberOfBytesException;

public class CryptoServiceUnitTest {

    private CryptoService cryptoService;

    @BeforeEach
    public void beforeEach() {
        cryptoService = new CryptoServiceImpl();
    }

    @AfterEach
    public void afterEach() {
        cryptoService = null;
    }

    @Test
    public void generateRandomValue_shouldThrowErrorWhenNumberOfBytesIsLesserThan0() {
        assertThrows(RandomValueNumberOfBytesException.class, () -> {
            this.cryptoService.generateRandomValue(-1);
        });
    }

    @Test
    public void generateRandomValue_shouldThrowErrorWhenNumberOfBytesIs0() {
        assertThrows(RandomValueNumberOfBytesException.class, () -> {
            this.cryptoService.generateRandomValue(0);
        });
    }

    @Test
    public void generateRandomValue_shouldGeneratesString() throws RandomValueNumberOfBytesException {
        assertNotNull(this.cryptoService.generateRandomValue(10));
    }

    @Test
    public void hashValue_shouldGeneratesHashedString() {
        assertNotNull(this.cryptoService.hashValue("test", "test"));
    }
}
