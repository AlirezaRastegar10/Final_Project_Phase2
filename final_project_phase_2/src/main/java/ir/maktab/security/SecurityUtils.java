package ir.maktab.security;

import ir.maktab.entity.User;

public class SecurityUtils {
    private static User user = null;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        SecurityUtils.user = user;
    }
}
