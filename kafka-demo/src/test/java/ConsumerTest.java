import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author eric
 * @date 3/2/2023
 */
public class ConsumerTest {

    @Test
    public void testEnableCommitConsumer() throws InterruptedException {
        String topic = "test1";
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.3.100:9092");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group0");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer(properties);
        consumer.subscribe(Arrays.asList(topic));
        TopicPartition partition = new TopicPartition(topic, 0);
        OffsetAndMetadata test1 = consumer.committed(new TopicPartition(topic, 0));
        System.out.println(test1);
        int i = 0;
        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(100);
            consumer.commitAsync();
            TimeUnit.SECONDS.sleep(10);
            System.out.println(poll.count());
            for (ConsumerRecord<String, String> record : poll) {
                System.out.println(++i + record.value());
            }
            test1 = consumer.committed(partition);
            System.out.println(test1);
            //throw new RuntimeException();
        }


    }

}
