package com.luminesway.concursoadminstrator.shared.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.luminesway.concursoadminstrator.config.properties.AesProperties;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AesCryptoService {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;

    private final SecretKeySpec keySpec;
    private final SecureRandom secureRandom = new SecureRandom();

    public AesCryptoService(AesProperties properties) {
        byte[] keyBytes = Base64.getDecoder().decode(properties.getKey());

        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException("Invalid AES key length");
        }

        this.keySpec = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String plainText) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    keySpec,
                    new GCMParameterSpec(TAG_LENGTH, iv)
            );

            byte[] encrypted = cipher.doFinal(plainText.getBytes());

            ByteBuffer buffer = ByteBuffer.allocate(iv.length + encrypted.length);
            buffer.put(iv);
            buffer.put(encrypted);

            return Base64.getEncoder().encodeToString(buffer.array());

        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar", e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            byte[] decoded = Base64.getDecoder().decode(encryptedText);
            ByteBuffer buffer = ByteBuffer.wrap(decoded);

            byte[] iv = new byte[IV_LENGTH];
            buffer.get(iv);

            byte[] cipherText = new byte[buffer.remaining()];
            buffer.get(cipherText);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(
                    Cipher.DECRYPT_MODE,
                    keySpec,
                    new GCMParameterSpec(TAG_LENGTH, iv)
            );

            return new String(cipher.doFinal(cipherText));

        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar", e);
        }
    }
}
