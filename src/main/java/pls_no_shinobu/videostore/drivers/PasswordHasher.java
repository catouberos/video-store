/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2023A
  Assessment: Final Project
  Author: pls_no_shinobu
*/
package pls_no_shinobu.videostore.drivers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordHasher {
    public static final int COST_POW = 10;

    // password hashed pattern: digit-cost-pow$32-letter-hashed
    private static final Pattern pattern = Pattern.compile("(\\d\\d?)\\$(.{43})");

    private final SecureRandom random;

    public PasswordHasher() {
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
        byte[] dk = encrpytPBKDF2(password, salt, 1 << COST_POW);

        // finalize the saved hash
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);

        // save saved hash (as byte) and encode it to a string
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        return COST_POW + "$" + enc.encodeToString(hash);
    }

    public boolean check(String password, String token) {
        return check(password.toCharArray(), token);
    }

    public boolean check(char[] password, String token) {
        // match the token using compiled pattern
        Matcher match = pattern.matcher(token);

        if (!match.matches()) throw new IllegalArgumentException("Token is invalid");

        // get arguments from match
        int iterations = Integer.parseInt(match.group(1));
        byte[] hash = Base64.getUrlDecoder().decode(match.group(2));
        byte[] salt = Arrays.copyOfRange(hash, 0, 16);

        // generate the hashed password using provided infomation
        byte[] check = encrpytPBKDF2(password, salt, 1 << iterations);
        int zero = 0;

        // algorithm: on every byte on hashed password, check if:
        // 1. the hashed password (after salt string) matches (using XOR bitwise - when the bit is
        // different) the generated password
        // 2. if the statement is true (that they are the same), it will return 0 for XOR
        // 3. zero is bitwise OR assigned to that value - means if they are the same, return 0

        for (int i = 0; i < check.length; ++i) zero |= hash[salt.length + i] ^ check[i];

        return zero == 0;
    }

    private static byte[] encrpytPBKDF2(char[] password, byte[] salt, int iterations) {
        // key length in bit, 128/8 = 16
        KeySpec spec = new PBEKeySpec(password, salt, iterations, 128);

        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Missing algorithm PBKDF2WithHmacSHA1", e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("SecretKeyFactory is invalid", e);
        }
    }
}
