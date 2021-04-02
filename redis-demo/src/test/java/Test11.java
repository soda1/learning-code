import org.junit.Test;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.util.UUID;

public class Test11 {

    @Test
    public void test1() {

        System.out.println(Math.pow(2, 32));
    }

    @Test
    public void test2() {
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        Object deserialize = jdkSerializationRedisSerializer.serialize("videoLike".getBytes());
        System.out.println(deserialize);
    }


    @Test
    public void test3() {
        System.out.println(UUID.randomUUID().toString());
    }
}
