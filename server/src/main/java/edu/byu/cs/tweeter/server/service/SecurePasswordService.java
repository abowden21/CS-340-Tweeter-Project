package edu.byu.cs.tweeter.server.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurePasswordService {

    private final byte[] salt = "jazzAreWinningTheFinals".getBytes();

    // TODO: consider switching to BCRYPT or something more secure than sha256

    public static class PasswordException extends Exception {
        public PasswordException(String message) {
            super(message);
        }
    }

    public class SecurePassword {
        private String hashedPassword;
        private SecurePassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
        }

        public String getHashedPassword() {
            return this.hashedPassword;
        }
    }

    public SecurePasswordService() {}

    private byte[] hash(byte[] plaintext) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(this.salt);
        byte[] hash = digest.digest(plaintext);
        return hash;
    }

    private String bytesToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    private byte[] stringToBytes(String string) {
        return string.getBytes(StandardCharsets.ISO_8859_1);
    }

    public SecurePassword hash(String plaintext) throws NoSuchAlgorithmException {
        byte[] plaintextBytes = stringToBytes(plaintext);
        String hashString = this.bytesToString(hash(plaintextBytes));
        return new SecurePassword(hashString);
    }

    public boolean check(String plaintext, String storedHash) throws NoSuchAlgorithmException {
        byte[] plaintextBytes = this.stringToBytes(plaintext);
        byte[] hashedPassword = this.hash(plaintextBytes);
        String hashedPasswordString = this.bytesToString(hashedPassword);
        return hashedPasswordString.equals(storedHash);
    }
}