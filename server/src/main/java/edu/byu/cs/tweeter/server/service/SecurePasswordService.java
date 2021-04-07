package edu.byu.cs.tweeter.server.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurePasswordService {

    public class SecurePassword {
        private String salt;
        private String hashedPassword;
        private SecurePassword(String salt, String hashedPassword) {
            this.salt = salt;
            this.hashedPassword = hashedPassword;
        }

        public String getSalt() {
            return this.salt;
        }
        public String getHashedPassword() {
            return this.hashedPassword;
        }
    }

    public SecurePasswordService() {}

    private byte[] hash(byte[] plaintext, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(salt);
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
        byte salt[] = new byte[20];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        byte[] plaintextBytes = stringToBytes(plaintext);
        byte[] hash = this.hash(plaintextBytes, salt);
        String saltString = this.bytesToString(salt);
        String hashString = this.bytesToString(hash);
        return new SecurePassword(saltString, hashString);
    }

    public boolean check(String plaintext, String storedHash, String salt) throws NoSuchAlgorithmException {
        byte[] saltBytes = this.stringToBytes(salt);
        byte[] plaintextBytes = this.stringToBytes(plaintext);
        byte[] hashedPassword = this.hash(plaintextBytes, saltBytes);
        String hashedPasswordString = this.bytesToString(hashedPassword);
        return hashedPasswordString.equals(storedHash);
    }
}