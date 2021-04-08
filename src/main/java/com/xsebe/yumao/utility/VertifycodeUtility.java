package com.xsebe.yumao.utility;

public final class VertifycodeUtility {

    public static char[] NUMBERS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private VertifycodeUtility() {
    }

    private static char randomChar(final char[] chrs) {
        return chrs[(int) (Math.random() * chrs.length)];
    }

    public static String randomLen(final int len, final char[] chrs) {
        StringBuffer string = new StringBuffer();
        for (int i = 0; i < len; i++) {
            string.append(randomChar(chrs));
        }
        return string.toString();
    }
}
