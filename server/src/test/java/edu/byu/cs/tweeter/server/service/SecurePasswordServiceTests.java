package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurePasswordServiceTests {

    SecurePasswordService service;
    String password1;
    String password2;
    String password3;
    String password4;

    @BeforeEach
    void setup() {
        service = new SecurePasswordService();
        password1 = "mypassword123";
        password2 = "babyYoda93293";
        password3 = "longerAndHarderToGuess00000_138737&#&3241";
    }

    @Test
    void test_hashDoesNotEqualPlaintext() throws NoSuchAlgorithmException {
        String hashed1 = service.hash(password1).getHashedPassword();
        assertNotEquals(hashed1, password1);
        String hashed2 = service.hash(password2).getHashedPassword();
        assertNotEquals(hashed2, password2);
        String hashed3 = service.hash(password3).getHashedPassword();
        assertNotEquals(hashed3, password3);
    }

    @Test
    void test_ableToVerifyPasswordOnceHashed() throws NoSuchAlgorithmException {
        SecurePasswordService.SecurePassword secure1 = service.hash(password1);
        assertTrue(service.check(password1, secure1.getHashedPassword()));

        SecurePasswordService.SecurePassword secure2 = service.hash(password2);
        assertTrue(service.check(password2, secure2.getHashedPassword()));

        SecurePasswordService.SecurePassword secure3 = service.hash(password3);
        assertTrue(service.check(password3, secure3.getHashedPassword()));
    }
}
