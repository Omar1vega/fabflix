package edu.uci.ics.vegao1.service.idm.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public final class Crypto {
    private static SecureRandom secRand = new SecureRandom();
    public static final int ITERATIONS = 10000;
    public static final int KEY_LENGTH = 512;

    // PBKDF2 -- Password-based-key-derivation-function
    // HMAC -- Key-hashed Message Authentication code, used in conjunction with any cryptographic hash function.
    // SHA512 -- 512 bit member function of the SHA-2 cryptographic hash functions family designed by the NSA
    private static final String hashFunction = "PBKDF2WithHmacSHA512";

    private Crypto() {
    }

    public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(hashFunction);
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] genSalt() {
        byte[] salt = new byte[4];
        secRand.nextBytes(salt);
        salt[0] = (byte) (~(Byte.toUnsignedInt(salt[0]) >>> 2));
        return salt;
    }
}
