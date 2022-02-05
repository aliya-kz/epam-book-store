package passwordEncr;
import com.lambdaworks.crypto.SCryptUtil;

public class PasswordEncrypter {

    public static String encrypt(String password) {

        return SCryptUtil.scrypt(password, 16, 16, 16);
    }
}

