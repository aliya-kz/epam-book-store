package validation;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;

import java.util.Locale;
import java.util.ResourceBundle;

import static validation.ValidationConstants.*;

public class ChangePasswordFormValidator {

    private String message;
    private String locale;
    ResourceBundle bundle;
    RegExpValidator regExpValidator = new RegExpValidator();
    private final UserDao userDao = new UserDaoImpl();
    private static final String PASSWORD_SIZE_ERROR = "PASSWORD_SIZE_ERROR";
    private static final String PASSWORD_EQUALITY_ERROR = "PASSWORD_EQUALITY_ERROR";
    private static final String INVALID_PASSWORD_ERROR = "INVALID_PASSWORD_ERROR";

    public ChangePasswordFormValidator(String locale) {
        this.locale = locale;
        bundle = ResourceBundle.getBundle(CONTENT, new Locale(locale));
        message = SIGN_BLANK;
    }

    public boolean isValid(User user, String oldPassword, String newPassword, String newPasswordRepeat) {

        if (!regExpValidator.isPassword(newPassword)) {
            message = message.concat(bundle.getString(PASSWORD_SIZE_ERROR));
            return false;
        }
        if (!newPassword.equals(newPasswordRepeat)) {
            message = message.concat(bundle.getString(PASSWORD_EQUALITY_ERROR));
            return false;
        }
        boolean oldPasswordCorrect = userDao.validateUser(user.getEmail(), oldPassword);
        if (!oldPasswordCorrect) {
            message = message.concat(bundle.getString(INVALID_PASSWORD_ERROR));
            return false;
        }
        return true;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
