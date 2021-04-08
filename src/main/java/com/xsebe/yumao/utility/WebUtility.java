package com.xsebe.yumao.utility;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.CharEncoding;

import com.xsebe.api.utility.DataUtility;
import com.xsebe.api.utility.URLDeEncodeUtility;

public final class WebUtility {

    private WebUtility() {
    }

    public static String getParameter(final String name, final String targetCharset, final HttpServletRequest req) {
        String parameter = req.getParameter(name);
        try {
            return new String(parameter.getBytes(CharEncoding.ISO_8859_1), targetCharset);
        } catch (Throwable ex) {
        }
        return parameter;
    }

    public static String getReturnMapping(final String startsWithCurrent, final String returnMapping, final String defaultReturnMapping) {
        String _returnMapping = defaultReturnMapping;
        if (DataUtility.isNotEmptyString(returnMapping, true)) {
            _returnMapping = URLDeEncodeUtility.decode(returnMapping, CharEncoding.UTF_8);
        }
        if (_returnMapping.startsWith(startsWithCurrent)) {
            _returnMapping = defaultReturnMapping;
        }
        return _returnMapping;
    }
}
