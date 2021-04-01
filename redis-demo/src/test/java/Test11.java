import org.junit.Test;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

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
}
