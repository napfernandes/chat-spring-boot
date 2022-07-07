package com.napfernandes.chat.crypto;

import com.napfernandes.chat.crypto.exception.RandomValueNumberOfBytesException;

public interface CryptoService {
    String generateRandomValue(int numberOfBytes) throws RandomValueNumberOfBytesException;

    String hashValue(String stringValue, String hashString);
}
