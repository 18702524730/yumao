package com.xsebe.yumao.utility;

import java.util.Arrays;

import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.xsebe.yumao.PermConstants;

public final class ShiroUtility {

    private ShiroUtility() {
    }

    public static boolean isAdmin(final Subject currentUser) {
        return currentUser.hasRole(PermConstants.ROLE_LABEL_ROOT);
    }

    public static boolean isAuthenticated(final Subject currentUser) {
        return currentUser.isAuthenticated();
    }

    public static boolean hasRole(final Subject currentUser, final String label) {
        return currentUser.hasRole(label);
    }

    public static boolean hasAllRoles(final Subject currentUser, final String... labels) {
        return currentUser.hasAllRoles(Arrays.asList(labels));
    }

    public static boolean hasPermission(final Subject currentUser, final String label) {
        return currentUser.isPermitted(label);
    }

    public static boolean hasAllPermissions(final Subject currentUser, final String... labels) {
        return currentUser.isPermittedAll(labels);
    }

    public static Session getSession(final Subject currentUser) {
        return currentUser.getSession(true);
    }

    public static Object removeSessionAttribute(final Session session, final Object key) {
        try {
            return session.removeAttribute(key);
        } catch (InvalidSessionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Object getSessionAttribute(final Session session, final Object key) {
        try {
            return session.getAttribute(key);
        } catch (InvalidSessionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setSessionAttribute(final Session session, final Object key, final Object value) {
        try {
            session.setAttribute(key, value);
        } catch (InvalidSessionException ex) {
            ex.printStackTrace();
        }
    }
}
