package com.yamamz.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Created by admin on 2/18/2018.
 */
public class PasswordUtils {

    private static StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    /**
     * Encrypts (digests) a password.
     *
     * @param data - the password to be encrypted.
     * @return
     */
    public static String encryptPassword(String data) {

        String result = passwordEncryptor.encryptPassword(data);

        return result;
    }

    /**
     * Checks an unencrypted (plain) password against an encrypted one (a digest) to see if they match.
     *
     * @param plainText
     * @param encryptedPassword
     * @return true if passwords match, false if not.
     */
    public static boolean checkPassword(String plainText, String encryptedPassword) {

        return passwordEncryptor.checkPassword(plainText, encryptedPassword);
    }

}
