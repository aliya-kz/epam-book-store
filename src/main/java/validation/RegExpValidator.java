package validation;

import javax.validation.ConstraintValidator;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.regex.Pattern;

import static validation.RegExpConstants.*;


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
        card = card.replaceAll(REGEXP_REMOVE_CARD_SYMBOLS, "");
        return Pattern.matches(REGEXP_CARD, card);
    }

    public boolean isPhone(String phone) {
        phone = phone.replaceAll(REGEXP_REMOVE_PHONE_SYMBOLS, "");
        return Pattern.matches(REGEXP_PHONE, phone);
    }

    public boolean isEighteenYearsOld(Date date) {
        LocalDate now = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        Period period = Period.between(dateOfBirth, now);
        return period.getYears() >= 18;
    }

    public static void main (String [] args) {
        RegExpValidator validator = new RegExpValidator();
       /*System.out.println(validator.isCard("1234567891 555"));
        System.out.println(validator.isCard("12345678915554"));
        System.out.println(validator.isEmail("123456789d@555"));
        System.out.println(validator.isEmail("ddd@gg.com"));
        System.out.println(validator.isName("John-gg"));
        System.out.println(validator.isName("gf33uuu"));*/
        System.out.println(validator.isPhone("(8777)92366-07"));
        System.out.println(validator.isPhone("888f777777775"));

    }
}
