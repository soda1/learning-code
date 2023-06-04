import com.soda.learn.amqp.AMQPApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@SpringBootTest(classes = AMQPApplication.class)
@RunWith(SpringRunner.class)
public class AMQPTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void test01() {
        //测试RabbitMQ
        rabbitTemplate.convertAndSend("soda.exchange", "soda.test", "hello", new CorrelationData(UUID.randomUUID().toString()));
    }

    @Test
    public void test02() {
        String str = "w" + "a";
    }


    @Test
    public void test03() {
        //测试RabbitMQ
        rabbitTemplate.convertAndSend("soda.delayExchange", "soda.delayKey", "hello", new CorrelationData(UUID.randomUUID().toString()));
    }

}
