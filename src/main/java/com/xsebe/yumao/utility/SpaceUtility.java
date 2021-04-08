package com.xsebe.yumao.utility;

import java.text.DecimalFormat;

public final class SpaceUtility {
    
    public static String getSpaceDisplay(final String space) {
        DecimalFormat fmt = new DecimalFormat("0.#");
        float byteLen = Float.parseFloat(space);
        float kbLen = byteLen / 1024;
        if ((int) kbLen <= 0) {
            return fmt.format(byteLen) + " 字节";
        }
        float mbLen = kbLen / 1024;
        if ((int) mbLen <= 0) {
            return fmt.format(kbLen) + " KB";
        }
        float gbLen = mbLen / 1024;
        if ((int) gbLen <= 0) {
            return fmt.format(mbLen) + " MB";
        }
        return fmt.format(gbLen) + " GB";
    }
}
