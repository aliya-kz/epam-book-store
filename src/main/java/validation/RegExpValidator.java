package validation;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.regex.Pattern;

import static validation.ValidationConstants.*;


public class RegExpValidator {

    public boolean isEmail(String email) {
        return Pattern.matches(REGEXP_EMAIL, email);
    }

    public boolean isPassword(String password) {
        return Pattern.matches(REGEXP_PASSWORD, password);
    }

    public boolean isName(String name) {
        return Pattern.matches(REGEXP_NAME, name);
    }

    public boolean isCard(String card) {
        card = card.replaceAll(REGEXP_REMOVE_CARD_SYMBOLS, SIGN_BLANK);
        return Pattern.matches(REGEXP_CARD, card);
    }

    public boolean isPhone(String phone) {
        phone = phone.replaceAll(REGEXP_REMOVE_PHONE_SYMBOLS, SIGN_BLANK);
        return Pattern.matches(REGEXP_PHONE, phone);
    }

    public boolean isEighteenYearsOld(Date date) {
        LocalDate now = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        Period period = Period.between(dateOfBirth, now);
        return period.getYears() >= 18;
    }
}
