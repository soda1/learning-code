import com.soda.learn.KafkaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author soda
 * @date 2021/5/12
 */

@SpringBootTest(classes = KafkaApplication.class)
@RunWith(value = SpringRunner.class)
public class KafkaTest {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Test
    public void test() {
        ListenableFuture<SendResult<Integer, String>> send = kafkaTemplate.send("test1", "hello world222");
        send.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                result.toString();
            }
        });
        kafkaTemplate.flush();
    }

    @Test
    public void seedMsg() {
        for (int i = 0; i < 5; i++) {
            kafkaTemplate.send("test1", "hello world" + i);

        }
        kafkaTemplate.flush();
    }


}
