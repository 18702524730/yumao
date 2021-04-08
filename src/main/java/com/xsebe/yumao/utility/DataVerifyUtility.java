package com.xsebe.yumao.utility;

import java.util.Date;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.DateFormatUtility;
import com.xsebe.yumao.exception.YumaoException;

public final class DataVerifyUtility {

    private DataVerifyUtility() {
    }
    /**
    * @Title: notEmptyRequired 
    * @Description: 
    * @param s 验证的字符串
    * @param trim  为true的话去除前后空格
    * @param happenCode
    * @param happenMessage
    * @return
    * @throws YumaoException String
    * @date 2018年8月15日下午4:48:07
     */
    public static String notEmptyRequired(final String s, final boolean trim, final int happenCode, final String happenMessage) throws YumaoException {
        if (DataUtility.isEmptyString(s, trim)) {//为空的话抛异常，trim为true的话去除前后空格
            throw new YumaoException(happenCode, happenMessage);
        }
        return (trim ? s.trim() : s);
    }
    /**
     * 
    * @Title: notEmptyNoRequired 
    * @Description: TODO 
    * @param s 验证的字符串
    * @param trim 为true的话去除前后空格
    * @return String
    * @date 2018年8月15日下午4:48:56
     */
    public static String notEmptyNoRequired(final String s, final boolean trim) {
        if (DataUtility.isEmptyString(s, trim)) {//为空的话就返回字符串，trim为true的话去前后空格
            return "";
        }
        return (trim ? s.trim() : s);
    }
    /**
     * 
    * @Title: lengthAtBoundRequired 
    * @Description: 如果字符串不在这个范围就抛异常
    * @param s
    * @param min
    * @param max
    * @param happenCode
    * @param happenMessage
    * @throws YumaoException void
    * @date 2018年8月15日下午4:50:22
     */
    public static void lengthAtBoundRequired(final String s, final int min, final int max, final int happenCode, final String happenMessage)
            throws YumaoException {//如果字符串长度不在该范围就抛异常
        if (DataUtility.isNotBoundAt(s.length(), min, max)) {
            throw new YumaoException(happenCode, happenMessage);
        }
    }
    /**
     * 
    * @Title: matchesRequired 
    * @Description: 如果不匹配正则就抛异常
    * @param s 字符串
    * @param regex 正则
    * @param happenCode
    * @param happenMessage
    * @throws YumaoException void
    * @date 2018年8月15日下午4:51:27
     */
    public static void matchesRequired(final String s, final String regex, final int happenCode, final String happenMessage) throws YumaoException {
        if (DataUtility.isNotMatches(s, regex)) {
            throw new YumaoException(happenCode, happenMessage);
        }
    }
    
    public static Date dateRequired(final String s, final String pattern, final int happenCode, final String happenMessage) throws YumaoException {
        Date date = DateFormatUtility.parse(pattern, s);
        if (DataUtility.isNull(date)) {
            throw new YumaoException(happenCode, happenMessage);
        }
        return date;
    }
    //验证邮箱格式
    public static void emailRequired(final String email, final int happenCode , final String happenMessage) throws YumaoException{
        matchesRequired(email, "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", happenCode, happenMessage);
    }
    
    //固定电话和手机号同时验证
    public static void fixAndMobilePequired(final String fixAndMobile, final int happenCode , final String happenMessage) throws YumaoException{
        matchesRequired(fixAndMobile, "((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$", happenCode, happenMessage);
    }
    
    public static void mobileRequired(final String mobile, final int happenCode, final String happenMessage) throws YumaoException {
        matchesRequired(mobile, "^1\\d{10}$", happenCode, happenMessage);
    }
    
    public static void idcardRequired(final String _idcard, final int happenCode, final String happenMessage) throws YumaoException {
        matchesRequired(_idcard, "^\\d{17}[0-9Xx]$", happenCode, happenMessage);
    }
    
    public static void bankCardNoRequired(final String bankCardNo, final int happenCode, final String happenMessage) throws YumaoException {
        matchesRequired(bankCardNo, "^\\d{1,22}$", happenCode, happenMessage);
    }
}
