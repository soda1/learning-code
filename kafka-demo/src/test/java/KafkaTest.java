import com.soda.KafkaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author soda
 * @date 2021/5/12
 */

@SpringBootTest(classes = KafkaApplication.class)
@RunWith(value = SpringRunner.class)
public class KafkaTest {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void test() {
        kafkaTemplate.send("test1", "hello world");
        kafkaTemplate.flush();
    }
}
