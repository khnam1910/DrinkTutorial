package com.example.drinktutorial.Controller;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordBcrypt {
    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Kiểm tra mật khẩu đã mã hóa với bcrypt
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
