package validation;

import java.util.regex.Pattern;

import static validation.ValidationConstants.*;


public class RegExpValidator {

    public boolean isPassword(String password) {
        return Pattern.matches(REGEXP_PASSWORD, password);
    }

}
