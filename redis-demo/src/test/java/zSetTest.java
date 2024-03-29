import com.soda.learn.redis.MyRedisApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyRedisApplication.class)
public class zSetTest {

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 请求限流
     */
    @Test
    public void rateLimitTest() throws InterruptedException {
        int count = 0;
        for (int j = 0; j < 1; j++) {
            TimeUnit.MILLISECONDS.sleep(1000);
            for (int i = 0; i < 1000; i++) {
                if (rateLimit(1, 30)) {
                    count++;
                    System.out.println(count);
                }

            }
        }


    }

    private boolean rateLimit(int period, int maxLimit) {
        String key = "rateLimit";
        byte[] keyBytes = key.getBytes();
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //pipeline operation
        connection.openPipeline();
        connection.multi();
        long nowTs = System.currentTimeMillis();
        connection.zAdd(keyBytes, nowTs, ("" + nowTs).getBytes());
        connection.zRemRangeByScore(keyBytes, 0, nowTs - period * 1000);
        connection.exec();
        connection.closePipeline();
        //if this operation use pipeline, will return null
        Long limit = connection.zCard(keyBytes);
        //System.out.println(limit);
        return limit <= maxLimit;
    }
    @Test
    public void test() {
        String key = "rateLimit";
        byte[] keyBytes = key.getBytes();
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        Long limit = connection.zCard(keyBytes);
        System.out.println(limit);
    }
}


