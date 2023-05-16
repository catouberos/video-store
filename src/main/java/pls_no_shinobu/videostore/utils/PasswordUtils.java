/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtils {
    public static final int COST_POW = 10;

    private final SecureRandom random;

    public PasswordUtils() {
        this.random = new SecureRandom();
    }

    public String hash(String password) {
        return hash(password.toCharArray());
    }

    public String hash(char[] password) {
        // generate new salt
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // encrypt password using itself and salt, with the cost of 2^COST_POW
        byte[] dk = encryptPBKDF2(password, salt, 1 << COST_POW);

        // finalize the saved hash
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);

        // save saved hash (as byte) and encode it to a string
        return COST_POW + "$" + HexFormat.of().formatHex(hash);
    }

    public boolean check(String password, String token) {
        return check(password.toCharArray(), token);
    }

    public boolean check(char[] password, String token) {
        String[] parts = token.split("\\$");

        // get arguments from match
        int iterations = Integer.parseInt(parts[0]);
        byte[] hash = HexFormat.of().parseHex(parts[1]);
        byte[] salt = Arrays.copyOfRange(hash, 0, 16);

        // generate the hashed password using provided infomation
        byte[] check = encryptPBKDF2(password, salt, 1 << iterations);
        int zero = 0;

        // algorithm: on every byte on hashed password, check if:
        // 1. the hashed password (after salt string) matches (using XOR bitwise - when the bit is
        // different) the generated password
        // 2. if the statement is true (that they are the same), it will return 0 for XOR
        // 3. zero is bitwise OR assigned to that value - means if they are the same, return 0

        for (int i = 0; i < check.length; ++i) zero |= hash[salt.length + i] ^ check[i];

        return zero == 0;
    }

    private static byte[] encryptPBKDF2(char[] password, byte[] salt, int iterations) {
        // key length in bit, 128/8 = 16
        KeySpec spec = new PBEKeySpec(password, salt, iterations, 128);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Missing algorithm PBKDF2WithHmacSHA1", e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("SecretKeyFactory is invalid", e);
        }
    }
}
