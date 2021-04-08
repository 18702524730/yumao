package com.xsebe.yumao.utility;

import java.util.Random;

import org.apache.shiro.crypto.hash.Sha512Hash;

public final class PasswordCipherUtility {

    private PasswordCipherUtility() {
    }

    public static byte[] createSalt() {
        byte[] b = new byte[16];
        Random rand = new Random();
        rand.nextBytes(b);
        return b;
    }

    public static byte[] getLoginPasswordCipherBySalt(final String loginPassword, final byte[] salt) {
        return new Sha512Hash(loginPassword.toCharArray(), salt, 6).getBytes();
    }
    
}
