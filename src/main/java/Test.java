import com.zzzz.controller.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static void main(String[] args) {
//        String e1 = "abc@qq.com";
//        String e2 = "abc@qq@qq.com";
//        String e3 = "abc.@@qq.com";
//        String e4 = "@qq.com";
//        String e5 = "abc@";
//        String e6 = "@";
//        String e7 = "abcqq.com";
//        System.out.println(checkEmailValidity(e1));
//        System.out.println(checkEmailValidity(e2));
//        System.out.println(checkEmailValidity(e3));
//        System.out.println(checkEmailValidity(e4));
//        System.out.println(checkEmailValidity(e5));
//        System.out.println(checkEmailValidity(e6));
//        System.out.println(checkEmailValidity(e7));
        logger.debug("DEBUG!");
        logger.error("ERROR!");
    }

    private static boolean checkEmailValidity(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0) {
            return false;
        }
        return true;
    }
}
