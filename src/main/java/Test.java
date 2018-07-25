import com.zzzz.controller.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class Test {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("140.143.59.220", 6379);
        jedis.auth("root");
        System.out.println(jedis.ping());
    }
}
