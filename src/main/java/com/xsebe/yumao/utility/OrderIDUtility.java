package com.xsebe.yumao.utility;

import java.text.DecimalFormat;
import java.util.Date;

import com.xsebe.api.utility.DateFormatUtility;
import com.xsebe.yumao.DateConstants;

public final class OrderIDUtility {

    public static String createOrderID() {
        StringBuffer string = new StringBuffer();
        string.append("ym-");
        string.append(DateFormatUtility.format(DateConstants.PATTERN_YYYYMMDDHHMMSSSSS, new Date()));
        string.append('-');
        string.append(new DecimalFormat("000").format(Math.random() * 999));
        return string.toString();
    }
}
