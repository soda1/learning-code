import com.soda.redis.MyRedisApplication;
import com.soda.redis.pojo.Animal;
import com.soda.redis.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyRedisApplication.class)
public class redisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    //redis string类型
    @Test
    public void redisTest() {
        User user = new User("李白", 18);

        System.out.println(redisTemplate.getKeySerializer());
        System.out.println(redisTemplate);
        System.out.println(redisTemplate.getConnectionFactory());

        redisTemplate.opsForValue().set("myRedis1", user);
        //设置过期时间
        redisTemplate.opsForValue().getOperations().expire("myRedis1", 100, TimeUnit.SECONDS);

        System.out.println(redisTemplate.opsForValue().get("myRedis1"));


        //测试反序列化

/*
        Animal animal = new Animal("企鹅", 3);
        animal.setUser(user);
        redisTemplate.opsForValue().set("animal", animal);

        System.out.println(redisTemplate.opsForValue().get("animal"));
*/
    }



    //redis list类型
    //list类型实际上是一个队列
    @Test
    public void test2() {

        //从左压入
        redisTemplate.opsForList().leftPush("listTest", new User("杜甫", 20));
        redisTemplate.opsForList().leftPush("listTest", new User("王安石", 30));

        //从右边弹出
        System.out.println(redisTemplate.opsForList().rightPop("listTest"));
        System.out.println(redisTemplate.opsForList().rightPop("listTest"));

    }


    @Test
    public void test03

    /**
     * 注解解析
     * @throws ClassNotFoundException
     */
    @Test
    public void test3() throws ClassNotFoundException {
        Class<?> aClass = Class.forName("org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean");
        Annotation[] annotations = aClass.getAnnotations();
        Conditional annotation = aClass.getAnnotation(Conditional.class);
        System.out.println(annotations.length);
        for (int i = 0; i < annotations.length; i++) {
            System.out.println(annotations[i].annotationType());

        }
        Class<? extends Condition>[] value = annotation.value();
        for (int i = 0; i < value.length; i++) {
            System.out.println(value[i]);
        }
    }


}


