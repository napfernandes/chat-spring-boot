package com.napfernandes.chat.crypto;

public interface CryptoService {
    String generateRandomValue(int number);

    String hashValue(String stringValue, String hashString);
}
