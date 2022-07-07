package com.napfernandes.chat.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class CryptoServiceImpl implements CryptoService {
    @Override
    public String generateRandomValue(int number) {
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            byte[] saltBytes = new byte[number];
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

            secureRandom.nextBytes(saltBytes);

            for (int i = 0; i < saltBytes.length; i++) {
                stringBuilder.append(Integer.toString((saltBytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    public String hashValue(String stringValue, String hashString) {
        final StringBuilder stringBuilder = new StringBuilder();

        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            if (hashString != null) {
                messageDigest.update(hashString.getBytes());
            }

            byte[] hashedBytes = messageDigest.digest(stringValue.getBytes());

            for (int i = 0; i < hashedBytes.length; i++) {
                stringBuilder.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        return stringBuilder.toString();
    }
}