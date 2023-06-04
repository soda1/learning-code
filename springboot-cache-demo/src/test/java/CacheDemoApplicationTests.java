import com.soda.learn.CacheDemoApplication;
import com.soda.learn.javabean.Account;
import com.soda.learn.mapper.AccountMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CacheDemoApplication.class)
@RunWith(SpringRunner.class)
public class CacheDemoApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

    @Qualifier("myRedisTemplate")
    @Autowired
    RedisTemplate myRedisTemplate;

    @Autowired
    AccountMapper accountMapper;
    @Test
    public void test01() {
        Account byId = accountMapper.findById(1);
//        myRedisTemplate.opsForList().leftPush("account", byId);
//        myRedisTemplate.opsForHash().put("account1", "1", byId);

    }
}
