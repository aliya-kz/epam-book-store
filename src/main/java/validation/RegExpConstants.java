package validation;

public class RegExpConstants {
    public final static String REGEXP_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public final static String REGEXP_NAME = "(^[\\sa-zA-Z,.'-]+|^[\\sа-яА-Я,.'-]+)$";
    public final static String REGEXP_CARD = "\\d{13,16}$";
    public final static String REGEXP_PHONE ="\\d{10,15}$";
    public final static String REGEXP_PASSWORD ="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    public final static String REGEXP_REMOVE_PHONE_SYMBOLS = "[\\s\\-\\(\\)]";
    public final static String REGEXP_REMOVE_CARD_SYMBOLS = "[\\s\\-]";

}
