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
        int aInt = 0xa0;
        byte aByte = (byte) aInt;
        int diff = 4;
        int bitCount = 0;
        int aInt1 = aByte  & ((0xff << 4) & 0xff);
        //for (int i = 0; i < diff; i++) {
        //    int i1 = aByte & (1 << (7 - i));
        //    if (i1 > 0) {
        //        bitCount++;
        //    }
        //}
        bitCount = Integer.bitCount(aInt1);
        System.out.println(bitCount);
    }
}
