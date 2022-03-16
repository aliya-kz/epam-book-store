package passwordEncr;
import com.lambdaworks.crypto.SCryptUtil;

public class PasswordEncrypter {

    private static final int CPU_PARAMETER = 16;
    private static final int MEMORY_PARAMETER = 16;
    private static final int PARALLELIZATION_PARAMETER = 16;

    public static String encrypt(String password) {
        return SCryptUtil.scrypt(password, CPU_PARAMETER, MEMORY_PARAMETER, PARALLELIZATION_PARAMETER);
    }
}

